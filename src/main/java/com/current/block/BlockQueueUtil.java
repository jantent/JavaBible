package com.current.block;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author: tangJ
 * @Date: 2018/11/7 16:09
 * @description:
 */
public class BlockQueueUtil {
    private static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(1000);

    public static void put(String toBePut) throws InterruptedException {
        blockingQueue.put(toBePut);
    }

    public static String take() throws InterruptedException {
        return blockingQueue.take();
    }

    public static int size() {
        return blockingQueue.size();
    }
}
