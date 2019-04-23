package edu.pooh.states;

public class StateManager {

    private static IState currentState = null;

    public static void change(IState nextState, Object[] args) {
        getCurrentState().exit();
        nextState.enter(args);
        setCurrentState(nextState);
    }

    public static void setCurrentState(IState IState) {
        currentState = IState;
    }

    public static IState getCurrentState() {
        return currentState;
    }

} // **** end StateManager class ****