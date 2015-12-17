import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;

public class Menu extends JPanel implements Runnable, Settings{

    Board board;

    public Rectangle playButton = new Rectangle(board.WIDTH / 2 + 120, 150, 100, 50);
    public Rectangle helpButton = new Rectangle(board.WIDTH / 2 + 120, 250, 100, 50);
    public Rectangle quitButton = new Rectangle(board.WIDTH / 2 + 120, 350, 100, 50);
    public BufferedImage play;
    public BufferedImage back;
    public BufferedImage settings;
    public BufferedImage help;
    public BufferedImage quit;
    public BufferedImage bgmbutton;


    private Dimension d;
    private BufferedImage background = null;

    private boolean ingame = true;
    private Thread animator;
    private Random generator = new Random();

    public Menu() {
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            background = loader.loadImage("spacepix/background.png");
        }
        catch(IOException e){e.printStackTrace();}
        menuInit();
        setDoubleBuffered(true);
    }

    public void addNotify() {
        super.addNotify();
        menuInit();
    }

    public void menuInit() {
        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
    }


    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        repaint();

        while (ingame) {
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

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            play = loader.loadImage("/Play.png");
            help = loader.loadImage("/Help.png");
            settings = loader.loadImage("/Settings.png");
            back = loader.loadImage("/Back.png");
            quit = loader.loadImage("/Quit.png");
            bgmbutton = loader.loadImage("/Sound-button-icon.png");

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ingame) {
            g.drawImage(play, 100, 160, null);
            g.drawImage(settings, 100, 230, null);
            g.drawImage(help, 100, 300, null);
            g.drawImage(quit, 100, 370, null);

            g.drawImage(bgmbutton, 550, 400, null);

            Font fnt = new Font("Curlz MT", Font.BOLD, 60);
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Space Invaders", 70, 100);

            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        }
    }

}
