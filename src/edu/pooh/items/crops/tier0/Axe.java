package edu.pooh.items.crops.tier0;

import edu.pooh.entities.Entity;
import edu.pooh.entities.statics.statics1x1.Wood;
import edu.pooh.entities.statics.statics2x2.TreeStump;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;

public class Axe extends Item {

    private static Axe uniqueInstance = new Axe();

    private Axe() {
        super(Assets.axe, "Axe", ID.AXE);
    } // **** end Axe() singleton-pattern constructor ****

    public static synchronized Axe getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void resetTexture() {
        texture = Assets.axe;
    }

    @Override
    public void execute() {
        Entity entity = handler.getWorld().getEntityManager().getPlayer().getEntityCurrentlyFacing();
        System.out.println("Axe.execute(), targeted-entity: " + entity);
        handler.getWorld().getEntityManager().getPlayer().decreaseStaminaCurrent(2);
        System.out.println("Axe.execute(), player's stamina decrease by 2");

        if (entity instanceof Wood) {
            ((Wood)entity).die();
        } else if (entity instanceof TreeStump) {
            ((TreeStump)entity).die();
        }
    }

} // **** end Axe class ****