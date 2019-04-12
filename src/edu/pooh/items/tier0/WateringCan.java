package edu.pooh.items.tier0;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

public class WateringCan extends Item {

    private static WateringCan uniqueInstance = new WateringCan();

    private int countWater = 0;

    private WateringCan() {
        super(Assets.wateringCan, "Watering Can", ID.WATERING_CAN);
    } // **** end WateringCan() singleton-pattern constructor ****

    public static WateringCan getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    public int getCountWater() {
        return countWater;
    }

    public void increaseCountWater(int count) {
        countWater += count;
    }

    @Override
    public void execute() {
        Tile t = handler.getWorld().getEntityManager().getPlayer().getTileCurrentlyFacing();

        if (t != null) {
            System.out.print("targeted-tile's id: " + t.getId());

            // If tile is poolWater, increase countWater by 18.
            if (t.getId() >= 236 && t.getId() <= 248) {
                increaseCountWater(18);
            } else if (t.getId() == 0 && (t.getTexture() == Assets.dirtSeed) && countWater > 0) {
                t.setTexture(Assets.waterFX);
                countWater--;
            }
        }

        System.out.println("Executed WateringCan.");
    }

} // **** end WateringCan class ****