package com.current.lock;

/**
 * @author: tangJ
 * @Date: 2018/11/7 09:32
 * @description:
 */
public class MainApp {
    public static void main(String args[]) {
        WorkerService service = new WorkerService();
        Thread a = new Thread(getTaskA(service), "a");
        Thread b = new Thread(getTaskB(service), "b");
        Thread c = new Thread(getTaskC(service), "c");
        a.start();
        b.start();
        c.start();
    }

    public static Runnable getTaskA(WorkerService service) {
        return new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    service.executeOne();
                }
            }
        };
    }

    public static Runnable getTaskB(WorkerService service) {
        return new Runnable() {
            @Override
            public void run() {
                for (; ; )
                    service.executeTwo();
            }
        };
    }

    public static Runnable getTaskC(WorkerService service) {
        return new Runnable() {
            @Override
            public void run() {
                for (; ;) {
                    service.executeThree();
                }
            }
        };
    }
}
