package com.current.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: tangJ
 * @Date: 2018/11/8 11:36
 * @description: 线程工厂
 */
public class JanThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private String namePrefix;

    public JanThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = "poolThread-" + namePrefix + "-" + poolNumber.getAndIncrement();
        Thread t = new Thread(group, r, threadName, 0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
