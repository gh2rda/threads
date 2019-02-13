package ru.rzn.sbr.javaschool.lesson10.balls;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;
import javax.swing.*;

/**
 * 1. Измените метода {@link Solution#main(String[])} таким образом, чтобы вместо явного создания потоков использовался
 * какой-нибудь {@link java.util.concurrent.Executor}.
 * 2. Реализуйте последовательую "заморозку" потоков при попадании {@link Ball} на диагональ {@link BallWorld}
 * (где x == y). Попаданием считать пересечение описывающего прямоуголькника диагонали. При заморозке всех потоков
 * осуществляйте возобновление выполнения
 * 3. Введите в программу дополнительный поток, который уничтожает {@link Ball} в случайные моменты времени.
 * Начните выполнение этого потока c задержкой в 15 секунд после старта всех {@link Ball}. {@link Ball} должны
 * уничтожаться в случайном порядке. Под уничтожением {@link Ball} подразумевается
 * а) исключение из массива {@link BallWorld#balls} (нужно реализовать потокобезопасный вариант)
 * б) завершение потока, в котором выполняется соответствующая задача (следует использовать
 * {@link java.util.concurrent.Future}сформированный при запуске потока для прерывания
 * {@link java.util.concurrent.Future#cancel(boolean)})
 */
public class Solution {

    public static void nap(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("Thread " + Thread.currentThread().getName() + " throwed exception " + e.getMessage());
        }
    }

    public static void main(String[] a) {

        final BallWorld world = new BallWorld();
        final JFrame win = new JFrame();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                win.getContentPane().add(world);
                win.pack();
                win.setVisible(true);
            }
        });

        Thread.currentThread().setName("MyMainThread");

        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<?>> ttt = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ttt.add(executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Solution.nap((int) (5000 * Math.random()));
                    ThreadLocalRandom rand = ThreadLocalRandom.current();
                    Color color = new Color(rand.nextInt(0xFFFFFF));
                    new Thread(new Ball(world,
                            rand.nextInt(0, world.getWidth() - Ball.getBALLW() + 1),
                            rand.nextInt(0, world.getHeight() - Ball.getBALLH() + 1),
                            rand.nextInt(0, 10),
                            rand.nextInt(0, 10),
                            color)).start();
                }
            }));
        }
        boolean allStart = false;
        while (!allStart) {
            allStart = true;
            for (Future t : ttt) {
                if (!t.isDone()) allStart = false;
            }
        }
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        boolean allDelete = false;
//        int numDel = 0;

        for (int i = 0; i < 3; i++) {
            world.deleteBall(world.getBalls().get(i));
        }

//            nap((int) (5000 * Math.random()));
//            new Thread(new Ball(world, 50, 80, 5, 10, Color.red)).start();
//            nap((int) (5000 * Math.random()));
//            new Thread(new Ball(world, 70, 100, 8, 6, Color.blue)).start();
//            nap((int) (5000 * Math.random()));
//            new Thread(new Ball(world, 150, 100, 9, 7, Color.green)).start();
//            nap((int) (5000 * Math.random()));
//            new Thread(new Ball(world, 200, 130, 3, 8, Color.black)).start();
//            nap((int) (5000 * Math.random()));
    }
}
