package com.netty.heartbreak.client;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: tangJ
 * @Date: 2018/9/26 16:50
 * @description: 客户端缓存
 */
public class ClientCache {

    private static ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap();


    public static Object getMsg(String key) {
        return cacheMap.remove(key);
    }

    public static void saveMsg(String key, Object msg) {
        cacheMap.put(key, msg);
    }



}
