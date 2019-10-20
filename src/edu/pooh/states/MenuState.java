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

                // @@@@@@@ having game object's gameState reference a different GameState instance??? @@@@@@@@
                if (handler.getStateManager().getStatesStackSize() == 0) {
                    handler.getStateManager().pushIState(StateManager.GameState.GAME);
                    //handler.getGame().setGameState( new GameState(handler) );
                    //StateManager.setCurrentState( handler.getGame().getGameState() ); //ALSO deprecated! Should be change().
                }
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            }
        }));
    } // **** end MenuState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        handler.getTimeManager().setClockRunningFalse();

        if ((args[0] != null) && (args[0] instanceof Player)) {
            player = (Player)args[0];
        }
    }

    @Override
    public void exit() {
        args[0] = player;
    }

    @Override
    public void tick() {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.MENU)) {
            return;
        }

        ////////////////
        uiManager.tick();
        ////////////////
    }

    @Override
    public void render(Graphics g) {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.MENU)) {
            return;
        }

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