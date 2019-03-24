package edu.pooh.states;

public class StateManager {

    private static State currentState = null;

    public static void setCurrentState(State state) {
        currentState = state;
    }

    public static State getCurrentState() {
        return currentState;
    }

} // **** end StateManager class ****