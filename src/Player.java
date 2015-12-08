/**
 * Created by SteveLeeLX on 11/28/15.
 */
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends Sprite implements Settings{

    private final int START_Y = 770;
    private final int START_X = 270;

    private BufferedImage player;
    private int width;
    private int height;
    private int HP;
    private int speed = 3;

    //constructor
    public Player(int HP) {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            player = loader.loadImage("spacepix/player-1github.png");
            width = player.getWidth(null);
            height = player.getHeight(null);
            setImage(player);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setHP(HP);
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

    public class Supply extends Sprite{

        private final String heart = "spacepix/alien.png";
        private final String speed = "spacepix/alien2.png";
        private final String attack = "spacepix/explosion.png";

        private boolean isHeart = false;
        private boolean isSpeed = false;
        private boolean isAttack = false;

        public Supply() {

        }

        public void getHeart() {

        }


    }


}