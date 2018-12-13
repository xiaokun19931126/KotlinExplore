package com.xiaokun.kotlinexplore

import android.bluetooth.le.AdvertiseCallback
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.contact_list_item.view.*
import kotlinx.android.synthetic.main.content_contacts.*
import kotlinx.android.synthetic.main.input_contact_dialog.view.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

/**
 * kotlin中有四个可见性修饰符:private、 protected、 internal 和 public。
 * 如果没有显式指定修饰符的话，默认可见性是 public。
 * @author 肖坤
 */
class ContactsActivity : AppCompatActivity(), TextWatcher {

    /**
     * 关键字lateinit：
     * 1.使用这个关键字当你知道这个属性一旦被初始化后不会为null；
     * 2.使用lateinit关键字在private修饰符给所有的成员变量移除null赋值，通过移除?.来改变类型不为null;
     *   但是布尔值除外;
     * 3.给Boolean属性类型赋值：
     *   private var mEntryValid = false
     */
    private lateinit var mPrefs: SharedPreferences
    private lateinit var mContacts: ArrayList<Contact>
    private lateinit var mAdapter: ContactsAdapter

    private lateinit var mFirstNameEdit: EditText
    private lateinit var mLastNameEdit: EditText
    private lateinit var mEmailEdit: EditText
    private var valitor = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        mPrefs = getPreferences(Context.MODE_PRIVATE)
        mContacts = loadContacts()
        mAdapter = ContactsAdapter(mContacts)

        setSupportActionBar(toolbar)
        setupRecyclerView()

