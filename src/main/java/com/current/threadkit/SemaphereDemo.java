package com.current.threadkit;

import com.current.threadpool.ThreadPoolKit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 * @author: tangJ
 * @Date: 2018/11/9 14:40
 * @description: 信号量例子
 */
public class SemaphereDemo {
    public static void main(String args[]) {
        ExecutorService threadPool = ThreadPoolKit.getThreadPool();
        int N = 8;
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < N; i++) {
            threadPool.execute(new Worker(i, semaphore));
        }
        threadPool.shutdown();
    }

    static class Worker extends Thread {
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                // 获得信号量
                semaphore.acquire();
                System.out.println("worker " + num + "  working...");
                Thread.sleep(2000);
                System.out.println("worker " + num + "  release...");
                // 释放信号量
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
