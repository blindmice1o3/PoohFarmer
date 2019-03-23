package edu.pooh.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage image;

    public SpriteSheet(String path) {
        image = loadImage(path);
    }

    private BufferedImage loadImage(String path) {

        //////////////////////////////////////////////////////////////
        return null;
    }

    public BufferedImage cropSpriteSheet(int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }

} // **** end SpriteSheet class ****