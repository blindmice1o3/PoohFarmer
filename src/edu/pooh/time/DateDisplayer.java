package edu.pooh.time;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.main.Handler;

import java.awt.*;

public class DateDisplayer {

    private Handler handler;

    public DateDisplayer(Handler handler) {
        this.handler = handler;
    } // **** end DateDisplayer(Handler) constructor ****

    public void render(Graphics g) {
        if (!handler.getKeyManager().dateDisplayerKey) {
            return;
        }

        g.drawImage(Assets.dateDisplayerBackground, 64, 48, 668, 500, null);

        Text.drawString(g, "(" + TimeManager.gameSeason + ") " + TimeManager.gameMonth + ", " + TimeManager.gameDay,
                264, 248, true, Color.RED, Assets.font28);
        Text.drawString(g, "Year: " + (TimeManager.gameYear + 1) + " of 2.5",
                264, 298, true, Color.RED, Assets.font28);
    }

} // **** end DateDisplayer class ****