package com.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryOneTime;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author: tangJ
 * @Date: 2018/9/28 16:25
 * @description:
 */
public class AboutDistribute {


    /**
     * 单机环境下生成流水号
     *
     * @throws Exception
     */
    @Test
    public void simFlu() throws Exception {
        CountDownLatch downLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        downLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.out.println("生成的订单号为： " + orderNo);
                }
            }).start();
        }
        downLatch.countDown();
        Thread.sleep(10000);
    }


    @Test
    public void distributeLock()throws Exception{
        String path = "/disPath";
        CuratorFramework client = ZkUtil.getClient();
        client.start();

        InterProcessLock lock = new InterProcessMutex(client,path);
        final CountDownLatch down = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        down.await();
                        lock.acquire();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                        String orderNo = sdf.format(new Date());
                        System.out.println("生成的订单号为： " + orderNo);
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        down.countDown();
        Thread.sleep(500);
    }

    @Test
    public void distributeCount()throws Exception{
        String path = "/count";
        CuratorFramework client = ZkUtil.getClient();
        client.start();
        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client,path,new RetryOneTime(1000));
        AtomicValue<Integer> rc = atomicInteger.add(8);
        System.out.println("结果为："+ rc.preValue());
    }
}
