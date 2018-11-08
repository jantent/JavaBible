package com.current.threadpool;

/**
 * @author: tangJ
 * @Date: 2018/11/8 13:38
 * @description:
 */
public class MyTask implements Runnable{

    WorkService workService;

    public MyTask(WorkService workService) {
        this.workService = workService;
    }

    @Override
    public void run() {
        workService.process();
    }
}
