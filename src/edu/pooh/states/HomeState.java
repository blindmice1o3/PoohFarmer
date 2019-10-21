package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;
import java.io.Serializable;

public class HomeState
        implements IState, Serializable {

    private transient Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    public HomeState(Handler handler) {
        this.handler = handler;

        world = new World(handler, World.WorldType.HOME);
    } // **** end HomeState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        handler.getTimeManager().setClockRunningFalse();

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
        if (handler.getTimeManager().getNewDay()) {
            /** Daily GameState method calls. */
            // INCREASE CropEntity int daysWatered IF DirtNormalTile HAD ITS watered SET TO TRUE THE PREVIOUS DAY.
            ((GameState)handler.getStateManager().getIState(StateManager.GameState.GAME)).increaseCropEntityDaysWatered();
            // SWAP CropEntity currentImage from wet-version to dry-version
            for (Entity e : ((GameState)handler.getStateManager().getIState(StateManager.GameState.GAME)).getWorld().getEntityManager().getEntities()) {
                if (e instanceof CropEntity) {
                    ((CropEntity)e).swapCurrentImageWetToDry();
                }
            }
            // RESET ALL DirtNormalTile objects' boolean watered TO FALSE.
            ((GameState)handler.getStateManager().getIState(StateManager.GameState.GAME)).setAllDirtNormalTileWateredToFalse();



            /** Daily ChickenCoopState method calls. */
            //Egg INSTANTIATION is handled by ChickenCoopState, this is different for Milk.
            //TODO: WHAT ABOUT CHICKEN THAT HAVE BEEN BROUGHT AND LEFT TO HomeState ?????
            // @@@@ DO EGG INSTANTIATION BEFORE INCREASING daysInstantiated (prevent bug [using extra fodder]) @@@@
            // MISSED FEEDING: chicken egg-laying state to chicken grumpy state.
            ((ChickenCoopState)handler.getStateManager().getIState(StateManager.GameState.CHICKEN_COOP)).instantiateEggBasedOnFodderDisplayerTile();
            // (BE SURE TO RESET ALL DISPLAY TILE TO FALSE)
            ((ChickenCoopState)handler.getStateManager().getIState(StateManager.GameState.CHICKEN_COOP)).setAllFodderDisplayerTileActivatedToFalse();
            // INCREASE Chicken int daysInstantiated if IT'S LESS THAN 7 daysInstantiated.
            ((ChickenCoopState)handler.getStateManager().getIState(StateManager.GameState.CHICKEN_COOP)).increaseChickenDaysInstantiated();
            // CHECK INCUBATOR
            ((ChickenCoopState)handler.getStateManager().getIState(StateManager.GameState.CHICKEN_COOP)).incrementDaysIncubating();



            /** Daily CowBarnState method calls. */
            /* feeding system [Map data structure to associate each instance of FodderDisplayTile w corresponding Cow instance] */
            //CHECK FodderDisplayTile... determine which ADULT/PREGNANT cows were fed... increment/decrement affectionScore.
            ((CowBarnState)handler.getStateManager().getIState(StateManager.GameState.COW_BARN)).determineWhichCowNotFedFodder();

            /* DEVELOP Aging system */
            //INCREASE daysInstantiated for ALL Cow instances.
            //Based on updated daysInstantiated/affectionScore/daysImpregnanted, determine/update each cow's cowState.
            ((CowBarnState)handler.getStateManager().getIState(StateManager.GameState.COW_BARN)).increaseCowDaysInstantiated();

            /* DEVELOP Birthing/Pregnancy/Incubating system */   //SEE AGING SYSTEM!!!!! should probably separate later.
            //PREGNANT cow update. Birthing check (instantiate BABY with dayInstantiated = 0).
            // (MAY NEED TO FORCE PLAYER to name this new cow when entering CowBarnState).
            //INCREASE daysImpregnanted for PREGNANT Cow instance.
            ((CowBarnState)handler.getStateManager().getIState(StateManager.GameState.COW_BARN)).increaseCowDaysImpregnanted(); //BIRTHING CODE.


            /* DEVELOP Brush and Milker [Item/Tool's concrete subclasses] */
            //brushed and talkedTo (probably boolean) should also be RESET.
            ((CowBarnState)handler.getStateManager().getIState(StateManager.GameState.COW_BARN)).setAllCowBrushedAndMilkedToFalse();
            //CLEAR FodderDisplayTile.
            ((CowBarnState)handler.getStateManager().getIState(StateManager.GameState.COW_BARN)).setAllFodderDisplayerTileActivatedToFalse();



            //////// RESET TIME FOR NEW DAY /////////
            handler.getTimeManager().incrementElapsedInGameDays();
            handler.getTimeManager().setNewDayFalse();
            handler.getTimeManager().resetElapsedRealSeconds();
            /////////////////////////////////////////

            handler.getTimeManager().setClockRunningTrue();
        }

        ///////////////////////////////////////////////////
        if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
            Entity tempHoldableEntity = (Entity)player.getHoldableObject();

            if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                ((GameState)handler.getStateManager().getIState(StateManager.GameState.GAME)).getWorld().getEntityManager().addEntity(
                        tempHoldableEntity
                );
            }
        }
        ///////////////////////////////////////////////////

        handler.getTimeManager().translateElapsedInGameDaysToGameYearsSeasonsMonthsDays();
    }

    @Override
    public void tick() {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.HOME)) {
            return;
        }

        ///////////////
        world.tick();
        ///////////////

        checkTransferPoints();
    }

    private void checkTransferPoints() {
        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointHomeToGame()) ) {
            handler.getStateManager().popIState();

            //positions the player to where they entered from.
            IState currentState = handler.getStateManager().getCurrentState();
            GameState gameState = (GameState)handler.getStateManager().getIState(StateManager.GameState.GAME);
            currentState.enter(gameState.getArgs());
        }
    }

    @Override
    public void render(Graphics g) {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.HOME)) {
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

} // **** end HomeState class ****