package com.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

/**
 * @author: tangJ
 * @Date: 2018/9/28 15:21
 * @description:
 */
public class AboutWatcher {

    private static String forPath = "/zk-book/cache";

    @Test
    public void testNodeCache() throws Exception{
        CuratorFramework client = ZkUtil.getClient();
        client.start();

        String content = "测试数据";
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(forPath,content.getBytes());
        final NodeCache cache = new NodeCache(client,forPath,false);
        cache.start(true);
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("NODE DATA update， new data:"+new String(cache.getCurrentData().getData()));
            }
        });
        String newData = "新的数据";
        client.setData().forPath(forPath, newData.getBytes());
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath(forPath);
        Thread.sleep(5000);
    }

    @Test
    public void testPathCache() throws Exception{
        String path = "/path";
        CuratorFramework client = ZkUtil.getClient();
        client.start();

        PathChildrenCache cache = new PathChildrenCache(client,path,true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                switch (pathChildrenCacheEvent.getType()){
                    case CHILD_ADDED:
                        System.out.println("child add：  "+ pathChildrenCacheEvent.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("child revoked：  "+ pathChildrenCacheEvent.getData().getPath());
                        break;
                        default:
                            break;
                }
            }
        });
        client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        Thread.sleep(1000);

        client.create().withMode(CreateMode.PERSISTENT).forPath(path+"/c1");
        Thread.sleep(1000);

        client.delete().forPath(path+"/c1");
        Thread.sleep(Integer.MAX_VALUE);


    }
}
