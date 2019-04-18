package edu.pooh.entities.statics.crops;

import edu.pooh.entities.statics.harvests.NuggetWild;
import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * TODO: Move responsibility of tracking waterable from this singular concrete subclass of StaticEntity
 * to the abstract class named Tile. Set up an observer design-pattern between Tile class and StaticEntity
 * class. Inside the tile's setStaticEntity(StaticEntity) method, have the tile call its
 * registerObserver() method to add the newly composed StaticEntity object to the tile's list of Observer.
 * Add an instance variable to Tile class named boolean watered. Whenever the state of the tile's watered
 * variable is set to true, call tile's (Subject) notifyObservers() method, which traverse its collection
 * of Observer objects to call their update() method.
 */
public class CannabisWild extends StaticEntity {

    // TODO: THIS CLASS CAN BECOME AN abstract CropEntity class !!!!!!!!!!!!!!!

    private int daysWatered;

    private boolean waterable;
    private boolean harvestable;
    private BufferedImage currentImage;

    public CannabisWild(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        setCurrentImage(Assets.dirtSeedsDry);
        setDaysWatered(0);
        setWaterable(true);
        setHarvestable(false);

        // NO COLLISION WHILE dirtSeed. START COLLISION AT plantSproutling.
        setBoundsWidth(0);
        setBoundsHeight(0);
    } // **** end CannabisWild(Handler, float, float) constructor ****

    @Override
    public void tick() {
        if (active) {
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
            } else {
                setHarvestable(true);
            }

            if (harvestable) {
                die();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentImage, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        Text.drawString(g, "dayWatered: " + daysWatered, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), false, Color.YELLOW, Assets.font28);
    }

    @Override
    public void die() {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(
                new NuggetWild(handler, x + (Tile.TILE_WIDTH * 0.25f), y + (Tile.TILE_HEIGHT * 0.25f)));
        handler.getWorld().getEntityManager().setToBeAdded(true);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        Tile[][] tempLevel = handler.getWorld().getTilesViaRGB();
        DirtNormalTile tempDirtNormalTile;
        for (int xx = 0; xx < handler.getWorld().getWidth(); xx++) {
            for (int yy = 0; yy < handler.getWorld().getHeight(); yy++) {
                if (tempLevel[xx][yy] instanceof DirtNormalTile) {
                    tempDirtNormalTile = (DirtNormalTile)tempLevel[xx][yy];

                    if (tempDirtNormalTile.getStaticEntity() == this) {
                        tempDirtNormalTile.setTexture(Assets.dirtNormal);
                        tempDirtNormalTile.setDirtState(DirtNormalTile.DirtState.NORMAL);

                        setActive(false);
                        tempDirtNormalTile.setStaticEntity(null);
                    }
                }
            }
        }
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