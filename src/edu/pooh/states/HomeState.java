package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.main.Handler;
import edu.pooh.time.TimeManager;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;

public class HomeState implements IState {

    private Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    public HomeState(Handler handler) {
        this.handler = handler;

        world = new World(handler, World.WorldType.HOME);
    } // **** end HomeState(Handler) constructor ****

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
        if (TimeManager.getNewDay()) {
            // INCREASE CropEntity int daysWatered IF DirtNormalTile HAD ITS watered SET TO TRUE THE PREVIOUS DAY.
            ((GameState)handler.getGame().getGameState()).increaseCropEntityDaysWatered();
            // RESET ALL DirtNormalTile objects' boolean watered TO FALSE.
            ((GameState)handler.getGame().getGameState()).setAllDirtNormalTileWateredToFalse();

            //////// RESET TIME FOR NEW DAY /////////
            TimeManager.incrementElapsedInGameDays();
            TimeManager.setNewDayFalse();
            TimeManager.resetElapsedRealSeconds();
            /////////////////////////////////////////

            TimeManager.setClockRunningTrue();
        }

        TimeManager.translateElapsedInGameDaysToGameYearsSeasonsMonthsDays();
    }

    @Override
    public void tick() {
        if (StateManager.getCurrentState() != handler.getGame().getHomeState()) {
            return;
        }

        ///////////////
        world.tick();
        ///////////////

        checkTransferPoints();
    }

    private void checkTransferPoints() {
        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointHomeToGame()) ) {
            StateManager.change(handler.getGame().getGameState(), args);
        }
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentState() != handler.getGame().getHomeState()) {
            return;
        }

        ////////////////
        world.render(g);
        ////////////////
    }

} // **** end HomeState class ****