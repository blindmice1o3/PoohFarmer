package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.main.Handler;
import edu.pooh.worlds.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ChickenCoopState implements IState {

    private Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    public ChickenCoopState(Handler handler) {
        this.handler = handler;
        args = new Object[10];

        world = new World(handler, World.WorldType.CHICKEN_COOP);
    } // **** end ChickenCoopState(Handler) constructor ****

    int prevBoundsX = 0;
    int prevBoundsY = 0;
    int prevBoundsWidth = 0;
    int prevBoundsHeight = 0;
    int prevSpawnX = 0;
    int prevSpawnY = 0;
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
        prevSpawnX = (int) args[5];
        prevSpawnY = (int) args[6];

        /*
        player.setBoundsX(9);
        player.setBoundsY(12);
        player.setBoundsWidth(21);
        player.setBoundsHeight(27);
        player.setWidth(40);
        player.setHeight(40);
        */
        /////////////////////////////////////////////////////////////////////////////////////
        player.setPosition(world.getPlayerSpawnX()*64 , world.getPlayerSpawnY()*64);
        /////////////////////////////////////////////////////////////////////////////////////
        System.out.println(world.getPlayerSpawnX()  + ", " + world.getPlayerSpawnY());

        world.getEntityManager().addEntity(player);
        world.getEntityManager().setPlayer(player);
    }

    @Override
    public void exit() {
        args[0] = player;
        /*
        args[1] = prevBoundsX;
        args[2] = prevBoundsY;
        args[3] = prevBoundsWidth;
        args[4] = prevBoundsHeight;
        */
        args[5] = prevSpawnX;
        args[6] = prevSpawnY;
    }

    @Override
    public void tick() {
        if (StateManager.getCurrentState() != handler.getGame().getChickenCoopState()) {
            return;
        }

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            StateManager.change(handler.getGame().getGameState(), args);
        }

        ///////////////
        player.tick();
        ///////////////

        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointChickenCoopToGame()) ) {
            StateManager.change(handler.getGame().getGameState(), args);
            ///////////
        }
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentState() != handler.getGame().getChickenCoopState()) {
            return;
        }

        // Render background image.
        world.render(g);
    }

} // **** end ChickenCoopState class ****