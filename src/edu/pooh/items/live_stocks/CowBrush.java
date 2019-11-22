package edu.pooh.items.live_stocks;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.live_stocks.Cow;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;

public class CowBrush extends Item {

    private static CowBrush uniqueInstance = new CowBrush();

    private CowBrush() {
        super(Assets.cowBrush, "Cow Brush", ID.COW_BRUSH);
    } // **** end CowBrush() singleton-pattern constructor ****

    public static synchronized CowBrush getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void resetTexture() {
        texture = Assets.cowBrush;
    }

    @Override
    public void execute() {
        Entity entity = handler.getWorld().getEntityManager().getPlayer().getEntityCurrentlyFacing();
        System.out.println("CowBrush.execute(), targeted-entity: " + entity);
        handler.getWorld().getEntityManager().getPlayer().getStaminaModule().decreaseStaminaCurrent(2);
        System.out.println("CowBrush.execute(), player's stamina decrease by 2");

        //EntityCurrentlyFacing is a cow that is NOT brushed.
        if ((entity instanceof Cow) && (!((Cow)entity).isBrushed()) ) {
            ((Cow)entity).increaseAffectionScore(3);
            ((Cow)entity).setBrushed(true);
        }
    }

} // **** end CowBrush class ****