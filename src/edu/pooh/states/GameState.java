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

    int prevBoundsX = 0;
    int prevBoundsY = 0;
    int prevBoundsWidth = 0;
    int prevBoundsHeight = 0;
    int prevX = 0;
    int prevY = 0;
    @Override
    public void enter(Object[] args) {
        handler.setWorld(world);

        player = (Player) args[0];
        /*
        prevBoundsX = (int) args[1];
        prevBoundsY = (int) args[2];
        prevBoundsWidth = (int) args[3];
        prevBoundsHeight = (int) args[4];
        */
        prevX = (int) args[5];
        prevY = (int) args[6];

        /*
        player.setBoundsX((int) args[1]);
        player.setBoundsY((int) args[2]);
        player.setBoundsWidth((int) args[3]);
        player.setBoundsHeight((int) args[4]);
        player.setWidth(Creature.DEFAULT_CREATURE_WIDTH);
        player.setHeight(Creature.DEFAULT_CREATURE_HEIGHT);
        */
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        player.setPosition((int)args[5], (int)args[6]);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////

        world.getEntityManager().addEntity(player);
        world.getEntityManager().setPlayer(player);
    }

    @Override
    public void exit() {
        prevX = (int)player.getX();
        prevY = (int)player.getY();

        args[0] = player;
        /*
        args[1] = prevBoundsX;
        args[2] = prevBoundsY;
        args[3] = prevBoundsWidth;
        args[4] = prevBoundsHeight;
        */
        args[5] = prevX;
        args[6] = prevY;
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