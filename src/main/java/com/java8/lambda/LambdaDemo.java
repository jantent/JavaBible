package com.java8.lambda;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author: tangJ
 * @Date: 2018/11/15 17:47
 * @description:
 */
public class LambdaDemo {

    /**
     * 简单的使用
     * e是由 编译期推理得出的，也可以显示指定参数类型
     */
    @Test
    public void simple(){
        Arrays.asList("a","b","a").forEach(e -> System.out.println(e));
        Arrays.asList("a","b","a").forEach((String e) -> System.out.println(e));
    }

    @Test
    public void complex(){
        Arrays.asList("a","b","a").forEach(
                e -> {
                    try {
                        System.out.println(e);
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }
            );
    }



}
