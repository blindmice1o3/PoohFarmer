package edu.pooh.gfx;

import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.time.TimeManager;

import java.awt.*;

public class DisplayerCalendarAndResourceManager {

    private Handler handler;

    public DisplayerCalendarAndResourceManager(Handler handler) {
        this.handler = handler;
    } // **** end DisplayerCalendarAndResourceManager(Handler) constructor ****

    public void render(Graphics g) {
        if (!handler.getKeyManager().dateDisplayerKey) {
            return;
        }

        g.drawImage(Assets.dateDisplayerBackground, 64, 48, 668, 500, null);

        Text.drawString(g, "(" + TimeManager.gameSeason + ") " + TimeManager.gameMonth + ", " + TimeManager.gameDay,
                264, 248, true, Color.RED, Assets.font28);
        Text.drawString(g, "Year: " + (TimeManager.gameYear + 1) + " of 2.5",
                264, 298, true, Color.RED, Assets.font28);

        Text.drawString(g, "Chicken Counter: " + (ResourceManager.chickenCounter),
                264, 348, true, Color.YELLOW, Assets.font28);
        Text.drawString(g, "Cow Counter: " + (ResourceManager.cowCounter),
                264, 398, true, Color.BLUE, Assets.font28);
    }

} // **** end DisplayerCalendarAndResourceManager class ****