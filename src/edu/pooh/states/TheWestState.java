package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Player;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.time.TimeManager;
import edu.pooh.worlds.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TheWestState implements IState {

    private Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    public TheWestState(Handler handler) {
        this.handler = handler;

        world = new World(handler, World.WorldType.THE_WEST);
    } // **** end TheWestState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
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
        if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
            Entity tempHoldableEntity = (Entity) player.getHoldableObject();

            if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                ((CrossroadState)handler.getStateManager().getIState(StateManager.GameState.CROSSROAD)).getWorld().getEntityManager().addEntity(
                        tempHoldableEntity
                );
            }
        }
    }

    private boolean jumping = false;
    private boolean falling = false;
    private int initialJumpY = 0;
    private int maxJumpHeight = 100;
    private int deltaY = 5;
    @Override
    public void tick() {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.THE_WEST)) {
            return;
        }

        // Every tick(), move player left 1 pixel, loop back if reach left end of background image.
        /////////////////////////////////
        if (!world.getTile((int)((player.getX()/Tile.TILE_WIDTH))-1, (int)(player.getY()/Tile.TILE_HEIGHT)).isSolid()) {
            player.setX((player.getX() - 1));
        }
        /////////////////////////////////
        if (player.getX() == ((handler.getWidth()/2) - (player.getWidth()/2))) {
            player.setX((world.getWidthInTiles()*Tile.TILE_WIDTH) - (handler.getWidth()/2) - (player.getWidth()/2));
        }

        // Check jumping.
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
            jumping = true;
            initialJumpY = (int)player.getY();
        }
        if (jumping) {
            if ((initialJumpY - player.getY()) >= maxJumpHeight) {
                falling = true;
                jumping = false;
            } else {
                player.setY( (player.getY() - deltaY) );
            }
        } else if (falling) {
            if (player.getY() >= initialJumpY) {
                falling = false;
            } else {
                player.setY( (player.getY() + deltaY) );
            }
        }

        ///////////////
        world.tick();
        ///////////////

        checkTransferPoints();
    }

    private void checkTransferPoints() {
        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointTheWestToCrossroad()) ) {
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
                handler.getStateManager().getIState(StateManager.GameState.THE_WEST)) {
            return;
        }

        ////////////////
        world.render(g);
        ////////////////
    }

    public World getWorld() {
        return world;
    }

} // **** end TheWestState class ****