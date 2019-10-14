package edu.pooh.states;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseState implements IState {

    private Handler handler;

    public PauseState(Handler handler) {
        this.handler = handler;
    } // **** end PauseState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        handler.getTimeManager().setClockRunningFalse();
    }

    @Override
    public void exit() {
        handler.getTimeManager().setClockRunningTrue();
    }

    @Override
    public void tick() {
        getInput();


    }

    private void getInput() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
            handler.getStateManager().popIState();
        }
    }

    @Override
    public void render(Graphics g) {
        int xCenter = (handler.getWidth() / 2);
        int yCenter = (handler.getHeight() / 2);
        int widthPanel = (handler.getWidth() / 2);
        int heightPanel = (handler.getHeight() / 2);
        int xPanel = (xCenter - (widthPanel / 2));
        int yPanel = (yCenter - (heightPanel / 2));

        //panel
        g.setColor(Color.BLUE);
        g.fillRect(xPanel, yPanel, widthPanel, heightPanel);
        //border
        g.setColor(Color.YELLOW);
        g.drawRect(xPanel, yPanel, widthPanel, heightPanel);
        //text
        g.drawString("PAUSE", (xCenter - 20), (yCenter + 10));


        //TODO: *************************PlayerLogState*************************
        Text.drawString(g, "(" + handler.getTimeManager().gameSeason + ") " + handler.getTimeManager().gameMonth + ", " + handler.getTimeManager().gameDay,
                (xPanel + 10), (yPanel + 30), false, Color.RED, Assets.font28);
        Text.drawString(g, "Year: " + (handler.getTimeManager().gameYear + 1) + " of 2.5",
                (xPanel + 10), (yPanel + 65), false, Color.RED, Assets.font28);

        Text.drawString(g, "Chicken Counter: " + (ResourceManager.chickenCounter),
                (xPanel + 10), (yPanel + 100), false, Color.YELLOW, Assets.font28);
        Text.drawString(g, "Cow Counter: " + (ResourceManager.cowCounter),
                (xPanel + 10), (yPanel + 135), false, Color.GREEN, Assets.font28);
    }

} // **** end PauseState class ****