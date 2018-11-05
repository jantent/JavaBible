package com.current.threadlocal;

/**
 * @author: tangJ
 * @Date: 2018/11/5 18:07
 * @description:
 */
public class ThreadLocalDemo {
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    static class task implements Runnable{

        @Override
        public void run() {

        }
    }
}
