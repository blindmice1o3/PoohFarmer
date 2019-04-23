package edu.pooh.states;

public class StateManager {

    private static IState currentIState = null;

    public static void change(IState nextIState, Object[] args) {
        getCurrentIState().exit();
        nextIState.enter(args);
        setCurrentIState(nextIState);
    }

    public static void setCurrentIState(IState IState) {
        currentIState = IState;
    }

    public static IState getCurrentIState() {
        return currentIState;
    }

} // **** end StateManager class ****