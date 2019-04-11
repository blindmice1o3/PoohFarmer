package edu.pooh.items.tier0;

import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;

public class Shovel extends Item {

    private static Shovel uniqueInstance = new Shovel();

    private Shovel() {
        super(Assets.shovel, "Shovel", ID.SHOVEL);
    } // **** end Shovel() singleton-pattern constructor ****

    public static Shovel getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {

    }

} // **** end Shovel class ****