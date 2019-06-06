package com.xiaokun.kotlinexplore.basis

/**
 * Created by xiaokun on 2019/6/6.
 *
 * 任何类如果不指定 open 或者 abstract 都是默认指定 final
 * 只有加了上面两个修饰符才可以被继承
 * 如果不指定继承,默认任何类都是继承 Any (和 java 的 Object 类似)
 *
 * @date 2019/6/6
 * @author xiaokun
 */
open class Animal(name: String)