package edu.pooh.states;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class PauseState
        implements IState, Serializable {

    private transient Handler handler;

    public PauseState(Handler handler) {
        this.handler = handler;
    } // **** end PauseState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        //PauseState is an IState that shouldn't affect the game clock.
        handler.getTimeManager().setClockRunningFalse();
    }

    @Override
    public void exit() {
        //DO NOT call TimeManager.setClockRunningTrue(), sometime popping PauseState result in in-doors IState.
        //SAME for TimeManager.setClockRunningFalse(), sometime popping PauseState result in out-doors IState.
        //NEVERMIND THE EARLIER COMMENTS, we do have to decide when to restart the game clock.
        int indexPriorIState = (handler.getStateManager().getStatesStack().size() - 2);
        IState priorIState = handler.getStateManager().getStatesStack().get(indexPriorIState);

        if ( (priorIState instanceof CrossroadState) || (priorIState instanceof GameState) ||
                (priorIState instanceof MountainState) || (priorIState instanceof TheWestState) ) {
            handler.getTimeManager().setClockRunningTrue();
        }
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
        Text.drawString(g, "(" + handler.getTimeManager().gameSeason + ") " +
                        handler.getTimeManager().gameMonth + ", " + handler.getTimeManager().gameDay,
                (xPanel + 10), (yPanel + 30), false, Color.RED, Assets.font28);
        Text.drawString(g, "Year: " + (handler.getTimeManager().gameYear + 1) + " of 2.5",
                (xPanel + 10), (yPanel + 65), false, Color.RED, Assets.font28);

        Text.drawString(g, "Chicken Counter: " + (handler.getResourceManager().getChickenCounter()),
                (xPanel + 10), (yPanel + 100), false, Color.YELLOW, Assets.font28);
        Text.drawString(g, "Cow Counter: " + (handler.getResourceManager().getCowCounter()),
                (xPanel + 10), (yPanel + 135), false, Color.GREEN, Assets.font28);
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end PauseState class ****