package edu.pooh.items.tier0;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.creatures.live_stocks.Cow;
import edu.pooh.entities.statics.produce_yields.Milk;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

public class CowMilker extends Item {

    private static CowMilker uniqueInstance = new CowMilker();

    private CowMilker() {
        super(Assets.cowMilker, "Cow Milker", ID.COW_MILKER);
    } // **** end CowMilker() singleton-pattern constructor ****

    public static synchronized CowMilker getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {
        Entity entity = handler.getWorld().getEntityManager().getPlayer().getEntityCurrentlyFacing();
        System.out.println("CowMilker.execute(), targeted-entity: " + entity);
        handler.getWorld().getEntityManager().getPlayer().decreaseStaminaCurrent(2);
        System.out.println("CowMilker.execute(), player's stamina decrease by 2");

        Player tempPlayer = handler.getWorld().getEntityManager().getPlayer();

        //EntityCurrentlyFacing is a cow
        if (entity instanceof Cow) {
            Cow tempCow = (Cow)entity;
            //(ADULT!!!!!HEALTHY!!!!!) that is NOT milked, AND player is not holding anything.
            if ( (tempCow.getCowHealth() == Cow.CowHealth.HEALTHY) &&
                    ((tempCow.getCowState() == Cow.CowState.ADULT_1) ||
                            (tempCow.getCowState() == Cow.CowState.ADULT_2) ||
                            (tempCow.getCowState() == Cow.CowState.ADULT_3)) &&
                    (!((Cow)entity).isMilked()) && (tempPlayer.getHoldableObject() == null)) {

                Milk.MilkSize tempMilkSize = null;

                if (tempCow.getCowState() == Cow.CowState.ADULT_1) {
                    tempMilkSize = Milk.MilkSize.SMALL;
                } else if (tempCow.getCowState() == Cow.CowState.ADULT_2) {
                    tempMilkSize = Milk.MilkSize.MEDIUM;
                } else if (tempCow.getCowState() == Cow.CowState.ADULT_3) {
                    tempMilkSize = Milk.MilkSize.LARGE;
                }

                System.out.println("Instantiating new Milk object and setting it as player's holdableObject");
                /////////////////////////////////////////////////////////////////////////////////////////////////////
                Milk tempMilk = new Milk(handler, (tempCow.getX() * Tile.TILE_WIDTH), (tempCow.getY() * Tile.TILE_HEIGHT),
                        tempMilkSize);
                /////////////////////////////////////////////////////////////////////////////////////////////////////
                handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(
                        tempMilk
                );
                handler.getWorld().getEntityManager().setToBeAdded(true);
                tempPlayer.setHoldableObject(tempMilk);
                tempPlayer.setHolding(true);


                ((Cow)entity).setMilked(true);
            }
        }
    }

} // **** end CowMilker class ****