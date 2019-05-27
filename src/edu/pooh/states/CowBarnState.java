package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.creatures.live_stocks.Cow;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.time.TimeManager;
import edu.pooh.worlds.World;

import java.awt.*;

public class CowBarnState implements IState {

    private Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    public CowBarnState(Handler handler) {
        this.handler = handler;

        world = new World(handler, World.WorldType.COW_BARN);
    } // **** end CowBarnState(Handler) constructor ****

    public void increaseCowDaysInstantiated() {
        if (TimeManager.getNewDay()) {
            for (Entity e : world.getEntityManager().getEntities()) {
                if (e instanceof Cow) {
                    ////////////////////////////////////////
                    ((Cow)e).increaseDaysInstantiated();
                    ((Cow)e).incrementCowStateByDaysInstantiated();
                    ////////////////////////////////////////
                    System.out.println("CowBarnState.increaseCowDaysInstantiated()... NOW AT: " +
                            ((Cow)e).getDaysInstantiated());
                }
            }
        }
    }

    public void setAllCowBrushedAndMilkedToFalse() {
        for (Entity e : world.getEntityManager().getEntities()) {
            if (e instanceof Cow) {
                ((Cow)e).setBrushed(false);
                ((Cow)e).setMilked(false);
            }
        }
        System.out.println("CowBarnState.setAllCowBrushedAndMilkedToFalse()...");
    }

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
        ///////////////////////////////////////////////////
        if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
            Entity tempHoldableEntity = (Entity) player.getHoldableObject();

            if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                ((GameState)handler.getGame().getGameState()).getWorld().getEntityManager().addEntity(
                        tempHoldableEntity
                );
            }
        }
        ///////////////////////////////////////////////////
    }

    @Override
    public void tick() {
        if (StateManager.getCurrentState() != handler.getGame().getCowBarnState()) {
            return;
        }

        ///////////////
        world.tick();
        ///////////////

        checkTransferPoints();
    }

    private void checkTransferPoints() {
        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointCowBarnToGame()) ) {
            StateManager.change(handler.getGame().getGameState(), args);
        }
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentState() != handler.getGame().getCowBarnState()) {
            return;
        }

        ////////////////
        world.render(g);
        ////////////////
    }

    public World getWorld() {
        return world;
    }

} // **** end CowBarnState class ****