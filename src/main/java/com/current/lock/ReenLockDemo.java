package com.current.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: tangJ
 * @Date: 2018/11/6 17:57
 * @description:
 */
public class ReenLockDemo {

    private static ReentrantLock lock = new ReentrantLock(true);

    static int count = 0;

    public static Runnable getRunable(){
        Runnable runnable = new Runnable() {
            @Override
            public void run()    {
                addCount();
            }
        };
        return runnable;
    }

    public static void addCount(){
        lock.lock();
        count++;
        System.out.println(Thread.currentThread().getName()+"正在执行，当前count为："+count);
        lock.unlock();
    }

    public static void main(String args[]){
        Runnable runnable = getRunable();
        new Thread(runnable, "a").start();
        new Thread(runnable, "b").start();
        new Thread(runnable, "c").start();
        new Thread(runnable, "d").start();
    }


}

