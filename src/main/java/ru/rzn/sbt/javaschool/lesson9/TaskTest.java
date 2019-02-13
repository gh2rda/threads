package ru.rzn.sbt.javaschool.lesson9;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {
    @Override
    public void run() {
        // вывести текст на экран
        String s="";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException u) {

            s="прервано";
        }
        System.out.println(Thread.currentThread() + s+"/прошел обработку");
    }
}


public class TaskTest {
    public static void main(String[] args)
            throws Exception {
        Runnable task = new Task();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        for( int i = 0; i < 10 ; i++) {
//            executorService.schedule(new Task(), 500, TimeUnit.MILLISECONDS);
            executorService.submit(new Task());
        }
        Thread.sleep(1100);

        List<Runnable> res = executorService.shutdownNow();
//        executorService.awaitTermination(100000, TimeUnit.MILLISECONDS);
        System.out.println("не начато " + res.size());
    }
}

