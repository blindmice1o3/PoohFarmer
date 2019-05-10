package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.items.Item;
import edu.pooh.items.tier0.SeedsWild;
import edu.pooh.main.Handler;
import edu.pooh.time.TimeManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

// TODO: implement buy/sell functionality. Probably need UIManager for this IState.
public class TravelingFenceState implements IState {

    private Handler handler;

    private Object[] args;
    private Player player;

    private HashMap<String, Integer> inStock;

    public TravelingFenceState(Handler handler) {
        this.handler = handler;

        initInStock();
    } // **** end TravelingFenceState(Handler) constructor ****

    private void initInStock() {
        inStock = new HashMap<String, Integer>();

        SeedsWild tempSeedsWild;
        for (int i = 0; i < SeedsWild.SeedType.values().length; i++) {
            // @@@@@@@@@@@ INSTANTIATION @@@@@@@@@@
            tempSeedsWild = new SeedsWild(handler);
            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            switch (i) {
                case 0:
                    tempSeedsWild.setName("special seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.CANNABIS_WILD);
                    inStock.put(tempSeedsWild.getName(), 500);
                    break;
                case 1:
                    tempSeedsWild.setName("Turnip seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.TURNIP);
                    inStock.put(tempSeedsWild.getName(), 200);
                    break;
                case 2:
                    tempSeedsWild.setName("Potato seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.POTATO);
                    inStock.put(tempSeedsWild.getName(), 200);
                    break;
                case 3:
                    tempSeedsWild.setName("Tomato seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.TOMATO);
                    inStock.put(tempSeedsWild.getName(), 300);
                    break;
                case 4:
                    tempSeedsWild.setName("Corn seeds");
                    tempSeedsWild.setSeedType(SeedsWild.SeedType.CORN);
                    inStock.put(tempSeedsWild.getName(), 300);
                    break;
                default:
                    break;
            }
        }
        inStock.put("Grass seeds", 500);
    }

    @Override
    public void enter(Object[] args) {
        TimeManager.setClockRunningFalse();

        this.args = args;

        if ((args[0] != null) && (args[0] instanceof Player)) {
            player = (Player)args[0];
        }
    }

    @Override
    public void exit() {
        args[0] = player;
    }

    private boolean renderInStockList = false;
    @Override
    public void tick() {
        // VK_ESCAPE will set state to GameState.
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            StateManager.change(handler.getGame().getGameState(), args);
            renderInStockList = false;
        }

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
            renderInStockList = true;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.shoppingScreen, 10, 10,
                handler.getWidth()-20, handler.getHeight()-20, null);

        if (renderInStockList) {
            int y = 250;
            for (String s : inStock.keySet()) {
                Text.drawString(g, "Name: " + s  + "      PRICE: \'only\' " + inStock.get(s), 50, y, false, Color.GRAY, Assets.font28);
                y += 25;
            }
        }
    }

} // **** end TravelingFenceState class ****