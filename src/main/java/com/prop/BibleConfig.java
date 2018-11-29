package com.prop;

/**
 * @author: tangJ
 * @Date: 2018/11/29 17:10
 * @description:
 */
public class BibleConfig extends IConfig{

    public String name;

    public int age;


    private BibleConfig(){
        init();
    }

    private static class  Inner{
        static BibleConfig bibleConfig = new BibleConfig();
    }


    public static BibleConfig getInstance(){
        return Inner.bibleConfig;
    }




}
