package com.xiaokun.kotlinexplore

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.contact_list_item.view.*
import kotlinx.android.synthetic.main.input_contact_dialog.view.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        mPrefs = getPreferences(Context.MODE_PRIVATE)
        mContacts = loadContacts()

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

    private lateinit var mFirstNameEdit: EditText
    private lateinit var mLastNameEdit: EditText
    private lateinit var mEmailEdit: EditText

    /**
     * 展示添加或者编辑联系人dialog
     */
    private fun showAddContactDialog(adapterPosition: Int) {
        var dialogView = LayoutInflater.from(this).inflate(R.layout.input_contact_dialog, null)
        mFirstNameEdit = dialogView.edittext_firstname
        mLastNameEdit = dialogView.edittext_lastname
        mEmailEdit = dialogView.edittext_email


    }

    override fun afterTextChanged(s: Editable?) {

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

        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

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
