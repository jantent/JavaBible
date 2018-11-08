package com.current.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * @author: tangJ
 * @Date: 2018/11/8 11:20
 * @description: 线程池
 */

public class JanThreadPool extends ThreadPoolExecutor {

    // 记录开始时间
    private final ThreadLocal<Long> startTimeLocal = new ThreadLocal<>();

    private static Logger logger = Logger.getLogger("JanThreadPool");
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public JanThreadPool(int corePoolSize, int maximumPoolSize, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, 15, TimeUnit.SECONDS, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        logger.info(String.format("%s start", t.getName()));
        startTimeLocal.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTimeLocal.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            logger.info(String.format("Thread %s: end %s, time=%dns", t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        super.terminated();
    }
}
