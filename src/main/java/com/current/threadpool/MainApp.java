package com.current.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: tangJ
 * @Date: 2018/11/8 13:46
 * @description:
 */
public class MainApp {

    private static String threadName = "janti";

    private static int coreSize = Runtime.getRuntime().availableProcessors();

    private static int maxiSize = coreSize * 2;

    private static BlockingQueue queue = new ArrayBlockingQueue(10000);


    public static void main(String args[]) {
        WorkService service = new WorkService();
        Runnable runnable = new MyTask(service);

        JanThreadFactory threadFactory = new JanThreadFactory(threadName);

        JanThreadPool threadPool = new JanThreadPool(coreSize, maxiSize, queue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            threadPool.execute(runnable);
        }

    }
}
