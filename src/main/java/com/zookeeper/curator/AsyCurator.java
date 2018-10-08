package com.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: tangJ
 * @Date: 2018/9/28 12:55
 * @description:
 */
public class AsyCurator {

    private static String addr = "10.0.90.102:2181";

    private static String nodePath = "/zk-book/test";

    static CountDownLatch semaphore = new CountDownLatch(2);

    static ExecutorService threadPool = Executors.newFixedThreadPool(2);


    private BackgroundCallback getBck(){
        BackgroundCallback backgroundCallback = new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("返回码为: "+curatorEvent.getResultCode());
                System.out.println("状态为: "+curatorEvent.getType());
                semaphore.countDown();
            }
        };
        return backgroundCallback;
    }

    private CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(addr)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        return client;
    }

    @Test
    public void asyncCreateNode() throws Exception{
        CuratorFramework client = getClient();
        client.start();

        String sendMsg = "测试";

        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .inBackground(getBck(),threadPool).forPath(nodePath,sendMsg.getBytes());
        Thread.sleep(5000);
    }
}
