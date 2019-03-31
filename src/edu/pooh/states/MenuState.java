package edu.pooh.states;

import edu.pooh.main.Game;
import edu.pooh.main.Handler;

import java.awt.*;

public class MenuState implements State {

    private Handler handler;

    public MenuState(Handler handler) {
        this.handler = handler;
    } // **** end MenuState(Handler) constructor ****

    @Override
    public void tick() {
        if (handler.getMouseManager().isLeftPressed() && handler.getMouseManager().isRightPressed()) {
            StateManager.setCurrentState(handler.getGame().gameState);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);
    }

} // **** end MenuState class ****