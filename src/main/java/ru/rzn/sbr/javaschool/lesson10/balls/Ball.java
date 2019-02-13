package ru.rzn.sbr.javaschool.lesson10.balls;

import java.awt.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Ball implements Runnable {

    BallWorld world;
    private final static CyclicBarrier barrier = new CyclicBarrier(3);

    private volatile boolean visible = false;

    private int xpos, ypos, xinc, yinc;

    private final Color col;

    private final static int BALLW = 10;
    private final static int BALLH = 40;

    public Ball(BallWorld world, int xpos, int ypos, int xinc, int yinc, Color col) {

        this.world = world;
        this.xpos = xpos;
        this.ypos = ypos;
        this.xinc = xinc;
        this.yinc = yinc;
        this.col = col;
        world.addBall(this);
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public static int getBALLW() {
        return BALLW;
    }

    public static int getBALLH() {
        return BALLH;
    }

    @Override
    public void run() {
        this.visible = true;
        try {
            while (true) {
                move();
            }
        } catch (InterruptedException e) {
            // Пока ничего:)
        }
    }


    public void move() throws InterruptedException {
        if (xpos >= world.getWidth() - BALLW || xpos <= 0) xinc = -xinc;
        if (ypos >= world.getHeight() - BALLH || ypos <= 0) yinc = -yinc;

        Thread.sleep(30);
        doMove();
        if (ypos < xpos + BALLH / 2 && ypos > xpos - BALLH / 2 &&
                xpos < ypos + BALLW / 2 && xpos > ypos - BALLW / 2) {
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        world.repaint();
    }

    public synchronized void doMove() {
        xpos += xinc;
        ypos += yinc;

    }

    public synchronized void draw(Graphics g) {
        if (visible) {
            g.setColor(col);
            g.fillOval(xpos, ypos, BALLW, BALLH);
        }
    }
}
