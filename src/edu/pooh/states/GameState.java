package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.main.Handler;
import edu.pooh.worlds.World;

import java.awt.*;

public class GameState implements IState {

    private Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    public GameState(Handler handler) {
        this.handler = handler;
        args = new Object[5];

        world = new World(handler, "res/worlds/chapter1 - tiles (int).txt");
        handler.setWorld(world);    // IMPORTANT TO DO IN THIS ORDER, create world, then handler's setWorld().

        player = handler.getWorld().getEntityManager().getPlayer();
    } // **** end GameState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        if ((args[0] != null) && (args[0] instanceof Player)) {
            player = (Player)args[0];
        }
    }

    @Override
    public void exit() {
        args[0] = player;
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