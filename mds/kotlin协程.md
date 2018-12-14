kotlin协程：

协同程序是一种Kotlin功能，可将长时间运行的任务（如数据库或网络访问）的异步回调转换为顺序代码。

**将要学习如下：**

* 如何调用用协同程序编写的代码并获得结果
* 如何使用暂停功能使异步代码顺序执行
* 如何使用launch和runBlocking来控制代码如何执行
* 使用suspendCoroutine将现有API转换为协程的技术
* 如何在架构组件Architecture Components中使用协程
* 协程的最佳测试

**先决条件：**

* 具有使用AC组件的经验，如ViewModel,LiveData,Repository,Room
* 熟悉kotlin语法包括高阶函数和lamdba表达式
* 会使用基本的Android调用线程,回调等

