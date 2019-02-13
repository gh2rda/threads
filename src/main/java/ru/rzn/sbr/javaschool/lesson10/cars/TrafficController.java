package ru.rzn.sbr.javaschool.lesson10.cars;

import java.util.concurrent.Semaphore;

public class TrafficController {
//    Lock myLockL = new ReentrantLock();
//    static Object lock = new Object();
    private static final Semaphore SEMAPHORE = new Semaphore(1, true);
//    private static int leftToRight = 0;

    public void enterLeft() {
        try {
//            while (leftToRight == 0) {
//                if (leftToRight == 0) {
                SEMAPHORE.acquire();
//                leftToRight = 1;
//                }
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enterRight() {
        try {
//            while (leftToRight == 0) {

                SEMAPHORE.acquire();
//                leftToRight = -1;
//            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void leaveLeft() {
        SEMAPHORE.release();
//        leftToRight = 0;

    }

    public void leaveRight() {
        SEMAPHORE.release();
//        leftToRight = 0;

    }
}