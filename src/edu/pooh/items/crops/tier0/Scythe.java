package edu.pooh.items.crops.tier0;

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
    public void resetTexture() {
        texture = Assets.scythe;
    }

    @Override
    public void execute() {
        Entity entity = handler.getWorld().getEntityManager().getPlayer().getEntityCurrentlyFacing();
        System.out.println("Scythe.execute(), targeted-entity: " + entity);
        handler.getWorld().getEntityManager().getPlayer().getStaminaModule().decreaseStaminaCurrent(2);
        System.out.println("Scythe.execute(), player's stamina decrease by 2");

        // CropEntity
        if ((entity instanceof CropEntity) && (((CropEntity)entity).isTangibleToScythe())) {
            ((CropEntity)entity).die();
        }
        // Bush
        else if (entity instanceof Bush) {
            ((Bush)entity).die();
        }
    }

} // **** end Scythe class ****