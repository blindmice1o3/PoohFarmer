package edu.pooh.states;

import edu.pooh.main.Handler;

import java.awt.*;

public class TravelingFenceState implements State {

    private Handler handler;

    public TravelingFenceState(Handler handler) {
        this.handler = handler;
    } // **** end TravelingFenceState(Handler) constructor ****

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

} // **** end TravelingFenceState class ****