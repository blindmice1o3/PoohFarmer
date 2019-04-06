package edu.pooh.states;

import edu.pooh.main.Handler;

import java.awt.*;

public class GeneralStoreState implements State {

    private Handler handler;

    public GeneralStoreState(Handler handler) {
        this.handler = handler;
    } // **** end GeneralStoreState(Handler) constructor ****

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

} // **** end GeneralStoreState class ****