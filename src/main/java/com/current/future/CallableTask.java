package com.current.future;

import com.httpclient.PoolHttpClient;

import java.util.concurrent.Callable;

/**
 * @author: tangJ
 * @Date: 2018/11/9 16:50
 * @description:
 */
public class CallableTask implements Callable<String>{
    @Override
    public String call() throws Exception {
        PoolHttpClient poolHttpClient = new PoolHttpClient();
        String getUrl = "https://www.baidu.com";
        String result = poolHttpClient.doGet(getUrl);
        return result;
    }
}
