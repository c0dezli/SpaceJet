/**
 * Created by SteveLeeLX on 11/28/15.
 */
import javax.swing.ImageIcon;
import java.util.Random;


public class Alien extends Sprite {

    private Bomb bomb;
    private final String shot = "spacepix/alien.png";

    public Alien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        ImageIcon icon = new ImageIcon(this.getClass().getResource(shot));
        setImage(icon.getImage());

    }

    public void generate(int direction) {

    }

    public void move(int direction) {
        this.y -= direction;
    }

    public Bomb getBomb() {
        return bomb;
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