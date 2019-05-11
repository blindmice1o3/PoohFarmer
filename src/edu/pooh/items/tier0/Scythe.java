package edu.pooh.items.tier0;

import edu.pooh.entities.Entity;
import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.entities.statics.produce_yields.HarvestEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

public class Scythe extends Item {

    private static Scythe uniqueInstance = new Scythe();

    private Scythe() {
        super(Assets.scythe, "Scythe", ID.SCYTHE);
    } // **** end Scythe() singleton-pattern constructor ****

    public static synchronized Scythe getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {
        Entity entity = handler.getWorld().getEntityManager().getPlayer().getEntityCurrentlyFacing();

        if ((entity instanceof CropEntity) && (((CropEntity)entity).isTangibleToScythe())) {
            System.out.print("Scythe.execute(), targeted-entity: " + entity);

            // Scythe sets grass's daysWatered to 0, currentImage to Assets.grassSeeded, harvestable to false,
            // and tangibleToScythe to false.
            if (((CropEntity) entity).getCropType() == CropEntity.CropType.GRASS) {
                CropEntity tempGrass = (CropEntity) entity;

                ///////////////////////////////////////////////////
                //TODO: increment hay/feed/fodder.

                tempGrass.setDaysWatered(0);
                tempGrass.setCurrentImage(Assets.grassSeeded);
                tempGrass.setHarvestable(false);
                tempGrass.setTangibleToScythe(false);
                ///////////////////////////////////////////////////
            } else {
                //TODO: call special unimplemented version of the CropEntity's die() that removes it without dropping HarvestEntity.
            }
        }
    }

} // **** end Scythe class ****