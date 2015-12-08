import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu {

    Board board;

    public Rectangle playButton = new Rectangle(board.WIDTH / 2 + 120, 150, 100, 50);
    public Rectangle helpButton = new Rectangle(board.WIDTH / 2 + 120, 250, 100, 50);
    public Rectangle quitButton = new Rectangle(board.WIDTH / 2 + 120, 350, 100, 50);
    public BufferedImage play;
    public BufferedImage back;
    public BufferedImage settings;
    public BufferedImage help;
    public BufferedImage quit;
    public BufferedImage bgmbutton;


    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            play = loader.loadImage("/Play.png");
            help = loader.loadImage("/Help.png");
            settings = loader.loadImage("/Settings.png");
            back = loader.loadImage("/Back.png");
            quit = loader.loadImage("/Quit.png");
            bgmbutton = loader.loadImage("/Sound-button-icon.png");

        } catch (IOException e) {
            e.printStackTrace();
        }

        g.drawImage(play, 100, 160, null);
        g.drawImage(settings, 100, 230, null);
        g.drawImage(help, 100, 300, null);
        g.drawImage(quit, 100, 370, null);

        g.drawImage(bgmbutton, 550, 400, null);

        Font fnt = new Font("Curlz MT", Font.BOLD, 60);
        g.setFont(fnt);
        g.setColor(Color.white);
        g.drawString("Space Invaders", 70, 100);

    }

}
