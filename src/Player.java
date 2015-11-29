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
    private int HP = 5;

    public Player() {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(player));

        width = ii.getImage().getWidth(null);
        height = ii.getImage().getHeight(null);

        setImage(ii.getImage());
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
        if (x >= BOARD_WIDTH - 2*width)
            x = BOARD_WIDTH - 2*width;
        if (y <= 2)
            y = 2;
        if (y >= GROUND - 2*height)
            y = GROUND - 2*height;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP)
        {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN)
        {
            dy = 2;
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