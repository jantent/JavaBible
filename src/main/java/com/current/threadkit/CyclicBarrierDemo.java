package com.current.threadkit;

import com.current.threadpool.ThreadPoolKit;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

/**
 * @author: tangJ
 * @Date: 2018/11/9 16:09
 * @description: 循环栅栏
 */
public class CyclicBarrierDemo {
    public static void main(String args[]) throws InterruptedException {
        ExecutorService threadPool = ThreadPoolKit.getThreadPool();
        int N = 4;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N);
        for (int i = 0; i < N; i++) {
            threadPool.execute(new Worker(i, cyclicBarrier));
        }

        Thread.sleep(10 * 1000);
        System.out.println("----cyclicBarrier reuse----");

        for (int i = 0; i < N; i++) {
            threadPool.execute(new Worker(i, cyclicBarrier));
        }
    }

    static class Worker implements Runnable {
        int num;
        CyclicBarrier cyclicBarrier;

        public Worker(int num, CyclicBarrier cyclicBarrier) {
            this.num = num;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("worker " + num + " is Working");
                System.out.println("wait for others");
                Thread.sleep(2000);
                cyclicBarrier.await();
                System.out.println("worker " + num + " worker again");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
