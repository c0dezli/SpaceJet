/**
 * Created by SteveLeeLX on 11/28/15.
 */
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Shot extends Sprite {

    private BufferedImage shot;
    private final int H_SPACE = 15;
    private final int V_SPACE = 10;

    public Shot(int x, int y) {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            shot = loader.loadImage("spacepix/shot.png");
            setImage(shot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }

    public void move() {
        // shot move
        int y = this.getY();
        y -= 4;

        // if shot move to the upper bound, reset it
        if (y < 0)
            this.die();
        else this.setY(y);
    }
}