/**
 * Created by SteveLeeLX on 11/28/15.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel implements Runnable, Settings {

    private Dimension d;
    private ArrayList aliens;
    private Player player;
    private ArrayList shots;
    private HP hearts;

    private int alienX = 150;
    private int alienY = 5;
    private int direction = -1;

    private boolean ingame = true;
    private final String expl = "spacepix/explosion.png";
    private final String alienpix = "spacepix/alien.png";
    private final String heart = "spacepix/Pixel_heart_icon.png";
    private String message = "Game Over";

    private Thread animator;


    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    public void addNotify() {
        super.addNotify();
        gameInit();
    }

    public void gameInit() {
        long tStart = System.currentTimeMillis();
        aliens = new ArrayList();

        ImageIcon icon = new ImageIcon(this.getClass().getResource(alienpix));

        Alien alien = new Alien();
        alien.setImage(icon.getImage());
        aliens.add(alien);
        
        ImageIcon iii = new ImageIcon(this.getClass().getResource(heart));

        player = new Player(PLAYER_HP);

        shots = new ArrayList();
        //shot = new Shot();

        hearts = new HP(PLAYER_HEARTS);
        hearts.setImage(iii.getImage());





        if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {
        Iterator it = aliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();

            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {
                alien.die();
            }
        }
    }
    public void drawHeart(Graphics g) {
        for (int i=0; i<=player.getHP();i++){
            g.drawImage(hearts.getImage(), hearts.getX()+(i*15), hearts.getY(), this);
        }


    }
    public void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {
            player.die();
            ingame = false;
        }
    }

    public void drawShot(Graphics g) {
        Iterator it = shots.iterator();
        while (it.hasNext()){
            Shot shot = (Shot) it.next();

            if(shot.isVisible())
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }

    }

    public void drawBombing(Graphics g) {

        Iterator i3 = aliens.iterator();

        while (i3.hasNext()) {
            Alien a = (Alien) i3.next();

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (ingame) {

            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            drawHeart(g);



        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {

        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
        // game reset
    }

    public void animationCycle(long timeDiff) {

        // player

        player.move();
        // player.score = (int) timeDiff/10000;

        // player's shot
        for (Iterator i = shots.iterator(); i.hasNext();) {
            Shot shot = (Shot) i.next();
            if (shot.isVisible()) {
                Iterator it = aliens.iterator();
                int shotX = shot.getX();
                int shotY = shot.getY();

                while (it.hasNext()) {
                    Alien alien = (Alien) it.next();
                    int alienX = alien.getX();
                    int alienY = alien.getY();

                    if (alien.isVisible() && shot.isVisible()) {
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
                            player.score += 100;
                            // reset the player's shot
                            shot.die();
                        }
                    }
                }

                // shot move
                int y = shot.getY();
                y -= 4;

                // if shot move to the upper bound, reset it
                if (y < 0)
                    shot.die();
                else shot.setY(y);
            }
        }

        // aliens

        Iterator it1 = aliens.iterator();

        while (it1.hasNext()) {
            Alien a1 = (Alien) it1.next();
            int x = a1.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
                Iterator i1 = aliens.iterator();
                while (i1.hasNext()) {
                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;

                Iterator i2 = aliens.iterator();
                while (i2.hasNext()) {
                    Alien a = (Alien) i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }


        Iterator it = aliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    ingame = false;
                    message = "Invasion!";
                    // game reset
                }

                alien.move(direction);
            }
        }

        // aliens' bomb

        Iterator i3 = aliens.iterator();
        Random generator = new Random();

        while (i3.hasNext()) {
            int shot = generator.nextInt(15);
            Alien a = (Alien) i3.next();
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
                    ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
                    player.setImage(ii.getImage());
                    if (player.getHP() == 0){
                        player.setDying(true);
                    } else {
                        player = new Player(player.getHP() - 1);
                    }
                    b.setDestroyed(true);
                }
            }

            if (!b.isDestroyed()) {
                b.setY(b.getY() + 1);
                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {
            timeDiff = System.currentTimeMillis() - beforeTime;

            repaint();
            animationCycle(timeDiff);

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
        gameOver();
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            if (ingame) {
                if (e.isAltDown()) {
                    Shot shot = new Shot(x, y);
                    shots.add(shot);

                }
            }
        }
    }

}