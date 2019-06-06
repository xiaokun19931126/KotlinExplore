package com.xiaokun.kotlinexplore.basis

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by xiaokun on 2019/6/6.
 * @date 2019/6/6
 * @author xiaokun
 */
class TestActivity : AppCompatActivity() {

    /**
     * 如果你没有指定返回值,它就会返回Unit,与Java中的void类似,但是Unit是一个真正的对象
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //toast("你好")
        niceToast("你好")
    }

    /**
     * 当然你也可以指定返回值的类型 : + 类型
     */
    fun add(x: Int, y: Int): Int {
        return x + y
    }

    /**
     * 当返回值是一个表达式时,可以直接使用等号
     */
    fun add1(x: Int, y: Int): Int = x + y

    /**
     * 参数写法和java有些不同,是先写参数名 + : + 后写参数类型
     *
     * 第二个参数指定了默认值,如果没有传第二个参数,那么指定使用默认值
     */
    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, length).show()
    }

    /**
     * 后面两个参数都指定了默认值
     */
    fun niceToast(message: String, className: String = javaClass.simpleName,
                  length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, "[$className] $message", length).show()
    }

}