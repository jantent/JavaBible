package com.current.block;

import java.util.UUID;

/**
 * @author: tangJ
 * @Date: 2018/11/7 16:25
 * @description:
 */
public class WorkerServer {

    public void produce() throws Exception {
        String product = UUID.randomUUID().toString();
        BlockQueueUtil.put(product);
        System.out.println(Thread.currentThread().getName()+" produce one, product name : " + product + "---->   size : " + BlockQueueUtil.size());
    }

    public void consume() throws Exception {
        String result = BlockQueueUtil.take();
        System.out.println(Thread.currentThread().getName()+" consume one, product name : " + result + "---->   size : " + BlockQueueUtil.size());
    }
}
