package com.prop;

import java.io.*;
import java.util.*;

/**
 * @author: tangJ
 * @Date: 2018/11/29 14:31
 * @description:
 */
public class PropTest {

    public static void main(String args[]) throws Exception {
        BibleConfig bibleConfig = BibleConfig.getInstance();
        System.out.println(bibleConfig.name);
        System.out.println(bibleConfig.age);
    }
}
