package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

public class Block {
    double x = 1000;
    BufferStrategy bufferStrategy;

    public Block() {

    }

    public void draw() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, 1000, 500);
        g.fillRect((int) x, 0, 50, 100);
        g.dispose();
        bufferStrategy.show();
    }

    public void move(int deltaTime) {
        x -= deltaTime * 0.2;
        while (x < 0) {
            x += 1000;
        }
    }

    public int randomNum() {

    }

    public int getY() {
        return 0;
    }

    public int getX() {
        return 0;
    }

}
