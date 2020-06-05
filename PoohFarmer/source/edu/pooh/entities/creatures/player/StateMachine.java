package edu.pooh.entities.creatures.player;

import edu.pooh.main.Handler;
import edu.pooh.states.IState;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StateMachine {

    private Handler handler;
    private Map<String, IState> states;

    private IState currentIState;

    public StateMachine(Handler handler) {
        this.handler = handler;

        states = new HashMap<String, IState>();

        currentIState = null;
    } // **** end StateMachine(Handler) constructor ****

    public void tick() {
        currentIState.tick();
    }

    public void render(Graphics g) {
        currentIState.render(g);
    }

    public void setCurrentIState(IState newIState) {
        currentIState = newIState;
    }

    public void addIState(String nameOfIState, IState newIState) {
        states.put(nameOfIState, newIState);
    }

    public IState getIState(String nameOfIState) {
        if (states.containsKey(nameOfIState)) {
            return states.get(nameOfIState);
        } else {
            return null;
        }
    }

} // **** end StateMachine class ****