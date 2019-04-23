package edu.pooh.states;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.items.Item;
import edu.pooh.items.tier0.SeedsWild;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

// TODO: implement buy/sell functionality. Probably need UIManager for this State.
public class TravelingFenceState implements State {

    private Handler handler;
    private HashMap<Item, Float> inStock;

    public TravelingFenceState(Handler handler) {
        this.handler = handler;

        initInStock();
    } // **** end TravelingFenceState(Handler) constructor ****

    private void initInStock() {
        inStock = new HashMap<Item, Float>();

        SeedsWild tempSeedsWild;
        for (int i = 0; i < SeedsWild.SeedType.values().length; i++) {
            tempSeedsWild = new SeedsWild(handler);

            switch (i) {
                case 0:
                    tempSeedsWild.setName("\"Special\" seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.CANNABIS_WILD);
                    inStock.put(tempSeedsWild, 3.99f);
                    break;
                case 1:
                    tempSeedsWild.setName("Turnip seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.TURNIP);
                    inStock.put(tempSeedsWild, 1.99f);
                    break;
                case 2:
                    tempSeedsWild.setName("Potato seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.POTATO);
                    inStock.put(tempSeedsWild, 2.49f);
                    break;
                case 3:
                    tempSeedsWild.setName("Tomato seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.TOMATO);
                    inStock.put(tempSeedsWild, 2.99f);
                    break;
                case 4:
                    tempSeedsWild.setName("Corn seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.CORN);
                    inStock.put(tempSeedsWild, 3.49f);
                    break;
                default:
                    break;
            }

        }

    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    private boolean renderInStockList = false;
    @Override
    public void tick() {
        if (StateManager.getCurrentState() != handler.getGame().getTravelingFenceState()) {
            return;
        }

        // VK_ESCAPE will set state to GameState.
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            StateManager.setCurrentState(handler.getGame().getGameState());
            renderInStockList = false;
        }

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
            renderInStockList = true;
        }
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentState() != handler.getGame().getTravelingFenceState()) {
            return;
        }

        g.drawImage(Assets.shoppingScreen, 10, 10,
                handler.getWidth()-20, handler.getHeight()-20, null);

        if (renderInStockList) {
            int y = 250;
            for (Item i : inStock.keySet()) {
                Text.drawString(g, "Name: " + i.getName()  + "      PRICE: \'only\' " + inStock.get(i), 50, y, false, Color.GRAY, Assets.font28);
                y += 25;
                Text.drawString(g, "Id: " + i.getId(), 50, y, false, Color.GRAY, Assets.font28);
                y += 25;
                Text.drawString(g, "Count: " + i.getCount(),50, y, false, Color.GRAY, Assets.font28);
                y += 50;
            }
        }
    }

} // **** end TravelingFenceState class ****