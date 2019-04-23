package edu.pooh.states;

import edu.pooh.main.Handler;
import edu.pooh.worlds.World;

import java.awt.*;

public class GameIState implements IState {

    private Object[] args;
    private Handler handler;
    private World world;

    public GameIState(Handler handler) {
        this.handler = handler;
        world = new World(handler, "res/worlds/chapter1 - tiles (int).txt");
        handler.setWorld(world);    // IMPORTANT TO DO IN THIS ORDER, create world, then handler's setWorld().
    } // **** end GameIState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {
        args = new Object[5];
        args[0] = handler.getWorld().getEntityManager().getPlayer();
    }

    @Override
    public void tick() {
        if (StateManager.getCurrentIState() != handler.getGame().getGameIState()) {
            return;
        }

        /////////////
        world.tick();
        /////////////

        if ( handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(
                handler.getWorld().getTransferPointDoorHome()) ) {
            StateManager.change(handler.getGame().getHomeIState(), args);

        }
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentIState() != handler.getGame().getGameIState()) {
            return;
        }

        ////////////////
        world.render(g);
        ////////////////
    }

} // **** end GameIState class ****