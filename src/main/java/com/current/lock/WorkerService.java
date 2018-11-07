package com.current.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WorkerService {
    private static int nextThread = 1;

    private ReentrantLock lock = new ReentrantLock(false);

    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void executeOne() {
        try {
            lock.lock();
            while (nextThread != 1) {
                conditionA.await();
            }
            nextThread = 2;
            conditionB.signal();
            System.out.println(Thread.currentThread().getName() + " work");
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void executeTwo() {
        try {
            lock.lock();
            while (nextThread != 2) {
                conditionB.await();
            }
            nextThread = 3;
            conditionC.signal();
            System.out.println(Thread.currentThread().getName() + " work");
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void executeThree() {
        try {
            lock.lock();
            while (nextThread != 3) {
                conditionC.await();
            }
            nextThread = 1;
            conditionA.signal();
            System.out.println(Thread.currentThread().getName() + " work");
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
