package edu.pooh.main;

import edu.pooh.gfx.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DogTest extends JPanel {

    JFrame frame;
    private BufferedImage spriteSheetDog;

    public DogTest() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(300, 300));
        frame.setLocationRelativeTo(null);

        spriteSheetDog = ImageLoader.loadImage("/textures/entities/SNES - Harvest Moon - Kero Dog.png");

        frame.setContentPane(this);

        frame.setVisible(true);

        init();
    }

    public static void main(String[] args) {
        new DogTest();
    }

    long elapsedTime = 0;
    long nowTime;
    long lastTime = 0;
    public void init() {
        while (true) {


            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 14; j++) {
                    lastTime = System.currentTimeMillis();

                    while (elapsedTime < 400) {
                        nowTime = System.currentTimeMillis();
                        elapsedTime += nowTime - lastTime;
                        lastTime = nowTime;
                    }

                    row = i;
                    col = j;
                    elapsedTime = 0;

                    repaint();
                }
            }


        }
    }
    BufferedImage currentImage = null;

    int x = 30;
    int y = 28;
    int width = 30;
    int height = 28;
    int col = 0;
    int row = 0;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int tempX = x * col;
        int tempY = y * row;

        if (tempX > 413-30) {
            tempX = 413-30;
        }
        if (tempY > 141-28) {
            tempY = 141-28;
        }
        currentImage = spriteSheetDog.getSubimage(tempX, tempY, width, height);

        g.drawImage(currentImage, 0, 0, 150, 150, null);
        /*
        int x = 0;
        int y = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 14;) {
                g.drawImage(spriteSheetDog.getSubimage(x * j, y * i, 29, 28), 17, 17, null);
                x += 29;

                long elapsedTime = 0;
                long nowTime;
                long lastTime = System.currentTimeMillis();
                while (elapsedTime < 400) {
                    nowTime = System.currentTimeMillis();
                    elapsedTime += nowTime - lastTime;
                    lastTime = nowTime;
                }
            }
            y += 28;
            x = 0;
        }
    }
    */
    }
}
