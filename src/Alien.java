/**
 * Created by SteveLeeLX on 11/28/15.
 */
import javax.swing.ImageIcon;
import java.util.Random;


public class Alien extends Sprite {

    private Bomb bomb;
    private final String alien = "spacepix/alien.png";
    private final String alien2 = "spacepix/alien2.png";
    private final String expl = "spacepix/explosion.png";

    public Alien() {
        // Randomly choose the position alien appear
        Random gen = new Random();
        this.x = gen.nextInt(550);
        this.y = 20;

        // Draw the alien
        if (gen.nextInt(10) % 2 == 0) {
            ImageIcon icon = new ImageIcon(this.getClass().getResource(alien));
            setImage(icon.getImage());
        } else {
            ImageIcon icon = new ImageIcon(this.getClass().getResource(alien2));
            setImage(icon.getImage());
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
        ImageIcon icon = new ImageIcon(this.getClass().getResource(expl));
        setImage(icon.getImage());
        super.die();
    }

    public class Bomb extends Sprite {

        private final String bomb = "spacepix/bomb.png";
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