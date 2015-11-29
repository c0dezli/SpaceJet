/**
 * Created by wendongxie on 11/28/15.
 */
import javax.swing.ImageIcon;
public class HP extends Sprite implements Settings{
    private String hearts = "spacepix/Pixel_heart_icon.png";
    private final int START_Y = 0;
    private final int START_X = 0;

    private int width;
    private int HP;
    private int x,y;

    public HP(int HP)
    {
        ImageIcon icon = new ImageIcon(this.getClass().getResource(hearts));
        setImage(icon.getImage());
        width = icon.getImage().getWidth(null);
        x = START_X;
        y = START_Y;
        setHP(HP);
        setImage(icon.getImage());
        setX(x);
        setY(y);
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int n){
        HP = n;
    }

    public int getWidth(){
        return width;
    }
}



