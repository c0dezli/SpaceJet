/**
 * Created by wendongxie on 11/28/15.
 */
import javax.swing.ImageIcon;
public class HP extends Sprite implements Settings{
    private String hearts = "spacepix/Pixel_heart_icon.png";
    private final int START_Y = 0;
    private final int START_X = 0;

    private int width;
    private int height;
    private int HP;
    private int x,y;
    public  HP(int HP)
    {
        ImageIcon iii = new ImageIcon(this.getClass().getResource(hearts));
        setImage(iii.getImage());
        width = iii.getImage().getWidth(null);
        x = START_X;
        y = START_Y;
        setHP(HP);
        setImage(iii.getImage());
        setX(x);
        setY(y);
    }

    public int getHP() {
        return HP;
    }
    public void setHP(int n){
        HP = n;
    }
}



