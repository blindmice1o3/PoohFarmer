package edu.pooh.items.tier0;

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
    public void execute() {

    }

} // **** end CowBrush class ****