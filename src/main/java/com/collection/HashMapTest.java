package com.collection;

import org.junit.Test;

import java.util.HashMap;

/**
 * @author tangj
 * @date 2018/10/21 21:13
 * @description:
 */
public class HashMapTest {


    @Test
    public void testPut() throws Exception{
        HashMap<String,Object> testMap = new HashMap<>(10);
        testMap.put("test","test123");
    }
}
