package edu.pooh.items.tier1;

import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;

public class GoldShovel extends Item {

    private static GoldShovel uniqueInstance = new GoldShovel();

    private GoldShovel() {
        super(Assets.goldShovel, "Gold Shovel", ID.GOLD_SHOVEL);
    } // **** end GoldShovel() singleton-pattern constructor ****

    public static synchronized GoldShovel getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {

    }

} // **** end GoldShovel class ****