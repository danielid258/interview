package com.daniel.interview.jvm;

/**
 * Daniel on 2018/10/25.
 */
public class JVM {
    /**

     内存溢出   java.lang.OutOfMemoryError 指程序在申请内存时,没有足够的内存空间供其使用,出现OutOfMemoryError

     产生原因
         JVM内存过小。
         程序不严密,产生了过多的垃圾。
     程序体现
         内存中加载的数据量过于庞大,如一次从数据库取出过多数据。
         集合类中有对对象的引用,使用完后未清空,使得JVM不能回收。
         代码中存在死循环或循环产生过多重复的对象实体。
     解决方法
         增加JVM的内存大小 编辑bin/catalina.sh文件,找到JAVA_OPTS选项 设置需要的启动参数
         优化程序,释放垃圾
            主要思路就是避免死循环,防止一次载入太多的数据,提高程序健壮性及时释放没用的对象

     内存泄露   Memory Leak
     Java中,内存泄漏就是存在一些对象有下面两个特点
         1 这些对象是可达的,即在有向图中,存在通路可以与其相连
         2 这些对象是无用的,即程序以后不会再使用这些对象
     内存泄露的解决方法就是提高程序的健壮型,因为内存泄露是纯代码层面的问题

     */
}
