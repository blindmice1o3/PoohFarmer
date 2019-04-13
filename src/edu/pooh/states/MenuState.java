package edu.pooh.states;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.ui.ClickListener;
import edu.pooh.ui.UIImageButton;
import edu.pooh.ui.UIManager;

import java.awt.*;

public class MenuState implements State {

    private Handler handler;
    private UIManager uiManager;

    public MenuState(Handler handler) {
        this.handler = handler;
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(new UIImageButton(200, 200, 96, 31, Assets.startButtons,
                new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                StateManager.setCurrentState( new GameState(handler) );
            }
        }));
    } // **** end MenuState(Handler) constructor ****

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawString("Congratuations! Play again?", 175, 175);
        uiManager.render(g);
    }

} // **** end MenuState class ****