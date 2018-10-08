package com.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

/**
 * @author: tangJ
 * @Date: 2018/9/28 10:01
 * @description:
 */
public class ZkSession {
    private static String addr = "10.0.90.102:2181";

    private static String nodePath = "/zk-book/test";

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
    public void testSession() throws InterruptedException {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(addr, 5000, 3000, retryPolicy);
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testFluentSession() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(addr)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
    }

    @Test
    public void testNamespace() throws Exception {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(addr)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                // 创建独立的命名空间
                .namespace("base")
                .build();
        client.start();
    }

    /**
     * 创建节点
     *
     * @throws Exception
     */
    @Test
    public void createNode() throws Exception {
        CuratorFramework client = getClient();
        client.start();

        String nodeContent = "你好啊";

        // 创建节点
        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(nodePath, nodeContent.getBytes("gbk"));
    }


    /**
     * 删除节点
     *
     * @throws Exception
     */
    @Test
    public void deleteNode() throws Exception {
        CuratorFramework client = getClient();
        client.start();

        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(nodePath);
        client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(nodePath);
    }

    /**
     * 获取节点中的数据
     *
     * @throws Exception
     */
    @Test
    public void getData() throws Exception {
        CuratorFramework client = getClient();
        client.start();
        Stat stat = new Stat();
        byte[] nodeData = client.getData().storingStatIn(stat).forPath(nodePath);
        System.out.println(new String(nodeData));
    }


    @Test
    public void updateNode() throws Exception{
        CuratorFramework client = getClient();
        client.start();
        Stat stat = new Stat();
        byte[] nodeData = client.getData().storingStatIn(stat).forPath(nodePath);
        System.out.println(new String(nodeData));
        client.setData().withVersion(stat.getVersion()).forPath(nodePath,"hello".getBytes());
    }
}
