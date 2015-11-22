import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by SteveLeeLX on 11/22/15.
 */

public class RunningMan {
    public static void main(String[] args)
    {
        GameFrame frame = new GameFrame();
    }

}

class GameFrame extends JFrame {
    //Window Size
    final int WIDTH = 1000;
    final int HEIGHT = 500;

    public GameFrame(){
        setTitle("Running Man");
        JPanel panel = (JPanel) this.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        Canvas canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);

        canvas.createBufferStrategy(2);
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();

        canvas.requestFocus();
    }
}