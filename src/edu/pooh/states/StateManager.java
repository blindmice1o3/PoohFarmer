package edu.pooh.states;

import edu.pooh.main.Handler;
import edu.pooh.time.TimeManager;

import java.util.ArrayList;
import java.util.HashMap;

public class StateManager {

    public enum GameState { GAME, HOME, CHICKEN_COOP, COW_BARN, TOOL_SHED, CROSSROAD, MOUNTAIN,
        THE_WEST, MENU, TRAVELING_FENCE; }

    private Handler handler;

    //GAME'S ENTIRE SET OF IState INSTANCES STORED HERE.
    private HashMap<GameState, IState> states;
    //stores the order of in-use IState using a stack structure (LIFO or FILO).
    private ArrayList<IState> statesStack;

    /*
    // STATES
    private IState gameState, homeState, chickenCoopState, cowBarnState, toolShedState,
            crossroadState, mountainState, theWestState, menuState, travelingFenceState;
    */

    public StateManager(Handler handler) {
        this.handler = handler;

        initStates();

        initStatesStack();
    } // **** end StateManager(Handler) constructor ****

    private void initStates() {
        states = new HashMap<GameState, IState>();

        states.put(GameState.GAME, new edu.pooh.states.GameState(handler));
        states.put(GameState.HOME, new HomeState(handler));
        states.put(GameState.CHICKEN_COOP, new ChickenCoopState(handler));
        states.put(GameState.COW_BARN, new CowBarnState(handler));
        states.put(GameState.TOOL_SHED, new ToolShedState(handler));
        states.put(GameState.CROSSROAD, new CrossroadState(handler));
        states.put(GameState.MOUNTAIN, new MountainState(handler));
        states.put(GameState.THE_WEST, new TheWestState(handler));
        states.put(GameState.MENU, new MenuState(handler));
        states.put(GameState.TRAVELING_FENCE, new TravelingFenceState(handler));
    }

    private void initStatesStack() {
        statesStack = new ArrayList<IState>();

        IState initialState = states.get(GameState.GAME);
        statesStack.add( initialState );
    }

    public void pushIState(GameState nextState) {
        IState nextIState = states.get(nextState);

        statesStack.add( nextIState );
    }

    public void popIState() {
        //TOP.exit().
        getCurrentState().exit();

        int indexTop = (statesStack.size() - 1);
        statesStack.remove( indexTop );
    }

    public void change(GameState nextState, Object[] args) {
        //TOP.exit().
        getCurrentState().exit();

        //newTOP.enter().
        IState nextIState = states.get(nextState);
        nextIState.enter(args);

        pushIState(nextState);

        TimeManager.consoleOutputTimeInfo();
    }

    public IState getCurrentState() {
        int indexTop = (statesStack.size() - 1);
        IState topIState = statesStack.get(indexTop);

        return topIState;
    }

    public IState getIState(GameState key) {
        IState specifiedIState = states.get(key);

        return specifiedIState;
    }

    public int getStatesStackSize() {
        return statesStack.size();
    }

    /*
    public void setCurrentState(IState IState) {
        currentState = IState;
    }
    */

} // **** end StateManager class ****