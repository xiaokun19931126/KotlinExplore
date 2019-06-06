package com.xiaokun.kotlinexplore.basis

import android.util.Log

/**
 * Created by xiaokun on 2019/6/6.
 *
 * 任何类如果不指定 open 或者 abstract 都是默认指定 final
 * 只有加了上面两个修饰符才可以被继承
 *
 * 如果不指定继承,默认任何类都是继承 Any (和 java 的 Object 类似)
 *
 * 如果只有单个构造器时, 我们需要从父类继承下来的构造器中指定需要的参数
 * 这是用来代替 Java 中的 super 调用
 *
 * @date 2019/6/6
 * @author xiaokun
 */
class People(name: String, surname: String) : Animal(name) {

    //构造函数函数体
    init {
        Log.e("xiaokun", "name:" + name)
        Log.e("xiaokun", "surname:" + surname)
    }
}