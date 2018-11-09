package com.current.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: tangJ
 * @Date: 2018/11/9 14:42
 * @description: 创建一个默认的线程池
 */
public class ThreadPoolKit {
    private static String threadName = "janti";

    private static int coreSize = Runtime.getRuntime().availableProcessors() * 2;

    private static int maxiSize = coreSize * 2;

    private static BlockingQueue queue = new ArrayBlockingQueue(10000);

    private static JanThreadFactory threadFactory = new JanThreadFactory(threadName);

    private static JanThreadPool threadPool = new JanThreadPool(coreSize, maxiSize, queue, threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
