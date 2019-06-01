package edu.pooh.items.live_stocks;

import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;

public class CowSpontaneousGenerator extends Item {

    private static CowSpontaneousGenerator uniqueInstance =  new CowSpontaneousGenerator();

    private CowSpontaneousGenerator() {
        super(Assets.cowAdultDown[0], "Cow Spontaneous Generator", ID.COW_SPONTANEOUS_GENERATOR);
    } // **** end CowSpontaneousGenerator() singleton-pattern constructor ****

    public CowSpontaneousGenerator getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {

    }

} // **** end CowSpontaneousGenerator class ****