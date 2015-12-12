/**
 * Created by SteveLeeLX on 11/28/15.
 */
//package final_project;

import javax.swing.*;

public class SpaceInvaders extends JFrame implements Settings {
    private static int noGamesPlayed = 0;
    private boolean ingame = true;
    public SpaceInvaders()
    {
        if (!ingame){
            add(new Menu());
            setTitle("Space Invaders");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(BOARD_WIDTH, BOARD_HEIGHT);
            setLocationRelativeTo(null);
            setVisible(true);
            setResizable(false);
        }   else {
            add(new Board());
            setTitle("Space Invaders");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(BOARD_WIDTH, BOARD_HEIGHT);
            setLocationRelativeTo(null);
            setVisible(true);
            setResizable(false);
        }
    }

    public static void updateGamesPlayed() {
        noGamesPlayed ++;
    }
    public void reset(){
        SpaceInvaders.this.setVisible(false);
        SpaceInvaders.this.dispose();
        updateGamesPlayed();
        new SpaceInvaders();
    }
    public static void main(String[] args) {
        new SpaceInvaders();

    }
}