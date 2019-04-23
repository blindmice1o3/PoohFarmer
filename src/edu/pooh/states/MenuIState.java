package edu.pooh.states;

import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.items.tier0.WateringCan;
import edu.pooh.main.Handler;
import edu.pooh.ui.IClickListener;
import edu.pooh.ui.UIImageButton;
import edu.pooh.ui.UIManager;

import java.awt.*;

public class MenuIState implements IState {

    private Handler handler;
    private UIManager uiManager;

    public MenuIState(Handler handler) {
        this.handler = handler;
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

                handler.getGame().setGameIState( new GameIState(handler) );
                StateManager.setCurrentIState( handler.getGame().getGameIState() );
                ///////////////////////////////////////////////////////////////////////////
            }
        }));
    } // **** end MenuIState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void tick() {
        if (StateManager.getCurrentIState() != handler.getGame().getMenuIState()) {
            return;
        }

        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentIState() != handler.getGame().getMenuIState()) {
            return;
        }

        g.drawString("Congratuations! Play again?", 175, 175);
        uiManager.render(g);
    }

} // **** end MenuIState class ****