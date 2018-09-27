package com.httpclient;

import org.junit.Test;

/**
 * @author: tangJ
 * @Date: 2018/9/27 10:01
 * @description:
 */
public class HttpClientMainApp {

    static String getUrl = "https://www.baidu.com";
    static String postUrl = "https://127.0.0.1:8443/test";

    static PoolHttpClient httpClient = new PoolHttpClient();

    @Test
    public void testGet() {
        String resp = httpClient.doGet(getUrl);
        System.out.println(resp);
    }

    @Test
    public void testPost() {
        String msg = "你好";
        String resp = httpClient.doPost(postUrl, msg);
        System.out.println(resp);
    }
}
