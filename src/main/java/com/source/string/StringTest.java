package com.source.string;

import org.junit.Test;

/**
 * @author: tangJ
 * @Date: 2018/10/8 11:16
 * @description:
 */
public class StringTest {

    @Test
    public void testeuqals() throws Exception{
        String jof = new String("qewr");
        String andi = "qewr";
        System.out.println("== 比较结果：   "+ (jof==andi));
        System.out.println("equals 比较结果：   "+ (jof.equals(andi)));
    }

    @Test
    public void teststartwith() throws Exception{
        String bengin = "123";
        String src = "qe123456";
        System.out.println("开头接口是否正确： "+src.startsWith(bengin,2));
    }

    @Test
    public void testIndexof() throws Exception{
        String src = "qe123456";
        System.out.println("是否存在：  "+src.indexOf("1"));
    }

    @Test
    public void testHashCode() throws Exception{
        Character src = 'a';
        int code = src.hashCode();
        System.out.println(code);
    }
}
