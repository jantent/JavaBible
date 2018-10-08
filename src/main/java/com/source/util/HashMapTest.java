package com.source.util;

import org.junit.Test;

import java.util.HashMap;

/**
 * @author: tangJ
 * @Date: 2018/10/8 17:23
 * @description:
 */
public class HashMapTest {

    @Test
    public void  simpleTest() throws Exception{
        HashMap<Integer,String> map = new HashMap<>();
        String ha = "adf";
        for (int i=0;i<10000000;i++){

            map.put(i,ha+i);
        }
        System.out.println("内存大小为： "+Runtime.getRuntime().maxMemory()/(1024*1024));
        System.out.println("插入之后的结果为"+ map.size());
        Runtime.getRuntime().gc();
    }
}
