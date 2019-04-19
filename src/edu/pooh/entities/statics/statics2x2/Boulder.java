package edu.pooh.entities.statics.statics2x2;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Boulder extends StaticEntity {

    private BufferedImage[][] texture;

    public Boulder(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);

        texture = Assets.boulder2x2;
    } // **** end Boulder(Handler, float, float, int, int) constructor ****

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void die() {

    }

} // **** end Boulder class ****