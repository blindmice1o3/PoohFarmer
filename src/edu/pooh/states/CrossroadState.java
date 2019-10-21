package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.player.Player;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;
import java.io.Serializable;

public class CrossroadState
        implements IState, Serializable {

    public enum PlayerPreviousExit { GAME_STATE, MOUNTAIN_STATE, THE_WEST_STATE; }

    private transient Handler handler;
    private World world;

    private Object[] args;
    private Player player;
    private PlayerPreviousExit playerPreviousExit;

    public CrossroadState(Handler handler) {
        this.handler = handler;
        world = new World(handler, World.WorldType.CROSSROAD);

        playerPreviousExit = PlayerPreviousExit.GAME_STATE;
    } // **** end CrossroadState(Handler) constructor ****


    @Override
    public void enter(Object[] args) {
        handler.getTimeManager().setClockRunningTrue();

        handler.setWorld(world);
        player = (Player)args[0];

        this.args = args;

        /////////////////////////////////////////////////////////////////////////////////////
        if (playerPreviousExit == PlayerPreviousExit.GAME_STATE) {
            player.setPosition((world.getPlayerSpawnX() * Tile.TILE_WIDTH),
                    (world.getPlayerSpawnY() * Tile.TILE_HEIGHT));
        } else if (playerPreviousExit == PlayerPreviousExit.MOUNTAIN_STATE) {
            player.setPosition((7 * Tile.TILE_WIDTH),
                    (1 * Tile.TILE_HEIGHT));
        } else if (playerPreviousExit == PlayerPreviousExit.THE_WEST_STATE) {
            player.setPosition((Tile.TILE_WIDTH / 2),
                    (7 * Tile.TILE_HEIGHT));
        }
        /////////////////////////////////////////////////////////////////////////////////////
        world.getEntityManager().addEntity(player);
        world.getEntityManager().setPlayer(player);
    }

    @Override
    public void exit() {
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        handler.getTimeManager().incrementElapsedRealSecondsBy60();
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    @Override
    public void tick() {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.CROSSROAD)) {
            return;
        }

        ///////////////
        world.tick();
        ///////////////

        checkTransferPoints();
    }

    private void checkTransferPoints() {
        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointCrossroadToGame()) ) {
            ///////////////////////////////////////////////////
            playerPreviousExit = PlayerPreviousExit.GAME_STATE;

            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((GameState)handler.getStateManager().getIState(StateManager.GameState.GAME)).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            handler.getStateManager().popIState();

            //positions the player to where they entered from.
            IState currentState = handler.getStateManager().getCurrentState();
            GameState gameState = (GameState)handler.getStateManager().getIState(StateManager.GameState.GAME);
            currentState.enter(gameState.getArgs());
        } else if ( player.getCollisionBounds(0,0).intersects(world.getTransferPointCrossroadToMoutain()) ) {
            ///////////////////////////////////////////////////////
            playerPreviousExit = PlayerPreviousExit.MOUNTAIN_STATE;

            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((MountainState)handler.getStateManager().getIState(StateManager.GameState.MOUNTAIN)).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////////
            handler.getStateManager().change( StateManager.GameState.MOUNTAIN, args );
        } else if ( player.getCollisionBounds(0,0).intersects(world.getTransferPointCrossroadToTheWest()) ) {
            ///////////////////////////////////////////////////////
            playerPreviousExit = PlayerPreviousExit.THE_WEST_STATE;

            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((TheWestState)handler.getStateManager().getIState(StateManager.GameState.THE_WEST)).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////////
            handler.getStateManager().change( StateManager.GameState.THE_WEST, args );
        }
    }

    @Override
    public void render(Graphics g) {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.CROSSROAD)) {
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

    public Object[] getArgs() { return args; }

} // **** end CrossroadState class ****