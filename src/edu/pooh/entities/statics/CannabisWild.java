package edu.pooh.entities.statics;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CannabisWild extends StaticEntity {

    private int daysWatered;
    private boolean waterable;
    private BufferedImage currentImage;

    public CannabisWild(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        currentImage = Assets.dirtSeed;
        daysWatered = 0;
        waterable = true;
    } // **** end CannabisWild(Handler, float, float) constructor ****

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentImage, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);

        //g.drawImage(Assets.plantFlowering2, (int)(x - handler.getGameCamera().getxOffset()),
        //        (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() {

    }

    @Override
    public void hurt(int amt) {
        return;
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(BufferedImage texture) {
        currentImage = texture;
    }

    public boolean getWaterable() {
        return waterable;
    }

    public void setWaterable(boolean waterable) {
        this.waterable = waterable;
    }

    public int getDaysWatered() {
        return daysWatered;
    }

    public void increaseDaysWatered() {
        daysWatered++;
    }

} // **** end CannabisWild class ****