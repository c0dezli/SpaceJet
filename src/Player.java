/**
 * Created by SteveLeeLX on 11/28/15.
 */
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


public class Player extends Sprite implements Settings{

    private final int START_Y = 770;
    private final int START_X = 270;

    private final String player = "spacepix/player.png";
    private int width;
    private int height;
    private int HP;
    private int speed = 3;

    public Player(int HP) {
        ImageIcon icon = new ImageIcon(this.getClass().getResource(player));
        width = icon.getImage().getWidth(null);
        height = icon.getImage().getHeight(null);
        setHP(HP);
        setImage(icon.getImage());
        setX(START_X);
        setY(START_Y);
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int n) {
        HP = n;
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

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = -this.speed;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = this.speed;
        }
        if (key == KeyEvent.VK_UP)
        {
            dy = -this.speed;
        }

        if (key == KeyEvent.VK_DOWN)
        {
            dy = this.speed;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 0;
        }
        if (key == KeyEvent.VK_UP)
        {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN)
        {
            dy = 0;
        }
    }

}