import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by SteveLeeLX on 12/18/15.
 */
public class Supply extends Sprite{

    private BufferedImage img;

    public boolean isHeart = false;
    public boolean isSpeed = false;

    public Supply() {
        Random gen = new Random();
        this.x = gen.nextInt(550);
        this.y = 20;

        BufferedImageLoader loader = new BufferedImageLoader();
        int x = gen.nextInt(3);

        if (x==1) {
            this.isHeart = true;
            try {
                img = loader.loadImage("spacepix/Health.png");
                setImage(img);

            } catch (IOException e) { e.printStackTrace(); }
        } else {
            this.isSpeed = true;
            try {
                img = loader.loadImage("spacepix/speed.png");
                setImage(img);
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void move(int direction) {
        this.y -= direction;
    }


}