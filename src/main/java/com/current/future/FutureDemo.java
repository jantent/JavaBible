package com.current.future;

import com.current.threadpool.ThreadPoolKit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author: tangJ
 * @Date: 2018/11/9 16:56
 * @description:
 */
public class FutureDemo {
    public static void main(String args[]) throws Exception{
        ExecutorService threadPool = ThreadPoolKit.getThreadPool();
        CallableTask task = new CallableTask();
        Future<String> result = threadPool.submit(task);
        Thread.sleep(300);
        if (result.isDone()){
            System.out.println(" future is ok");
            System.out.println(" result is ----------");
            System.out.println(result.get());
        }else {
            System.out.println(" future is not ok,try again");
            Thread.sleep(300);
            System.out.println(result.get());
        }
    }
}
