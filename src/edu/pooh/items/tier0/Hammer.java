package edu.pooh.items.tier0;

import edu.pooh.entities.Entity;
import edu.pooh.entities.statics.statics1x1.Rock;
import edu.pooh.entities.statics.statics1x1.RockMountain;
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
    public void execute() {
        Entity entity = handler.getWorld().getEntityManager().getPlayer().getEntityCurrentlyFacing();
        System.out.print("Hammer.execute(), targeted-entity: " + entity);

        if (entity instanceof Rock) {
            Rock tempEntity = (Rock)entity;

            /////////////////
            tempEntity.die();
            /////////////////
        } else if (entity instanceof RockMountain) {
            RockMountain tempEntity = (RockMountain)entity;

            /////////////////
            tempEntity.die();
            /////////////////
        }
    }

} // **** end Hammer class ****