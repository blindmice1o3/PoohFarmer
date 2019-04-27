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
        args = new Object[10];

        world = new World(handler, World.WorldType.GAME);
        handler.setWorld(world);    // IMPORTANT TO DO IN THIS ORDER, create world, then handler's setWorld().
                                    // NOW IT SETS the enum WorldType worldType for the world as well!!!!

        player = handler.getWorld().getEntityManager().getPlayer();
    } // **** end GameState(Handler) constructor ****


    @Override
    public void enter(Object[] args) {
        handler.setWorld(world);

        this.args = args;

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        player.setPosition((int)args[1], (int)args[2]);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////

        world.getEntityManager().addEntity(player);
        world.getEntityManager().setPlayer(player);
    }

    @Override
    public void exit() {
        args[0] = player;
        args[1] = (int)player.getX();
        args[2] = (int)player.getY();
    }

    @Override
    public void tick() {
        if (StateManager.getCurrentState() != handler.getGame().getGameState()) {
            return;
        }

        /////////////
        world.tick();
        /////////////


        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToHome()) ) {
            StateManager.change(handler.getGame().getHomeState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToChickenCoop()) ) {
            StateManager.change(handler.getGame().getChickenCoopState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToCowBarn()) ) {
            StateManager.change(handler.getGame().getCowBarnState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToToolShed()) ) {
            StateManager.change(handler.getGame().getToolShedState(), args);
        }
    }

    //Responsibility being moved here FROM World class.
    /*
        public void checkMapTransferPoints() {
        if (transferPointGameToHome.intersects(entityManager.getPlayer().getCollisionBounds(0, 0))) {
            StateManager.setCurrentState(handler.getGame().getHomeState());
        } else if (transferPointDoorCowBarn.intersects(entityManager.getPlayer().getCollisionBounds(0, 0))) {
            StateManager.setCurrentState(handler.getGame().getHomeState());
        } else if (transferPointDoorChickenCoop.intersects(entityManager.getPlayer().getCollisionBounds(0, 0))) {
            StateManager.setCurrentState(handler.getGame().getHomeState());
        } else if (transferPointDoorToolShed.intersects(entityManager.getPlayer().getCollisionBounds(0, 0))) {
            StateManager.setCurrentState(handler.getGame().getHomeState());
        } else if (transferPointGateFarm.intersects(entityManager.getPlayer().getCollisionBounds(0, 0))) {
            StateManager.setCurrentState(handler.getGame().getHomeState());
        }
    }
     */

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