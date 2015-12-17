/**
 * Created by SteveLeeLX on 11/28/15.
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.lang.String;

public class Board extends JPanel implements Runnable, Settings {

    // instance init
    private Dimension d;
    private ArrayList aliens;
    private ArrayList shots;
    private Player player;
    private Thread animator;
    private Random generator = new Random();
    private int speed;
    private int score;

    // init value
    private int direction = -1;
    private BufferedImage background = null;
    private String message;
    private final String expl = "spacepix/explosion.png";
    public Rectangle playButton = new Rectangle(BOARD_WIDTH / 2 + 120, 150, 100, 50);
    public Rectangle helpButton = new Rectangle(BOARD_WIDTH / 2 + 120, 250, 100, 50);
    public Rectangle quitButton = new Rectangle(BOARD_WIDTH / 2 + 120, 350, 100, 50);
    public BufferedImage play;
    public BufferedImage back;
    public BufferedImage settings;
    public BufferedImage help;
    public BufferedImage quit;
    public BufferedImage bgmbutton;
    public Sound sound;

    // game state
    public enum STATE {
        menu,
        ingame,
        score
    }

    public static STATE state = STATE.menu;

    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            background = loader.loadImage("spacepix/background.png");
        }
        catch(IOException e) {e.printStackTrace();}
        gameInit();
        setDoubleBuffered(true);
    }

    public void addNotify() {
        super.addNotify();

        gameInit();
    }

    public void gameInit() {
        speed = 0;
        score = 0;
        aliens = new ArrayList();
        player = new Player(PLAYER_HP);
        shots = new ArrayList();


        if (animator == null) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {

        for (int i=0; i<aliens.size(); i++) {
                Alien alien = (Alien) aliens.get(i);

                if (alien.isVisible()) {
                    if(alien.getImage() == null){
                        BufferedImage img_fix;
                        BufferedImageLoader loader = new BufferedImageLoader();
                        try {
                            img_fix = loader.loadImage("spacepix/Layer 1.png");
                            alien.setImage(img_fix);
                        } catch (IOException e) {e.printStackTrace();}
                    }
                    g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
                }

                if (alien.isDying()) {
                    alien.die();
                }
        }
    }

    public void drawHeart(Graphics g) {
        for (int i=0; i<=player.hp.getHP();i++){
            Player.HP hearts = player.hp;
            g.drawImage(hearts.getImage(), hearts.getX()+(i*hearts.getWidth()), hearts.getY(), this);
        }


    }

    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {
            player.die();
            state = STATE.score;

        }
    }

    public void drawShot(Graphics g) {
        for (Iterator i = shots.iterator();i.hasNext();){
            Shot shot = (Shot) i.next();

            if(shot.isVisible())
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }

    }

    public void drawScore(Graphics g) {
        g.drawString("Score: "+ score, 100, 10);
    }

    public String toString(){
        return "Your Score is: " + score;
    }

    public void drawBombing(Graphics g) {
        Iterator i = aliens.iterator();
        while (i.hasNext()) {
            Alien a = (Alien) i.next();

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);

        if (state == STATE.ingame) {
            g.drawImage(background,0,0,null);
            g.drawLine(0, UPBOUND, BOARD_WIDTH, UPBOUND);
            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            drawHeart(g);
            drawScore(g);
        }

        else if (state == STATE.menu) {
            showMenu(g);
        }

        else {
            showScore(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void showMenu(Graphics g){

        Graphics2D g2d=(Graphics2D) g;




        BufferedImageLoader loader = new BufferedImageLoader();

        //draw menu
        try {
            background = loader.loadImage("spacepix/background.png");

        } catch (IOException e) {
            e.printStackTrace();
        }

        g.drawImage(background, 0, 0, null);
        Font small = new Font("Helvetica", Font.CENTER_BASELINE, 30);
        g.setColor(Color.white);
        g.setFont(small);
        g.setColor(Color.white);
        g.drawRect(100, BOARD_WIDTH / 2 - 40, BOARD_WIDTH - 200, 50);
        g.setColor(Color.white);
        g.drawRect(100, BOARD_WIDTH / 2 + 110, BOARD_WIDTH - 200, 50);
        g.setColor(Color.white);
        g.drawRect(100, BOARD_WIDTH / 2 + 260, BOARD_WIDTH - 200, 50);

        g.drawString("Press 'Alt' to Play", 170, 300);
        g.drawString("Press 'Shift' to Score", 150, 450);
        g.drawString("Press 'Ctrl' to Exit", 170, 600);

        Font fnt = new Font("Arial", Font.BOLD, 60);
        g.setFont(fnt);
        g.setColor(new Color(0, 32, 48));
        g.setColor(Color.white);
        g.drawString("Space Invaders", 70, 100);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void showScore(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        // Title
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 250, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 250, BOARD_WIDTH - 100, 50);

        // Row Title
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 50, BOARD_WIDTH - 500, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 50, BOARD_WIDTH - 500, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 - 50, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 - 50, BOARD_WIDTH - 400, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 - 50, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 - 50, BOARD_WIDTH - 400, 50);

        // Rank 1
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2, BOARD_WIDTH - 500, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2, BOARD_WIDTH - 500, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2, BOARD_WIDTH - 400, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2, BOARD_WIDTH - 400, 50);

        // Rank 2
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 + 50, BOARD_WIDTH - 500, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 + 50, BOARD_WIDTH - 500, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 + 50, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 + 50, BOARD_WIDTH - 400, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 + 50, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 + 50, BOARD_WIDTH - 400, 50);

        // Rank 3
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 + 100, BOARD_WIDTH - 500, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 + 100, BOARD_WIDTH - 500, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 + 100, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 + 100, BOARD_WIDTH - 400, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 + 100, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 + 100, BOARD_WIDTH - 400, 50);

        // Rank 4
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 + 150, BOARD_WIDTH - 500, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 + 150, BOARD_WIDTH - 500, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 + 150, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 + 150, BOARD_WIDTH - 400, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 + 150, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 + 150, BOARD_WIDTH - 400, 50);


        // Rank 5
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 + 200, BOARD_WIDTH - 500, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 + 200, BOARD_WIDTH - 500, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 + 200, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 500 + 50, BOARD_WIDTH / 2 + 200, BOARD_WIDTH - 400, 50);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 + 200, BOARD_WIDTH - 400, 50);
        g.setColor(Color.white);
        g.drawRect(BOARD_WIDTH - 300 + 50, BOARD_WIDTH / 2 + 200, BOARD_WIDTH - 400, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Game Over";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2 - 220);


        g.setColor(Color.white);
        g.setFont(small);
        message = "Your Score is " + score;
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2 - 120);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString("Ranking", (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 180,
                BOARD_WIDTH / 2 - 20);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString("Name", (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 30,
                BOARD_WIDTH / 2 - 20);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString("High Scores", (BOARD_WIDTH - metr.stringWidth(message)) / 2 + 145,
                BOARD_WIDTH / 2 - 20);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Rank1";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 190,
                BOARD_WIDTH / 2 + 30);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Name";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 40,
                BOARD_WIDTH / 2 + 30);

        g.setColor(Color.white);
        g.setFont(small);
        message = "High Scores";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 + 145,
                BOARD_WIDTH / 2 + 30);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Rank2";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 190,
                BOARD_WIDTH / 2 + 80);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Name";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 40,
                BOARD_WIDTH / 2 + 80);

        g.setColor(Color.white);
        g.setFont(small);
        message = "High Scores";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 + 145,
                BOARD_WIDTH / 2 + 80);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Rank3";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 190,
                BOARD_WIDTH / 2 + 130);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Name";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 40,
                BOARD_WIDTH / 2 + 130);

        g.setColor(Color.white);
        g.setFont(small);
        message = "High Scores";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 + 145,
                BOARD_WIDTH / 2 + 130);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Rank4";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 190,
                BOARD_WIDTH / 2 + 180);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Name";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 40,
                BOARD_WIDTH / 2 + 180);

        g.setColor(Color.white);
        g.setFont(small);
        message = "High Scores";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 + 145,
                BOARD_WIDTH / 2 + 180);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Rank5";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 190,
                BOARD_WIDTH / 2 + 230);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Name";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 - 40,
                BOARD_WIDTH / 2 + 230);

        g.setColor(Color.white);
        g.setFont(small);
        message = "High Scores";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2 + 145,
                BOARD_WIDTH / 2 + 230);

        g.setColor(Color.white);
        g.setFont(small);
        message = "Press Key \'Alt\' back to main.";
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2 + 330);
    }

    public void animationCycle() {
        // player
        player.move();

        // player's shot
        for (int i=0; i<shots.size();i++) {
      //      try {
                Shot shot = (Shot) shots.get(i);
                if (shot.isVisible()) {
                    Iterator it = aliens.iterator();
                    int shotX = shot.getX();
                    int shotY = shot.getY();

                    while (it.hasNext()) {
                        Alien alien = (Alien) it.next();
                        int alienX = alien.getX();
                        int alienY = alien.getY();

                        if (alien.isVisible()) {
                            // if you shot the alien
                            if (shotX >= (alienX) &&
                                    shotX <= (alienX + ALIEN_WIDTH) &&
                                    shotY >= (alienY) &&
                                    shotY <= (alienY + ALIEN_HEIGHT)) {
                                // kill the alien
                                ImageIcon icon = new ImageIcon(getClass().getResource(expl));
                                alien.setImage(icon.getImage());
                                alien.setDying(true);
                                // add score
                                score += 100;
                                // reset the player's shot
                                shot.die();
                            }
                        }
                    }
                    shot.move();
                }
          //  } catch (ConcurrentModificationException e) {}
        }

        if((System.currentTimeMillis()) % 50 == 0) {
            Alien alien1 = new Alien();

            aliens.add(alien1);
        }



        // check if the alien reached the ground
        for (Iterator i = aliens.iterator(); i.hasNext();) {
            Alien alien = (Alien) i.next();
            if (alien.isVisible()) {
                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    ImageIcon icon = new ImageIcon(getClass().getResource(expl));
                    alien.setImage(icon.getImage());
                    alien.setDying(true);
                    if (player.hp.getHP() == 0){
                        player.setDying(true);
                    } else {
                        player = new Player(player.hp.getHP() - 1);
                    }
                }
                speed -= .01;
                alien.move(direction + speed);
            }
        }

        // aliens' bomb
        for (Iterator i = aliens.iterator(); i.hasNext();) {
            int shot = generator.nextInt(15);
            Alien a = (Alien) i.next();
            Alien.Bomb b = a.getBomb();
            if (shot == CHANCE && a.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {
                if (bombX >= (playerX) &&
                        bombX <= (playerX + PLAYER_WIDTH) &&
                        bombY >= (playerY) &&
                        bombY <= (playerY + PLAYER_HEIGHT)) {
                    ImageIcon icon = new ImageIcon(this.getClass().getResource(expl));
                    player.setImage(icon.getImage());
                    if (player.hp.getHP() == 0){
                        player.setDying(true);
                    } else {
                        player = new Player(player.hp.getHP() - 1);
                    }
                    b.setDestroyed(true);
                }
            }

            if (!b.isDestroyed()) {
                b.setY(b.getY() + 2);
                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            repaint();
            if (state == STATE.ingame)
                animationCycle();

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

    // game control
    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            if(state == STATE.ingame)
                player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {

            if (state == STATE.menu) {
                if (e.isAltDown()) {
                    state = STATE.ingame;
                    System.out.println(state);
                }
                else if (e.isShiftDown()){
                    state = STATE.score;
                    System.out.println(state);
                }
                else if (e.isControlDown()){
                    System.exit(1);
                }
            }

            if (state == STATE.score) {

                if (e.isAltDown()) {
                    state = STATE.menu;
                    gameInit();
                }
            }

            if (state == STATE.ingame) {
                player.keyPressed(e);

                int x = player.getX();
                int y = player.getY();
                if (e.isAltDown()) {
                    Shot shot = new Shot(x, y);
                    shots.add(shot);
                    sound.hit.play();

                }
            }


        }
    }
}

