package com.current.threadlocal;

import java.util.UUID;

/**
 * @author: tangJ
 * @Date: 2018/11/5 18:07
 * @description:
 */
public class ThreadLocalDemo {


    public static void main(String args[]) {
        Task task = new Task();
        Thread threadA = new Thread(task, "threadA");
        Thread threadB = new Thread(task, "threadB");
        Thread threadC = new Thread(task, "threadC");

        threadA.start();
        threadB.start();
        threadC.start();
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            ThreadUtil.threadLocal.set(i);
            ThreadUtil.threadLocal.set(100);
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName+"get value:  "+ThreadUtil.threadLocal.get());
        }
    }
}