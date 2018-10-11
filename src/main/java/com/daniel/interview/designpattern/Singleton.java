package com.daniel.interview.designpattern;

/**
 * Daniel on 2018/10/11.
 */
public class Singleton {

}


//饿汉式，无线程安全问题，不能延迟加载，影响系统性能
/*
class Singleton {
    private static Singleton instance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }
}
*/

//双重校验锁，线程安全，推荐使用
/*
public class Singleton {

    private static volatile Singleton singleton;
    private Singleton() {
    }
    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
*/

//静态内部类，线程安全，主动调用时才实例化，延迟加载效率高，推荐使用
/*
public class Singleton {
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton (){}
    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
*/