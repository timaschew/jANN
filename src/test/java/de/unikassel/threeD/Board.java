package de.unikassel.threeD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Board extends JPanel implements Runnable {

    private Thread animator;
    private int x = 10;
    private int y = 10;

    private final int DELAY = 50;
	private int rectX = 300;
	private int rectY = 200; 
	
	private int speedX = 2;
	private int speedY = 2;
	private long prevPaintTime;


    public Board() {
    	prevPaintTime = System.currentTimeMillis();
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
    }

    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        long fps = System.currentTimeMillis() - prevPaintTime;

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRect(0, 0, rectX, rectY);
        g2d.drawOval(x, y, 20, 20);
        g2d.drawString("FPS: "+fps, getWidth()-50, 20);
//        g2d.drawImage(star, x, y, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        
        prevPaintTime = System.currentTimeMillis();
    }


    public void cycle() {
        if (y < 0 || y > rectY) {
            speedY *= -1;
        }
        
        if (x < 0 || x > rectX) {
        	 speedX *= -1;
        }
    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            cycle();
            x += speedX;
            y += speedY;
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
           
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}
