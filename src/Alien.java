/**
 * Created by SteveLeeLX on 11/28/15.
 */
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class Alien extends Sprite {

    private Bomb bomb;
    private BufferedImage alien;
    private BufferedImage alien2;
    private BufferedImage alien3;
    private BufferedImage alien4;
    private BufferedImage alien5;
    private BufferedImage alien6;
    private BufferedImage expl;


    public Alien() {
        // Randomly choose the position alien appear
        Random gen = new Random();
        this.x = gen.nextInt(550);
        this.y = 20;

        // Draw the alien
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            alien = loader.loadImage("spacepix/Layer 1.png");
            alien2 = loader.loadImage("spacepix/Layer 2.png");
            alien3 = loader.loadImage("spacepix/Layer 3.png");
            alien4 = loader.loadImage("spacepix/Layer 4.png");
            alien5 = loader.loadImage("spacepix/Layer 5.png");
            alien6 = loader.loadImage("spacepix/Layer 6.png");
            expl = loader.loadImage("spacepix/explosion.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int key = gen.nextInt(6);
        switch (key) {
            case(1):{setImage(alien);break;}
            case(2):{setImage(alien2);break;}
            case(3):{setImage(alien3);break;}
            case(4):{setImage(alien4);break;}
            case(5):{setImage(alien5);break;}
            case(6):{setImage(alien6);break;}
        }

        // Load the BOMB!
        bomb = new Bomb(this.x, this.y);
    }

    public void move(int direction) {
        this.y -= direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void die() {
        setImage(expl);
        super.die();
    }

    public class Bomb extends Sprite {

        private final String bomb = "spacepix/missle.png";
        private boolean destroyed;

        public Bomb(int x, int y) {
            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon icon = new ImageIcon(this.getClass().getResource(bomb));
            setImage(icon.getImage());
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }
    }
}