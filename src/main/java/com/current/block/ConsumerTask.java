package com.current.block;

/**
 * @author: tangJ
 * @Date: 2018/11/7 16:30
 * @description:
 */
public class ConsumerTask implements Runnable{
    private WorkerServer workerServer;

    public ConsumerTask(WorkerServer workerServer) {
        this.workerServer = workerServer;
    }

    @Override
    public void run() {
        for (;;){
            try {
                workerServer.consume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
