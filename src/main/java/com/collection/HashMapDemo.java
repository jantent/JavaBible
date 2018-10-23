package com.collection;

import org.junit.Test;

import java.util.HashMap;

/**
 * @author: tangJ
 * @Date: 2018/10/22 14:15
 * @description:
 */
public class HashMapDemo {
    @Test
    public void testInfinite() throws InterruptedException {
        HashMap<Integer,String> map = new HashMap<Integer,String>(2,0.75f);
        map.put(5, "C");

        new Thread("Thread1") {
            public void run() {
                map.put(7, "B");
                System.out.println(map);
            };
        }.start();
        new Thread("Thread2") {
            public void run() {
                map.put(3, "A");
                        System.out.println(map);
            };
        }.start();

        Thread.sleep(500);
    }
}
