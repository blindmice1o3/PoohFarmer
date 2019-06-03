package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.creatures.live_stocks.Cow;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.tiles.FodderDisplayerTile;
import edu.pooh.tiles.FodderExecutorTile;
import edu.pooh.tiles.SolidGenericTile;
import edu.pooh.tiles.Tile;
import edu.pooh.time.TimeManager;
import edu.pooh.worlds.World;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CowBarnState implements IState {

    private Handler handler;
    private World world;

    private FodderDisplayerTile[] fodderDisplayerTileArray;
    private FodderExecutorTile fodderToCowIncubator;
    private Map<FodderExecutorTile, Cow> fodderToCowIncubatorHashMap;
    private Map<FodderDisplayerTile, Cow> fodderToCowHashMap;

    private int originalStallIndexOfPregnant = 12;
    private int stallIndexOfUnbornBaby = 12;
    public int getOriginalStallIndexOfPregnant() { return originalStallIndexOfPregnant; }
    public void setOriginalStallIndexOfPregnant(int originalStallIndexOfPregnant) { this.originalStallIndexOfPregnant = originalStallIndexOfPregnant; }

    private Object[] args;
    private Player player;

    public CowBarnState(Handler handler) {
        this.handler = handler;

        world = new World(handler, World.WorldType.COW_BARN);

        initFodderDisplayerTile();
        fodderToCowIncubatorHashMap = new HashMap<FodderExecutorTile, Cow>();
        fodderToCowHashMap = new HashMap<FodderDisplayerTile, Cow>();


        //@@@@@@@@@@@@@
        // CowBarnState needs to track which FodderDisplayTile to associate w specific Cow instances.
        for (Entity e : world.getEntityManager().getEntities()) {
            if (e instanceof Cow) {
                assignCowToFodderDisplayerTile( (Cow)e );
            }
        }
        //@@@@@@@@@@@@@
    } // **** end CowBarnState(Handler) constructor ****

    public void initFodderDisplayerTile() {
        fodderDisplayerTileArray = new FodderDisplayerTile[12];

        for (int yy = 0; yy < world.getHeightInTiles(); yy++) {
            for (int xx = 0; xx < world.getWidthInTiles(); xx++) {
                if (world.getTile(xx, yy) instanceof FodderDisplayerTile) {
                    FodderDisplayerTile tempFodderDisplayerTile = (FodderDisplayerTile)world.getTile(xx, yy);
                    fodderDisplayerTileArray[ tempFodderDisplayerTile.getIndex() ] = tempFodderDisplayerTile;
                } else if ( (world.getTile(xx, yy) instanceof FodderExecutorTile) &&
                        (world.getTile((xx-1), yy) instanceof SolidGenericTile) ) {
                    FodderExecutorTile tempFodderExecutorTile = (FodderExecutorTile)world.getTile(xx, yy);
                    fodderToCowIncubator = tempFodderExecutorTile;
                }
            }
        }
    }

    public void unassignCowFromFodderDisplayerTile(Cow cow) {
        fodderToCowHashMap.remove(fodderDisplayerTileArray[cow.getFodderDisplayerTileArrayIndex()]);
        System.out.println("Unassigning " + cow + " from this stall index: " + cow.getFodderDisplayerTileArrayIndex());
    }

    public void unassignCowFromCowIncubatorHashMap() {
        fodderToCowIncubatorHashMap.remove(fodderToCowIncubator);
        System.out.println("Unassigning cow from COW INCUBATOR STALL.");
    }

    public void assignCowToFodderDisplayerTile(Cow cow) {
        ////////////////////////////////////////////////////////////////
        if (cow.getFodderDisplayerTileArrayIndex() != 12) {
            fodderToCowHashMap.put(fodderDisplayerTileArray[cow.getFodderDisplayerTileArrayIndex()], cow);
            System.out.println("Index for fodderDisplayTile array: " + cow.getFodderDisplayerTileArrayIndex() +
                    " (for cow: " + cow + ").");
        } else {
            fodderToCowIncubatorHashMap.put(fodderToCowIncubator, cow);
        }
        ////////////////////////////////////////////////////////////////
    }
    public void determineWhichCowNotFedFodder() {
        for (int i = 0; i < 12; i++) {
            if (fodderToCowHashMap.get(fodderDisplayerTileArray[i]) != null) {
                Cow tempCow = fodderToCowHashMap.get(fodderDisplayerTileArray[i]);

                //If no food... potential CowHealth change. (!!!NOT!!! BABY or CALF)
                if ( (!fodderDisplayerTileArray[i].isActivated()) &&
                        ((tempCow.getCowState() == Cow.CowState.ADULT_1) ||
                                (tempCow.getCowState() == Cow.CowState.ADULT_2) ||
                                (tempCow.getCowState() == Cow.CowState.ADULT_3)) ) {

                    tempCow.decreaseAffectionScore(8);

                    //50% chance of getting sick if adult, non-pregnant isn't fed.
                    //decrease affectionScore by 20 if becomes SICK (by 30 if becomes CRANKY)
                    if (tempCow.getRandom().nextInt(100) < 50) {   // [0-100), excludes 100
                        tempCow.setCowHealth(Cow.CowHealth.SICK);
                        tempCow.decreaseAffectionScore(20);
                    }
                }
            }
        }

        if (fodderToCowIncubatorHashMap.get(fodderToCowIncubator) != null) {
            Cow tempCow = fodderToCowIncubatorHashMap.get(fodderToCowIncubator);

            if ( !(fodderToCowIncubator.isSpecialActive()) && (tempCow.getCowState() == Cow.CowState.PREGNANT) ) {
                //PREGNANT does not get sick, but does lose affectionScore.
                tempCow.decreaseAffectionScore(8);
            }
        }
    }

    public void setAllFodderDisplayerTileActivatedToFalse() {
        for (FodderDisplayerTile tempFodderDisplayerTile : fodderDisplayerTileArray) {
            if ( tempFodderDisplayerTile.isActivated() ) {
                tempFodderDisplayerTile.setActivated(false);
            }
        }

        if (fodderToCowIncubator.isSpecialActive()) {
            fodderToCowIncubator.setSpecialActive(false);
        }
    }

    public void increaseCowDaysInstantiated() {
        if (TimeManager.getNewDay()) {
            for (Entity e : world.getEntityManager().getEntities()) {
                if (e instanceof Cow) {
                    ////////////////////////////////////////////////
                    // ALL cows increase in age.
                    ((Cow)e).increaseDaysInstantiated();
                    // ALL cows update CowState.
                    ((Cow)e).incrementCowStateByDaysInstantiated();
                    ////////////////////////////////////////////////
                }
            }
        }
    }

    public void increaseCowDaysImpregnanted() {
        if (TimeManager.getNewDay()) {
            for (Entity e : world.getEntityManager().getEntities()) {
                if ( (e instanceof Cow) && (((Cow)e).getCowState() == Cow.CowState.PREGNANT) ) {
                    Cow pregnantCow = (Cow)e;
                    ////////////////////////////////////////////////////////////////////////////////
                    pregnantCow.increaseDaysImpregnanted();

                    //BIRTHING CODE.
                    if ( pregnantCow.getDaysImpregnanted() == 21 ) {
                        System.out.println("INSTANTIATING BABY COW");
                        //Instantiate baby cow next to pregnant cow, assign stall.
                        //!!!!HARD-CODED location!!!! bad... rework later!!!
                        Cow babyCow = new Cow( handler, (3 * Tile.TILE_WIDTH), ((3 * Tile.TILE_HEIGHT)),
                                Cow.CowState.BABY, stallIndexOfUnbornBaby);
                        //EVERYTIME A COW IS INSTANTIATED it increases the counter (which was done once already in
                        //CowArtificialInseminator... so must NOT increase it again here... subtract 1.
                        ResourceManager.decreaseCowCounter(1);
                        babyCow.setDaysInstantiated(0);
                        babyCow.setAffectionScore(0);
                        assignCowToFodderDisplayerTile(babyCow);
                        world.getEntityManager().addEntity(babyCow);
                        //world.getEntityManager().setToBeAdded(true);
                        //Reset stallIndexOfUnbornBaby.
                        stallIndexOfUnbornBaby = 12;

                        //Now the mother.
                        System.out.println("REWORKING MOTHER COW");
                        pregnantCow.setDaysImpregnanted(0);
                        pregnantCow.setCowState(Cow.CowState.ADULT_1); //MUST DO BEFORE incrementCowStateByDaysInstantiated()!
                        //MAY NOT HAVE BEEN ADULT_1... re-determine if ADULT_1, ADULT_2, or ADULT_3.
                        pregnantCow.incrementCowStateByDaysInstantiated();
                        //AFTER BIRTHING, remove PREGNANT cow's association with cow incubator feeding stall.
                        pregnantCow.setFodderDisplayerTileArrayIndex(originalStallIndexOfPregnant);
                        assignCowToFodderDisplayerTile(pregnantCow);
                        unassignCowFromCowIncubatorHashMap();

                        //MUST remember to setOriginalStallIndexOfPregnant(12) AFTER BIRTHING!!!!!!
                        //Signifies no pregnant cow in population... allow CowArtificialInseminator to be used again.
                        originalStallIndexOfPregnant = 12;
                        System.out.println("BIRTHING COMPLETE");
                    }

                    break;
                    ///////////////////////////////////////////////////////////////////////////////
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

    // GETTERS AND SETTERS

    public World getWorld() {
        return world;
    }

    public void setStallIndexOfUnbornBaby(int stallIndexOfUnbornBaby) { this.stallIndexOfUnbornBaby = stallIndexOfUnbornBaby; }

} // **** end CowBarnState class ****