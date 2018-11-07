package com.current.block;

/**
 * @author: tangJ
 * @Date: 2018/11/7 16:31
 * @description:
 */
public class MainApp {
    public static void  main(String args[]){
        WorkerServer workerServer = new WorkerServer();
        ConsumerTask consumerTask = new ConsumerTask(workerServer);
        ProduceTask produceTask = new ProduceTask(workerServer);
        Thread produce1 = new Thread(produceTask,"proAAA");
        Thread produce2 = new Thread(produceTask,"proBBB");
        Thread consume1 = new Thread(consumerTask,"con");

        produce1.start();
        produce2.start();
        consume1.start();

    }
}
