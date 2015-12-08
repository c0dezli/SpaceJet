/**
 * Created by SteveLeeLX on 11/28/15.
 */
import javax.swing.ImageIcon;
import java.util.Random;


public class Alien extends Sprite {

    private Bomb bomb;
    private final String alien = "spacepix/Layer 1.png";
    private final String alien2 = "spacepix/Layer 2.png";
    private final String alien3 = "spacepix/Layer 3.png";
    private final String alien4 = "spacepix/Layer 4.png";
    private final String alien5 = "spacepix/Layer 5.png";
    private final String alien6 = "spacepix/Layer 6.png";
    private final String expl = "spacepix/explosion.png";

    public Alien() {
        // Randomly choose the position alien appear
        Random gen = new Random();
        this.x = gen.nextInt(550);
        this.y = 20;

        // Draw the alien
        int key = gen.nextInt(6);
        switch (key) {
            case(1):{
                ImageIcon icon = new ImageIcon(this.getClass().getResource(alien));
                setImage(icon.getImage());
                break;}
            case(2):{
                ImageIcon icon = new ImageIcon(this.getClass().getResource(alien2));
                setImage(icon.getImage());
                break;}
            case(3):{
                ImageIcon icon = new ImageIcon(this.getClass().getResource(alien3));
                setImage(icon.getImage());
                break;}
            case(4):{
                ImageIcon icon = new ImageIcon(this.getClass().getResource(alien4));
                setImage(icon.getImage());
                break;}
            case(5):{
                ImageIcon icon = new ImageIcon(this.getClass().getResource(alien5));
                setImage(icon.getImage());
                break;}
            case(6):{
                ImageIcon icon = new ImageIcon(this.getClass().getResource(alien6));
                setImage(icon.getImage());
                break;}
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