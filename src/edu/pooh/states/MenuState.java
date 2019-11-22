package edu.pooh.states;

import edu.pooh.entities.creatures.player.Player;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.items.crops.tier0.WateringCan;
import edu.pooh.main.Handler;
import edu.pooh.ui.IClickListener;
import edu.pooh.ui.UIImageButton;
import edu.pooh.ui.UIManager;

import java.awt.*;
import java.io.Serializable;

public class MenuState
        implements IState, Serializable {

    private transient Handler handler;

    private Object[] args;
    private Player player;

    private UIManager uiManager;

    public MenuState(Handler handler) {
        this.handler = handler;
        args = new Object[10];

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(new UIImageButton(200, 200, 96, 31, Assets.startButtons,
                new IClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                ///////////////////////////////////////////////////////////////////////////
                if (handler.getWorld().getEntityManager().getPlayer().getInventory().getItem(0).getId() == Item.ID.WATERING_CAN) {
                    ((WateringCan)handler.getWorld().getEntityManager().getPlayer().getInventory().getItem(0)).resetCountWater();

                }
            }
        }));
    } // **** end MenuState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        //MenuState is an IState that shouldn't affect the game clock.
        handler.getTimeManager().setClockRunningFalse();

        if ((args[0] != null) && (args[0] instanceof Player)) {
            player = (Player)args[0];
        }
    }

    @Override
    public void exit() {
        //DO NOT call TimeManager.setClockRunningTrue(), sometime popping MenuState result in in-doors IState.
        //SAME for TimeManager.setClockRunningFalse(), sometime popping MenuState result in out-doors IState.
        //NEVERMIND THE EARLIER COMMENTS, we do have to decide when to restart the game clock.
        int indexPriorIState = (handler.getStateManager().getStatesStack().size() - 2);
        IState priorIState = handler.getStateManager().getStatesStack().get(indexPriorIState);

        if ( (priorIState instanceof CrossroadState) || (priorIState instanceof GameState) ||
                (priorIState instanceof MountainState) || (priorIState instanceof TheWestState) ) {
            handler.getTimeManager().setClockRunningTrue();
        }

        args[0] = player;
    }

    @Override
    public void tick() {
        ////////////////
        uiManager.tick();
        ////////////////
    }

    @Override
    public void render(Graphics g) {
        g.drawString("Congratuations! Play again?", 175, 175);

        ////////////////
        uiManager.render(g);
        ////////////////
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end MenuState class ****