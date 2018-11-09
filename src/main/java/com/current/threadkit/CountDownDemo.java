package com.current.threadkit;

import com.current.threadpool.ThreadPoolKit;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

/**
 * @author: tangJ
 * @Date: 2018/11/9 15:02
 * @description:
 */
public class CountDownDemo {

    /**
     * 假设进行一个实验，实验前需要检查10台机器是否正常
     * 待所有机器检查完毕，实验开始
     * 使用CountDownLatch 计数
     */

    public static void main(String arg[]) throws InterruptedException {
        ExecutorService threadPool = ThreadPoolKit.getThreadPool();
        int N = 10;
        CountDownLatch countDownLatch = new CountDownLatch(N);
        for (int i=1;i<=N;i++){
            threadPool.execute(new Worker(i,countDownLatch));
        }
        countDownLatch.await();
        System.out.println("----------everything is OK-----------");

    }

    static class Worker implements Runnable {
       int num;
       CountDownLatch countDownLatch;

        public Worker(int num, CountDownLatch countDownLatch) {
            this.num = num;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("worker check machine number "+num+" complete!");
                countDownLatch.countDown();
                System.out.println("now count "+countDownLatch.getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
