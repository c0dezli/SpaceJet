/**
 * Created by SteveLeeLX on 11/28/15.
 */

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class Player extends Sprite implements Settings {

    private final int START_Y = 770;
    private final int START_X = 270;

    private BufferedImage player;
    private int width;
    private int height;
    public HP hp;
    public int speed = 4;

    //constructor
    public Player(int HP) {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            player = loader.loadImage("spacepix/player-1.png");
            width = player.getWidth(null);
            height = player.getHeight(null);
            setImage(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hp = new HP(HP);
        setX(START_X);
        setY(START_Y);
    }

    public void move() {
        x += dx;
        y += dy;
        if (x <= 2)
            x = 2;
        if (x >= BOARD_WIDTH - width)
            x = BOARD_WIDTH - width;
        if (y <= 2)
            y = 2;
        if (y >= GROUND - height) {
            y = GROUND - height;
        }
        if (y <= UPBOUND) {
            y = UPBOUND;
        }
    }

    public void setSpeed(int new_speed) {
        this.speed = new_speed;
    }

    public void setHP(int new_hp) { this.hp = new HP(new_hp); }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -this.speed;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = this.speed;
        }
        if (key == KeyEvent.VK_UP) {
            dy = -this.speed;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = this.speed;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }



    public class HP extends Sprite implements Settings {
        private String hearts = "spacepix/Pixel_heart_icon.png";
        private final int START_Y = 0;
        private final int START_X = 0;

        private int width;
        private int HP;
        private int x, y;

        public HP(int HP) {
            ImageIcon icon = new ImageIcon(this.getClass().getResource(hearts));
            setImage(icon.getImage());
            width = icon.getImage().getWidth(null);
            x = START_X;
            y = START_Y;
            setHP(HP);
            setImage(icon.getImage());
            setX(x);
            setY(y);
        }

        public int getHP() {
            return HP;
        }

        public void setHP(int n) {
            HP = n;
        }

        public int getWidth() {
            return width;
        }
    }


}