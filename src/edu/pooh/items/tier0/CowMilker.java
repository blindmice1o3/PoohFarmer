package edu.pooh.items.tier0;

import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;

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

    }

} // **** end CowMilker class ****