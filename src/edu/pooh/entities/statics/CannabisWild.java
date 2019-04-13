package edu.pooh.entities.statics;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CannabisWild extends StaticEntity {

    private int daysWatered;

    private boolean waterable;
    private boolean harvestable;
    private BufferedImage currentImage;

    public CannabisWild(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        setCurrentImage(Assets.dirtSeed);
        setDaysWatered(0);
        setWaterable(true);
        setHarvestable(false);

        // NO COLLISION WHILE dirtSeed. START COLLISION AT plantSproutling.
        setBoundsWidth(0);
        setBoundsHeight(0);
    } // **** end CannabisWild(Handler, float, float) constructor ****

    @Override
    public void tick() {
        if (daysWatered < 3) {
            return;
        } else if (daysWatered == 3) {
            setCurrentImage(Assets.plantSproutling);

            // START ENTITY COLLISION DETECTION
            setBoundsWidth(width);
            setBoundsHeight(height);
        } else if (daysWatered == 4) {
            setCurrentImage(Assets.plantJuvenille);
        } else if (daysWatered == 5) {
            setCurrentImage(Assets.plantAdult);
        } else if (daysWatered == 6) {
            setCurrentImage(Assets.plantFlowering2);

            setHarvestable(true);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentImage, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        Text.drawString(g, "dayWatered: " + daysWatered, (int)x, (int)y, false, Color.YELLOW, Assets.font28);
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

    public void increaseDaysWatered() {
        daysWatered++;
    }

    public void decreaseDaysWatered() { daysWatered--; }

    // GETTERS & SETTERS

    public boolean getHarvestable() {
        return harvestable;
    }

    public void setHarvestable(boolean harvestable) {
        this.harvestable = harvestable;
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

    public void setDaysWatered(int daysWatered) {
        this.daysWatered = daysWatered;
    }

} // **** end CannabisWild class ****