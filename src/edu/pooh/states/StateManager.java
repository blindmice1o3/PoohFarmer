package edu.pooh.states;

import edu.pooh.time.TimeManager;

public class StateManager {

    private static IState currentState = null;

    public static void change(IState nextState, Object[] args) {
        getCurrentState().exit();
        nextState.enter(args);
        setCurrentState(nextState);

        TimeManager.consoleOutputTimeInfo();
    }

    public static void setCurrentState(IState IState) {
        currentState = IState;
    }

    public static IState getCurrentState() {
        return currentState;
    }

} // **** end StateManager class ****