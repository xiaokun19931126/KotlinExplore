package com.xiaokun.kotlinexplore.contact

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.EditText
import android.widget.TextView
import com.xiaokun.kotlinexplore.R

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/12/13
 *      描述  ： kotlin的高阶函数
 *              此方法3个参数,返回值是boolean
 *              前面两个是有默认值的参数,如果不传就取默认值
 *              第三个参数是一个lambda表达式,此表达式扩展了TextView
 *
 *      版本  ：1.0
 * </pre>
 */
internal inline fun EditText.validateWith(
        passIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_pass),
        failIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_fail),
        validator: TextView.() -> Boolean): Boolean {
    //这里是TextView调用自身方法
    setCompoundDrawablesWithIntrinsicBounds(null, null, if (validator()) passIcon else failIcon, null)
    return validator()
}