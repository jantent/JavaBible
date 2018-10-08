package com.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.CancelLeadershipException;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.junit.Test;

/**
 * @author: tangJ
 * @Date: 2018/9/28 15:55
 * @description:
 */
public class AboutMaster {

    static String masterPath = "/master";

    @Test
    public void testSelcetMaster() throws Exception{
        CuratorFramework client = ZkUtil.getClient();
        client.start();
        LeaderSelector selector = new LeaderSelector(client, masterPath, new LeaderSelectorListener() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                System.out.println("成为Master");
            }

            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                if (connectionState == ConnectionState.SUSPENDED || connectionState == ConnectionState.LOST){
                    throw new CancelLeadershipException("抛出异常");
                }
            }
        });

        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
