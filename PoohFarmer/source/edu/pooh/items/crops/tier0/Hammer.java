package edu.pooh.items.crops.tier0;

import edu.pooh.entities.Entity;
import edu.pooh.entities.statics.statics1x1.Rock;
import edu.pooh.entities.statics.statics1x1.RockMountain;
import edu.pooh.entities.statics.statics2x2.Boulder;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;

public class Hammer extends Item {

    private static Hammer uniqueInstance = new Hammer();

    private Hammer() {
        super(Assets.hammer, "Hammer", ID.HAMMER);
    } // **** end Hammer() singleton-pattern constructor ****

    public static synchronized Hammer getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void resetTexture() {
        texture = Assets.hammer;
    }

    @Override
    public void execute() {
        Entity entity = handler.getWorld().getEntityManager().getPlayer().getEntityCurrentlyFacing();
        System.out.println("Hammer.execute(), targeted-entity: " + entity);
        handler.getWorld().getEntityManager().getPlayer().getStaminaModule().decreaseStaminaCurrent(2);
        System.out.println("Hammer.execute(), player's stamina decrease by 2");

        if (entity instanceof Rock) {
            ((Rock)entity).die();
        } else if (entity instanceof RockMountain) {
            ((RockMountain)entity).die();
        } else if (entity instanceof Boulder) {
            ((Boulder)entity).die();
        }
    }

} // **** end Hammer class ****