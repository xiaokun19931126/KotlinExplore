package com.xiaokun.kotlinexplore.contact

/**
 * 1.data关键字告诉kotlin编译器这个类是被用来储存数据，会被生成很多有用的方法，比如toString方法实现对象内容
 * 2.val修饰不可变属性,只可被赋值一次;var修饰可变属性,可以被赋值多次;
 *
 * @author 肖坤
 */
internal data class Contact(val firstName: String, val lastName: String, var email: String)