        fab.setOnClickListener { showAddContactDialog(-1) }
    }

    /**
     * 加载联系人
     */
    private fun loadContacts(): ArrayList<Contact> {
        var contactSet = mPrefs.getStringSet(CONTACT_KEY, HashSet())
        /**
         * mapTo()是kotlin的高阶函数用来扩展java列表类。
         * lambda扩展表达式传入的参数是list中的item
         */
        return contactSet.mapTo(ArrayList()) { Gson().fromJson(it, Contact::class.java) }
    }

    /**
     * 设置recyclerView
     */
    private fun setupRecyclerView() {
        contact_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        contact_list.adapter = mAdapter
    }

    /**
     * 从assets中获取联系人
     */
    private fun generateContacts() {
        var contactsString = readContactJsonFile()
        try {
            var contactsJson = JSONArray(contactsString)
            for (i in 0 until contactsJson.length()) {
                var contactJson = contactsJson.getJSONObject(i)
                var contact = Contact(contactJson.getString("first_name"), contactJson.getString("last_name"),
                        contactJson.getString("email"))
                mContacts.add(contact)
            }

            mAdapter.notifyDataSetChanged()
            saveContacts()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * 带?表示返回有可能为null
     */
    private fun readContactJsonFile(): String? {
        var contactsString: String? = null
        try {
            var inputStream = assets.open("mock_contacts.json")
            var size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            contactsString = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return contactsString
    }

    /**
     * 保存联系人到sp
     */
    private fun saveContacts() {
        val edit = mPrefs.edit()
        edit.clear()
        var contactSet = mContacts.map { Gson().toJson(it, Contact::class.java) }.toSet()
        edit.putStringSet(CONTACT_KEY, contactSet)
        edit.apply()
    }

    /**
     * 展示添加或者编辑联系人dialog
     */
    private fun showAddContactDialog(contactPosition: Int) {
        var dialogView = LayoutInflater.from(this).inflate(R.layout.input_contact_dialog, null)
        mFirstNameEdit = dialogView.edittext_firstname
        mLastNameEdit = dialogView.edittext_lastname
        mEmailEdit = dialogView.edittext_email

        mFirstNameEdit.addTextChangedListener(this)
        mLastNameEdit.addTextChangedListener(this)
        mEmailEdit.addTextChangedListener(this)

        //检测是编辑还是添加
        var editing = contactPosition > -1

        if (editing) {
            val contact = mContacts.get(contactPosition)
            mFirstNameEdit.isEnabled = false
            mLastNameEdit.isEnabled = false
            mEmailEdit.isEnabled = true
            mFirstNameEdit.setText(contact.firstName)
            mLastNameEdit.setText(contact.lastName)
            mEmailEdit.setText(contact.email)
        }

        var dialogTitle = if (editing) {
            getString(R.string.edit_contact)
        } else {
            getString(R.string.add_contact)
        }

        var builder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle(dialogTitle)
                .setPositiveButton("保存", null)
                .setNegativeButton("取消", null)

        val dialog = builder.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (valitor) {
                if (editing) {
                    var contact = mContacts.get(contactPosition)
                    contact.email = mEmailEdit.text.toString()
                    mAdapter.notifyItemChanged(contactPosition)
                } else {
                    var contact = Contact(mFirstNameEdit.text.toString(), mLastNameEdit.text.toString(), mEmailEdit.text.toString())
                    mContacts.add(contact)
                    mAdapter.notifyItemInserted(mContacts.size)
                }
                dialog.dismiss()
                saveContacts()
            } else {
                Toast.makeText(this@ContactsActivity, "请输入正确的联系人", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 清空联系人
     */
    private fun clearContacts() {
        mContacts.clear()
        saveContacts()
        mAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contacts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.action_clear -> {
                clearContacts()
                return true
            }
            R.id.action_generate -> {
                generateContacts()
                return true
            }
            R.id.action_sort_first -> {

                return true
            }
            R.id.action_sort_last -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun afterTextChanged(s: Editable?) {
        /**
         * 1.创建一个不可变的变量后面用来保存一个lambda表达式,命令为notEmpty
         *   这个lambda表达式携带一个TextView参数然后返回一个布尔值Boolean
         *   如下所示：
         *   val notEmpty:(TextView) -> Boolean
         * 2.给这个lambda表达式赋值,当textview的text属性不为空时返回true
         *   val notEmpty:(TextView) -> Boolean = {textView -> textView.text.isNotEmpty()}
         * 3.如果lambda表达式只有一个参数,那能被忽略然后被it代替
         *   如下所示：
         *   val notEmpty:(TextView) -> Boolean = {it.text.isNotEmpty()}
         * 4.创建另外一个lambda表达式并赋值给isEmail
         *   如下所示：
         *   val isEmail:(TextView) -> Boolean = {Patterns.EMAIL_ADDRESS.matcher(text).matches()}
         * 5.
         *
         *
         */
        val notEmpty: (TextView) -> Boolean = { it.text.isNotEmpty() }
        val isEmail: (TextView) -> Boolean = { Patterns.EMAIL_ADDRESS.matcher(it.text).matches() }

        var passIcon: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_pass)
        var failIcon: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_fail)

        if (notEmpty(mFirstNameEdit)) {
            mFirstNameEdit.setCompoundDrawablesWithIntrinsicBounds(null, null, passIcon, null)
        } else {
            mFirstNameEdit.setCompoundDrawablesWithIntrinsicBounds(null, null, failIcon, null)
        }

        if (notEmpty(mLastNameEdit)) {
            mLastNameEdit.setCompoundDrawablesWithIntrinsicBounds(null, null, passIcon, null)
        } else {
            mLastNameEdit.setCompoundDrawablesWithIntrinsicBounds(null, null, failIcon, null)
        }

        if (isEmail(mEmailEdit)) {
            mEmailEdit.setCompoundDrawablesWithIntrinsicBounds(null, null, passIcon, null)
        } else {
            mEmailEdit.setCompoundDrawablesWithIntrinsicBounds(null, null, failIcon, null)
        }

        if (notEmpty(mFirstNameEdit) && notEmpty(mLastNameEdit) && isEmail(mEmailEdit)) {
            valitor = true
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    /**
     * 1.如果你声明为 internal，它会在相同模块内随处可见;这表示这个构造方法在这个模块内都可以调用
     * 2.kotlin的主构造函数是类声明的一部分,在类的名字后面的括号里,也可以在类的内部使用constructor关键字
     *   提供其它构造函数
     *
     */
    private inner class ContactsAdapter internal constructor(private val mContacts: ArrayList<Contact>) :
            RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mContacts.size
        }

        override fun onBindViewHolder(holder: ViewHolder, poosition: Int) {
            val (firstName, lastName, email) = mContacts[poosition]
            val fullName = "$firstName$lastName"
            holder.nameLabel.text = fullName
            holder.emailLabel.text = email
        }

        /**
         * 类名后面跟括号是构造函数,这里没有添加constructor关键字和修饰符,但是这个是内部类
         */
        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var nameLabel: TextView = itemView.textview_name
            var emailLabel: TextView = itemView.textview_email

            /**
             * init代码块会被构造方法执行
             */
            init {
                itemView.setOnClickListener { showAddContactDialog(adapterPosition) }
            }

        }
    }

    /**
     * 请注意，即使伴随对象的成员看起来像其他语言中的静态成员，
     * 在运行时它们仍然是真实对象的实例成员，并且可以实现接口。
     */
    companion object {
        private val CONTACT_KEY = "contact_key"
        private val TAG = ContactsActivity::class.java.simpleName
    }

}
