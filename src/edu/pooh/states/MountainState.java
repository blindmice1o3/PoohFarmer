package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.player.Player;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;
import java.io.Serializable;

public class MountainState
        implements IState, Serializable {

    private transient Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    public MountainState(Handler handler) {
        this.handler = handler;

        world = new World(handler, World.WorldType.MOUNTAIN);
    } // **** end MountainState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        //MountainState is an out-doors IState.
        handler.getTimeManager().setClockRunningTrue();

        handler.setWorld(world);
        player = (Player)args[0];

        this.args = args;

        /////////////////////////////////////////////////////////////////////////////////////
        player.setPosition(world.getPlayerSpawnX() * Tile.TILE_WIDTH,
                world.getPlayerSpawnY() * Tile.TILE_HEIGHT);
        /////////////////////////////////////////////////////////////////////////////////////

        world.getEntityManager().addEntity(player);
        world.getEntityManager().setPlayer(player);
    }

    @Override
    public void exit() {
        //MountainState.exit() always result in CrossroadState, which is an out-doors IState.
        handler.getTimeManager().setClockRunningTrue();

        if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
            Entity tempHoldableEntity = (Entity) player.getHoldableObject();

            if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                ((CrossroadState)handler.getStateManager().getIState(StateManager.GameState.CROSSROAD)).getWorld().getEntityManager().addEntity(
                        tempHoldableEntity
                );
            }
        }
    }

    @Override
    public void tick() {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.MOUNTAIN)) {
            return;
        }

        ///////////////
        world.tick();
        ///////////////

        checkTransferPoints();
    }

    private void checkTransferPoints() {
        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointMountainToCrossroad()) ) {
            handler.getStateManager().popIState();

            //positions the player to where they entered from.
            IState currentState = handler.getStateManager().getCurrentState();
            CrossroadState crossroadState = (CrossroadState)handler.getStateManager().getIState(StateManager.GameState.CROSSROAD);
            currentState.enter(crossroadState.getArgs());
        }
    }

    @Override
    public void render(Graphics g) {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.MOUNTAIN)) {
            return;
        }

        ////////////////
        world.render(g);
        ////////////////
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

} // **** end MountainState class ****