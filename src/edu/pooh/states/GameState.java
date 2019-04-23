package edu.pooh.states;

import edu.pooh.main.Handler;
import edu.pooh.worlds.World;

import java.awt.*;

public class GameState implements State {

    private Handler handler;
    private World world;

    public GameState(Handler handler) {
        this.handler = handler;
        world = new World(handler, "res/worlds/chapter1 - tiles (int).txt");
        handler.setWorld(world);    // IMPORTANT TO DO IN THIS ORDER, create world, then handler's setWorld().
    } // **** end GameState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void tick() {
        if (StateManager.getCurrentState() != handler.getGame().getGameState()) {
            return;
        }

        /////////////
        world.tick();
        /////////////

        if ( handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(
                handler.getWorld().getTransferPointDoorHome()) ) {
            Object[] args = new Object[5];
            args[0] = handler.getWorld().getEntityManager().getPlayer();
            args[1] = handler.getWorld();

            StateManager.change(handler.getGame().getHomeState(), args);
        }
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentState() != handler.getGame().getGameState()) {
            return;
        }

        ////////////////
        world.render(g);
        ////////////////
    }

} // **** end GameState class ****