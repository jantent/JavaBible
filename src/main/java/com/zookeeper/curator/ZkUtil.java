package com.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: tangJ
 * @Date: 2018/9/28 15:22
 * @description:
 */
public class ZkUtil {

    private static String addr = "10.0.90.102:2181";

    public static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(addr)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        return client;
    }
}
