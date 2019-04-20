package edu.pooh.states;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TravelingFenceState implements State {

    private Handler handler;

    public TravelingFenceState(Handler handler) {
        this.handler = handler;
    } // **** end TravelingFenceState(Handler) constructor ****

    @Override
    public void tick() {
        // VK_ESCAPE will set state to GameState.
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            StateManager.setCurrentState(handler.getGame().gameState);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.shoppingScreen, 10, 10, handler.getWidth()-20, handler.getHeight()-20, null);
    }

} // **** end TravelingFenceState class ****