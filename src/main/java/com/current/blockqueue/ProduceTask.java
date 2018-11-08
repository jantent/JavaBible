package com.current.blockqueue;

/**
 * @author: tangJ
 * @Date: 2018/11/7 16:29
 * @description:
 */
public class ProduceTask implements Runnable{
    private WorkerServer workerServer;

    public ProduceTask(WorkerServer workerServer) {
        this.workerServer = workerServer;
    }

    @Override
    public void run() {
        for (;;) {
            try {
                workerServer.produce();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
