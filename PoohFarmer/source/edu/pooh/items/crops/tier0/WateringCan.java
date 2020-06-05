package edu.pooh.items.crops.tier0;

import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

public class WateringCan extends Item {

    private static WateringCan uniqueInstance = new WateringCan();

    private int countWater;
    private int countWaterMax;

    private WateringCan() {
        super(Assets.wateringCan, "Watering Can", ID.WATERING_CAN);
        countWater = 0;
        countWaterMax = 20;
    } // **** end WateringCan() singleton-pattern constructor ****

    public static synchronized WateringCan getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    public int getCountWater() {
        return countWater;
    }

    public void increaseCountWater(int count) {
        //if increasing countWater by argument (count) surpasses countWaterMax, set countWater to countWaterMax.
        if ((countWater + count) <= countWaterMax) {
            countWater += count;
        } else {
            countWater = countWaterMax;
        }
    }

    public void resetCountWater() { countWater = 0; }

    @Override
    public void resetTexture() {
        texture = Assets.wateringCan;
    }

    @Override
    public void execute() {
        Tile t = handler.getWorld().getEntityManager().getPlayer().getTileCurrentlyFacing();

        if (t != null) {
            System.out.println("WateringCan.execute(), targeted-tile: " + t.getId());
            handler.getWorld().getEntityManager().getPlayer().getStaminaModule().decreaseStaminaCurrent(2);
            System.out.println("WateringCan.execute(), player's stamina decrease by 2");

            // If tile is poolWater, increase countWater by 18.
            if (t.getId() >= 236 && t.getId() <= 248) {
                ////////////////////////
                increaseCountWater(20);
                ////////////////////////
            } else if (countWater > 0) {
                //////////////
                countWater--;
                //////////////
                //It's DirtNormalTile and is not watered.
                if ( t instanceof DirtNormalTile ) {
                    DirtNormalTile tempTile = (DirtNormalTile)t;
                    //DirtState.SEEDED
                    if ((tempTile.getDirtState() == DirtNormalTile.DirtState.SEEDED)) {
                        tempTile.setWatered(true);

                        if (tempTile.getStaticEntity() instanceof CropEntity) {
                            CropEntity tempCropEntity = (CropEntity)tempTile.getStaticEntity();
                            ///////////////////////////
                            tempCropEntity.swapCurrentImageDryToWet();
                            ///////////////////////////
                        }

                        tempTile.setTexture(Assets.dirtSeededWatered);
                        System.out.println("tile with DirtState.SEEDED has watered set to true");
                    }
                    //DirtState.TILLED
                    else if ((tempTile.getDirtState() == DirtNormalTile.DirtState.TILLED)) {
                        tempTile.setWatered(true);
                        tempTile.setTexture(Assets.dirtTilledWatered);
                        System.out.println("tile with DirtState.TILLED has watered set to true");
                    }
                }
            }
        }
    }



} // **** end WateringCan class ****