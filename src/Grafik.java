import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Grafik extends Canvas implements Runnable {
    private final int width = 800;
    private final int height = 600;

    private Thread thread;
    int fps = 30;
    private boolean isRunning;

    private BufferStrategy bs;

    private final int gräsX, gräsY;

    private int fågelX, fågelY, fågelVX, fågelVY;



    public Grafik() {
        JFrame frame = new JFrame("Very scuffed flappybird");
        this.setSize(width, height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        frame.setVisible(true);

        isRunning = false;


        gräsX = 300;
        gräsY = 150;



        fågelX = 200;
        fågelY = 200;
        fågelVX = 0;
        fågelVY = 0;

    }

    public void update() {


        fågelX += fågelVX;
        fågelY += fågelVY;
    }

    public void draw() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        update();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        drawGräs(g, gräsX, gräsY);
        drawFågel(g, fågelX, fågelY);
        g.dispose();
        bs.show();
    }



    private void drawFågel(Graphics g, int x, int y) {
        g.setColor(Color.green.darker());
        int[] xs = {0 + x, 10 + x, 20 + x};
        int[] ys = {30 + y, 0 + y, 30 + y};
        g.setColor(new Color(200, 128, 30));
        g.fillRect(7 + x, 30 + y, 6, 10);
    }


    private void drawGräs(Graphics g, int x, int y) {
        g.setColor(Color.green);
        g.fillRect(0, 500, 800, 100);
        g.setColor(new Color(0x444444));
        int[] xcoords = {x - 5, x + 25, x + 55};
        int[] ycoords = {y - 40, y - 65, y - 40};

    }

    private void drawRör(Graphics g, int x, int y) {
        g.setColor(Color.green.darker());
        g.fillRect(0, 500, 200, 300);
        int[] xcoords = {x - 5, x + 25, x + 55};
        int[] ycoords = {y - 40, y - 65, y - 40};
    }


        public static void main(String[] args) {
        Grafik painting = new Grafik();
        painting.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double deltaT = 1000.0 / fps;
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long now = System.currentTimeMillis();
            if (now - lastTime > deltaT) {
                update();
                draw();
                lastTime = now;
            }

        }
        stop();
    }

    private class KL implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == 'a') {
                fågelVX = -5;
            }
            if (keyEvent.getKeyChar() == 'd') {
                fågelVX = 5;
            }
            if (keyEvent.getKeyChar() == 'w') {
                fågelVY = -5;
            }
            if (keyEvent.getKeyChar() == 's') {
                fågelVY = 5;
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                fågelVX = -5;
            }
            if (keyEvent.getKeyChar() == KeyEvent.VK_RIGHT) {
                fågelVX = 5;
            }
            if (keyEvent.getKeyChar() == KeyEvent.VK_UP) {
                fågelVY = -5;
            }
            if (keyEvent.getKeyChar() == KeyEvent.VK_DOWN) {
                fågelVY = 5;
            }
        }


        @Override
        public void keyReleased(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == 'a') {
                fågelVX = 0;
            }
            if (keyEvent.getKeyChar() == 'd') {
                fågelVX = 0;
            }
            if (keyEvent.getKeyChar() == 'w') {
                fågelVY = 0;
            }
            if (keyEvent.getKeyChar() == 's') {
                fågelVY = 0;
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                fågelVX = -0;
            }
            if (keyEvent.getKeyChar() == KeyEvent.VK_RIGHT) {
                fågelVX = 0;
            }
            if (keyEvent.getKeyChar() == KeyEvent.VK_UP) {
                fågelVY = -0;
            }
            if (keyEvent.getKeyChar() == KeyEvent.VK_DOWN) {
                fågelVY = 0;

            }

        }
    }
}