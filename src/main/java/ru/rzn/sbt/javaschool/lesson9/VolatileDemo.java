package ru.rzn.sbt.javaschool.lesson9;

public class VolatileDemo {
    private static int MY_INT = 0;
    private static int MY_INT_MAX = 10;

    public static void main(String[] args) {
        ChangeListener t1 = new ChangeListener();
        t1.start();
        ChangeMaker t2 = new ChangeMaker();
        t2.start();
        System.out.println("Thread.activeCount()" + Thread.activeCount());
//        System.out.println("Thread.currentThread()"+Thread.);
//        t1.interrupt();
//        t2.interrupt();

    }

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int local_value = MY_INT;
            while (local_value < MY_INT_MAX) {
                if (local_value != MY_INT) {
                    System.out.println(String.format("Got Change for MY_INT : %s|| local : %d", MY_INT, local_value));
                }
                local_value = MY_INT;
//                else {
//                    local_value = MY_INT;
//
//                    System.out.println(String.format("MY_INT : %d || local : %d", MY_INT, local_value));
//                }
            }
        }
    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {

            int local_value = MY_INT;
            while (MY_INT < MY_INT_MAX) {
                System.out.println(String.format("Incrementing MY_INT to %d", local_value + 1));
                MY_INT = ++local_value;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}