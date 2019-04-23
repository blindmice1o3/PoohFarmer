package edu.pooh.items.tier0;

import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class WateringCan extends Item {

    private static WateringCan uniqueInstance = new WateringCan();

    private int countWater = 0;

    private WateringCan() {
        super(Assets.wateringCan, "Watering Can", ID.WATERING_CAN);
    } // **** end WateringCan() singleton-pattern constructor ****

    public static synchronized WateringCan getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    public int getCountWater() {
        return countWater;
    }

    public void increaseCountWater(int count) {
        countWater += count;
    }

    public void resetCountWater() { countWater = 0; }

    @Override
    public void execute() {
        Tile t = handler.getWorld().getEntityManager().getPlayer().getTileCurrentlyFacing();

        if (t != null) {
            System.out.println("targeted-tile's id: " + t.getId());

            // If tile is poolWater, increase countWater by 18.
            if (t.getId() >= 236 && t.getId() <= 248) {
                increaseCountWater(18);
            } else if (countWater > 0) {

                countWater--;

                if (t instanceof DirtNormalTile && ((DirtNormalTile) t).getStaticEntity() != null) {
                    DirtNormalTile tempTile = (DirtNormalTile)t;

                    if (tempTile.getStaticEntity() instanceof CropEntity) {
                        CropEntity tempStaticEntity = (CropEntity) tempTile.getStaticEntity();
                        System.out.println("Prior days watered: " + tempStaticEntity.getDaysWatered());

                        if (tempStaticEntity.getWaterable()) {

                            if (tempStaticEntity.getDaysWatered() == 2) {
                                Rectangle tempCannabisWildRect = new Rectangle(
                                        (int) tempStaticEntity.getX() * Tile.TILE_WIDTH,
                                        (int) tempStaticEntity.getY() * Tile.TILE_HEIGHT,
                                        tempStaticEntity.getWidth(), tempStaticEntity.getHeight());

                                // PREVENT BUG OF player-stuck-due-to-entity-collision.
                                if (handler.getWorld().getEntityManager().getPlayer().
                                        getCollisionBounds(0, 0).
                                        intersects(tempCannabisWildRect)) {
                                    return;
                                }
                            }

                            ////////////////////////////////////////////////////////////////////
                            tempStaticEntity.increaseDaysWatered();
                            ////////////////////////////////////////////////////////////////////
                            //debugging    tempStaticEntity.setWaterable(false);
                            //debugging    tempStaticEntity.setCurrentImage(Assets.waterFX);
                            System.out.println("Current days watered: " + tempStaticEntity.getDaysWatered());
                        }
                    }
                }

            }
        }

        System.out.println("Executed WateringCan.");
    }

} // **** end WateringCan class ****