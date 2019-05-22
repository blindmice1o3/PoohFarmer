package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.statics.crops.CropEntity;
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
            /** Daily GameState method calls. */
            // INCREASE CropEntity int daysWatered IF DirtNormalTile HAD ITS watered SET TO TRUE THE PREVIOUS DAY.
            ((GameState)handler.getGame().getGameState()).increaseCropEntityDaysWatered();
            // SWAP CropEntity currentImage from wet-version to dry-version
            for (Entity e : ((GameState)handler.getGame().getGameState()).getWorld().getEntityManager().getEntities()) {
                if (e instanceof CropEntity) {
                    ((CropEntity)e).swapCurrentImageWetToDry();
                }
            }
            // RESET ALL DirtNormalTile objects' boolean watered TO FALSE.
            ((GameState)handler.getGame().getGameState()).setAllDirtNormalTileWateredToFalse();


            /** Daily ChickenCoopState method calls. */
            // Number of FodderDisplayerTile object whose activated is set to true.
            ((ChickenCoopState)handler.getGame().getChickenCoopState()).instantiateEggBasedOnFodderDisplayerTile();

            //TODO: chicken egg-laying state to chicken grumpy state.

            // (BE SURE TO RESET ALL DISPLAY TILE TO FALSE)
            ((ChickenCoopState)handler.getGame().getChickenCoopState()).setAllFodderDisplayerTileActivatedToFalse();
            // @@@@ DO EGG INSTANTIATION BEFORE INCREASING daysInstantiated (prevent bug [using extra fodder]) @@@@
            // INCREASE Chicken int daysInstantiated if IT'S LESS THAN 7 daysInstantiated.
            ((ChickenCoopState)handler.getGame().getChickenCoopState()).increaseChickenDaysInstantiated();
            // CHECK INCUBATOR
            ((ChickenCoopState)handler.getGame().getChickenCoopState()).incrementDaysIncubating();



            //////// RESET TIME FOR NEW DAY /////////
            TimeManager.incrementElapsedInGameDays();
            TimeManager.setNewDayFalse();
            TimeManager.resetElapsedRealSeconds();
            /////////////////////////////////////////

            TimeManager.setClockRunningTrue();
        }

        ///////////////////////////////////////////////////
        if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
            Entity tempHoldableEntity = (Entity)player.getHoldableObject();

            if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                ((GameState)handler.getGame().getGameState()).getWorld().getEntityManager().addEntity(
                        tempHoldableEntity
                );
            }
        }
        ///////////////////////////////////////////////////

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

    public World getWorld() {
        return world;
    }

} // **** end HomeState class ****