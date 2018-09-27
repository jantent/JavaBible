package com.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * @author: tangJ
 * @Date: 2018/9/27 09:37
 * @description:
 */
public class IdleConnectionMonitorThread extends Thread{
    private final HttpClientConnectionManager connMgr;
    private volatile boolean exitFlag = false;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
        this.connMgr = connMgr;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!this.exitFlag) {
            synchronized (this) {
                try {
                    this.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 关闭失效的连接
            connMgr.closeExpiredConnections();
            // 可选的, 关闭30秒内不活动的连接
            connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
        }
    }

    public void shutdown() {
        this.exitFlag = true;
        synchronized (this) {
            notify();
        }
    }

}
