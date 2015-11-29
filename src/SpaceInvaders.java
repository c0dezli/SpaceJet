/**
 * Created by SteveLeeLX on 11/28/15.
 */
//package final_project;

import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Settings {

    public SpaceInvaders()
    {
        add(new Board());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new SpaceInvaders();
    }
}