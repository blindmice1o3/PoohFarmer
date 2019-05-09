package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.time.TimeManager;
import edu.pooh.worlds.World;

import java.awt.*;

public class ChickenCoopState implements IState {

    private Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    public ChickenCoopState(Handler handler) {
        this.handler = handler;

        world = new World(handler, World.WorldType.CHICKEN_COOP);
    } // **** end ChickenCoopState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        TimeManager.setClockRunningFalse();

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

    }

    @Override
    public void tick() {
        if (StateManager.getCurrentState() != handler.getGame().getChickenCoopState()) {
            return;
        }

        ///////////////
        world.tick();
        ///////////////

        checkTransferPoints();
    }

    private void checkTransferPoints() {
        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointChickenCoopToGame()) ) {
            StateManager.change(handler.getGame().getGameState(), args);
        }
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentState() != handler.getGame().getChickenCoopState()) {
            return;
        }

        ////////////////
        world.render(g);
        ////////////////
    }

} // **** end ChickenCoopState class ****