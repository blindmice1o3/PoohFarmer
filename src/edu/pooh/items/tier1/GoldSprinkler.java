package edu.pooh.items.tier1;

import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;

public class GoldSprinkler extends Item {

    private static GoldSprinkler uniqueInstance = new GoldSprinkler();

    private GoldSprinkler() {
        super(Assets.goldSprinkler, "Gold Sprinkler", ID.GOLD_SPRINKLER);
    } // **** end GoldSprinkler() singleton-pattern constructor ****

    public static GoldSprinkler getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {

    }

} // **** end GoldSprinkler class ****