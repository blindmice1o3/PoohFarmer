package edu.pooh.items.tier0;

import edu.pooh.entities.Entity;
import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.entities.statics.statics1x1.Bush;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;

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
        System.out.print("Scythe.execute(), targeted-entity: " + entity);

        // CropEntity (except CropType.GRASS) @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        if ((entity instanceof CropEntity) && (((CropEntity)entity).isTangibleToScythe())) {
        //                                    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            // Scythe sets grass's daysWatered to 0, currentImage to Assets.grassSeeded, harvestable to false,
            // and tangibleToScythe to false.
            if (((CropEntity)entity).getCropType() == CropEntity.CropType.GRASS) {
                CropEntity tempGrass = (CropEntity) entity;

                ////////////////////////////////////////////////////////////////////////////////////////////
                tempGrass.die();    //Grass die() is different than setActive(false). LEAVE THIS LINE ALONE!
                ////////////////////////////////////////////////////////////////////////////////////////////
            } else {
                //TODO: call special unimplemented version of the CropEntity's die() that removes it without dropping HarvestEntity.
            }
        }
        // Bush
        else if (entity instanceof Bush) {
            ((Bush)entity).die();
        }
    }

} // **** end Scythe class ****