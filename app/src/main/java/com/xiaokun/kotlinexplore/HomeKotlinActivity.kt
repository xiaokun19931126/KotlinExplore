package com.xiaokun.kotlinexplore

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.xiaokun.kotlinexplore.contact.ContactsActivity
import kotlinx.android.synthetic.main.activity_home.*

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/12/18
 *      描述  ：比较简洁,省去了很多模板代码
 *      版本  ：1.0
 * </pre>
 */
class HomeKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        contact.setOnClickListener {
            //跳转到联系人
            startActivity(Intent(this, ContactsActivity::class.java))
        }

        coroutine.setOnClickListener {
            //跳转到协程
            Toast.makeText(this, "未完待续", Toast.LENGTH_SHORT).show()
        }
    }
}