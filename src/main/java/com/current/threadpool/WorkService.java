package com.current.threadpool;

/**
 * @author: tangJ
 * @Date: 2018/11/8 13:39
 * @description:
 */
public class WorkService {

    public void process(){
        System.out.println(Thread.currentThread().getName()+" run");
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
