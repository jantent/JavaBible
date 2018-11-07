package com.current.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author: tangJ
 * @Date: 2018/11/7 10:26
 * @description:
 */
public class CallableDemo {

    public static void main(String args[]) throws Exception{
        int seed = 100;
        String threadName = "callabledemo";
        Callable callable = new Task(seed,threadName);
        FutureTask<MsgResult> futureTask = new FutureTask(callable);
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(callable);
        Thread.sleep(500);
        System.out.println(futureTask.get());
    }
}

class Task implements Callable<MsgResult> {

    private int seed;

    private String name;

    public Task(int seed, String name) {
        this.seed = seed;
        this.name = name;
    }

    @Override
    public MsgResult call() throws Exception {
        MsgResult result = new MsgResult();
        result.setName(name);
        result.setGoal((int) (Math.random() * seed));
        return result;
    }
}
