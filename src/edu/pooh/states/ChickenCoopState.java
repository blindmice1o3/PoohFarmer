package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.creatures.live_stocks.Chicken;
import edu.pooh.entities.statics.produce_yields.Egg;
import edu.pooh.entities.statics.statics2x2.ShippingBin;
import edu.pooh.main.Handler;
import edu.pooh.main.ISellable;
import edu.pooh.tiles.*;
import edu.pooh.worlds.World;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ChickenCoopState
        implements IState, Serializable {

    private transient Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    private Random random;

    public ChickenCoopState(Handler handler) {
        this.handler = handler;

        world = new World(handler, World.WorldType.CHICKEN_COOP);

        random = new Random();
    } // **** end ChickenCoopState(Handler) constructor ****

    public void increaseChickenDaysInstantiated() {
        if (handler.getTimeManager().getNewDay()) {
            for (Entity e : world.getEntityManager().getEntities()) {
                if (e instanceof Chicken) {
                    ////////////////////////////////////////
                    ((Chicken)e).increaseDaysInstantiated();
                    ((Chicken)e).incrementChickenStateByDaysInstantiated();
                    ////////////////////////////////////////
                    System.out.println("ChickenCoopState.increaseChickenDaysInstantiated()... NOW AT: " +
                            ((Chicken)e).getDaysInstantiated());
                }
            }
        }
    }

    public void incrementDaysIncubating() {
        Tile[][] tempWorld = world.getTilesViaRGB();
        int tempWorldWidthInTiles = tempWorld.length;
        int tempWorldHeightInTiles = tempWorld[0].length;
        System.out.println("world tile width : " + tempWorldWidthInTiles + "\nworld tile height: " + tempWorldHeightInTiles);

        for (int y = 0; y < tempWorldHeightInTiles; y++) {
            for (int x = 0; x < tempWorldWidthInTiles; x++) {
                if (tempWorld[x][y] instanceof EggIncubatorTile) {
                    EggIncubatorTile tempEggIncubatorTile = (EggIncubatorTile)tempWorld[x][y];

                    if (tempEggIncubatorTile.isIncubating()) {
                        tempEggIncubatorTile.incrementDaysIncubating();
                    }

                    if (tempEggIncubatorTile.getDaysIncubating() == 3) {
                        for (Entity e : getWorld().getEntityManager().getEntities()) {
                            if (e instanceof Egg) {
                                if ((tempEggIncubatorTile.getX()*Tile.TILE_WIDTH == e.getX()) &&
                                        (tempEggIncubatorTile.getY()*Tile.TILE_HEIGHT == e.getY())) {
                                    ((Egg)e).setActive(false);
                                    System.out.println("Incubator's Egg object getting active set to false.");
                                }
                            }
                        }

                        int randX = 0;
                        int randY = 0;
                        boolean tempBoolean = true;

                        while (tempBoolean) {
                            randX = random.nextInt(tempWorldWidthInTiles);
                            randY = random.nextInt(tempWorldHeightInTiles);
                            System.out.println("Finding open tile to instantiate chick (x, y): (" +
                                    randX + ", " + randY + ").");
                            if (getWorld().getTile(randX, randY) instanceof DirtNotFarmableTile) {
                                DirtNotFarmableTile tempTile = (DirtNotFarmableTile)getWorld().getTile(randX, randY);
                                System.out.println("DirtNotFarmableTile found");

                                Rectangle entityCollisionRect =
                                        new Rectangle(randX*Tile.TILE_WIDTH, randY*Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

                                System.out.println("Testing staticEntity as null and no collision with entity...");
                                if ((tempTile.getStaticEntity() == null) && (checkNoEntityCollision(entityCollisionRect))) {
                                    /////////////////////////////////////////////////////////////////////
                                    Chicken tempChicken = new Chicken(handler, randX*Tile.TILE_WIDTH,
                                            randY*Tile.TILE_HEIGHT, Chicken.ChickenState.CHICK);
                                    tempChicken.setChickenState(Chicken.ChickenState.CHICK);
                                    System.out.println("Instantiated chick.");

                                    //OVERRIDDEN addEntity() in World class's constructor for
                                    // special ChickenCoopState EntityManager.
                                    world.getEntityManager().addEntity(tempChicken);
                                    tempBoolean = false;

                                    tempEggIncubatorTile.setDaysIncubating(0);
                                    tempEggIncubatorTile.setIncubating(false);
                                    //////////////////////////////////////////////////////////////////////
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkNoEntityCollision(Rectangle collisionRect) {
        for (Entity tempEntity : world.getEntityManager().getEntities()) {
            if (tempEntity.getCollisionBounds(0, 0).intersects(collisionRect)) {
                return false;
            }
        }
        return true;
    }

    private int getFodderDisplayerTotal() {
        int fodderDisplayerTotal = 0;

        for (int col = 2; col < 14; col++) {
            if (world.getTile(col, 2) instanceof FodderDisplayerTile) {
                if ( ((FodderDisplayerTile)world.getTile(col, 2)).isActivated() ) {
                    fodderDisplayerTotal++;
                }
            }
        }

        return fodderDisplayerTotal;
    }

    private int getEggLayableChickenTotal() {
        int numberOfEggLayableChicken = 0;

        for (Entity e : world.getEntityManager().getEntities()) {
            if(e instanceof Chicken) {
                Chicken tempChicken = (Chicken)e;
                if (tempChicken.getChickenState() == Chicken.ChickenState.ADULT_EGG_LAYING) {
                    numberOfEggLayableChicken++;
                }
            }
        }

        return numberOfEggLayableChicken;
    }
    private int getNonEggLayableChickenTotal() {
        int numberofNonEggLayableChicken = 0;

        for (Entity e : world.getEntityManager().getEntities()) {
            if (e instanceof Chicken) {
                Chicken tempChicken = (Chicken)e;
                if ((tempChicken.getChickenState() == Chicken.ChickenState.ADULT_GRUMPY_3) ||
                        (tempChicken.getChickenState() == Chicken.ChickenState.ADULT_GRUMPY_2) ||
                        (tempChicken.getChickenState() == Chicken.ChickenState.ADULT_GRUMPY_1)) {
                    numberofNonEggLayableChicken++;
                }
            }
        }

        return numberofNonEggLayableChicken;
    }
    private void determineWhichChickenNotFedFodder(int numberOfHungryChicken) {
        ArrayList<Chicken> chickenOrderedByGrumpiness = new ArrayList<Chicken>();

        for (Entity e : world.getEntityManager().getEntities()) {
            if ((e instanceof Chicken) &&
                    (((Chicken)e).getChickenState() == Chicken.ChickenState.ADULT_GRUMPY_3)) {
                chickenOrderedByGrumpiness.add((Chicken)e);
            }
        }
        for (Entity e : world.getEntityManager().getEntities()) {
            if ((e instanceof Chicken) &&
                    (((Chicken)e).getChickenState() == Chicken.ChickenState.ADULT_GRUMPY_2)) {
                chickenOrderedByGrumpiness.add((Chicken)e);
            }
        }
        for (Entity e : world.getEntityManager().getEntities()) {
            if ((e instanceof Chicken) &&
                    (((Chicken)e).getChickenState() == Chicken.ChickenState.ADULT_GRUMPY_1)) {
                chickenOrderedByGrumpiness.add((Chicken)e);
            }
        }
        for (Entity e : world.getEntityManager().getEntities()) {
            if ((e instanceof Chicken) &&
                    (((Chicken)e).getChickenState() == Chicken.ChickenState.ADULT_EGG_LAYING)) {
                chickenOrderedByGrumpiness.add((Chicken)e);
            }
        }

        // Feeding the chicken at the end of the list (least grumpy first).
        int numberOfFodder = getFodderDisplayerTotal();
        while (numberOfFodder > 0) {
            decrementGrumpinessState(chickenOrderedByGrumpiness.get(chickenOrderedByGrumpiness.size()-1));
            chickenOrderedByGrumpiness.remove(chickenOrderedByGrumpiness.size()-1);

            numberOfFodder--;
        }

        // The rest goes hungry.
        for (Chicken chicken : chickenOrderedByGrumpiness) {
            chicken.setChickenState(Chicken.ChickenState.ADULT_GRUMPY_3);
        }
    }
    private void decrementGrumpinessState(Chicken chicken) {
        switch (chicken.getChickenState()) {
            case ADULT_GRUMPY_3:
                chicken.setChickenState(Chicken.ChickenState.ADULT_GRUMPY_2);
                break;
            case ADULT_GRUMPY_2:
                chicken.setChickenState(Chicken.ChickenState.ADULT_GRUMPY_1);
                break;
            case ADULT_GRUMPY_1:
                chicken.setChickenState(Chicken.ChickenState.ADULT_EGG_LAYING);
                break;
            default:
                System.out.println("ChickenCoopState.decrementGrumpinessState(Chicken) switch-construct default statement.");
                break;
        }
    }
    public void instantiateEggBasedOnFodderDisplayerTile() {
        // Which is less, number of chicken (adult state and egg laying) or the number of feed?
        int numberOfEgg = Math.min(getFodderDisplayerTotal(), getEggLayableChickenTotal());
        System.out.println("FODDER-DISPLAYER-TOTAL: " + getFodderDisplayerTotal() +
                "\nEGG-LAYABLE-CHICKEN-TOTAL: " + getEggLayableChickenTotal());

        int numberOfHungryChicken = (getEggLayableChickenTotal() + getNonEggLayableChickenTotal()) -
                getFodderDisplayerTotal();

        if (numberOfHungryChicken <= 0) {
            //Situation1 (ALL CHICKEN EAT)
            for (Entity e : world.getEntityManager().getEntities()) {
                if (e instanceof Chicken) {
                    decrementGrumpinessState((Chicken)e);
                }
            }
        } else {
            //Situation2 (ALL CHICKEN HUNGRY) && Situation3 (SOME CHICKEN EAT, SOME CHICKEN HUNGRY)
            determineWhichChickenNotFedFodder(numberOfHungryChicken);
        }

        // Instantiate Egg object and decrement fodderDisplayerTotal until it reaches 0.
        while (numberOfEgg > 0) {
            int randX = random.nextInt(world.getWidthInTiles());
            int randY = random.nextInt(world.getHeightInTiles());

            Tile tempTile = world.getTile(randX, randY);

            if (tempTile instanceof DirtNotFarmableTile) {
                // No previous Egg object set on this random tile.
                if (((DirtNotFarmableTile)tempTile).getStaticEntity() == null) {
                    Rectangle tempCollisionRect = new Rectangle(randX*Tile.TILE_WIDTH, randY*Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
                    // No entity collision in this random tile.
                    if (checkNoEntityCollision(tempCollisionRect)) {
                        /////////////////////////////////////////////////////////////////////
                        Egg tempEgg = new Egg(handler, randX*Tile.TILE_WIDTH, randY*Tile.TILE_HEIGHT);
                        ((DirtNotFarmableTile)tempTile).setStaticEntity(tempEgg);
                        world.getEntityManager().addEntity( tempEgg );
                        numberOfEgg--;
                        //////////////////////////////////////////////////////////////////////
                    }
                }
            }
        }
    }

    public void setAllFodderDisplayerTileActivatedToFalse() {
        for (int col = 2; col < 14; col++) {
            if (world.getTile(col, 2) instanceof FodderDisplayerTile) {
                if ( ((FodderDisplayerTile)world.getTile(col, 2)).isActivated() ) {
                    ((FodderDisplayerTile)world.getTile(col, 2)).setActivated(false);
                }
            }
        }
    }

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



        //@@@@@@@@@@@@@@@@@@@@@
        ShippingBin tempShippingBin = null;
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e instanceof ShippingBin) {
                tempShippingBin = (ShippingBin)e;
            }
        }
        if (tempShippingBin != null) {
            if (tempShippingBin.getInventory().size() != 0) {
                for (ISellable tempSellable : tempShippingBin.getInventory()) {
                    System.out.println(tempSellable);
                }
            }
        }
        //@@@@@@@@@@@@@@@@@@@@@
    }

    @Override
    public void exit() {
        ///////////////////////////////////////////////////
        if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
            Entity tempHoldableEntity = (Entity) player.getHoldableObject();

            if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                ((GameState)handler.getStateManager().getIState(StateManager.GameState.GAME)).getWorld().getEntityManager().addEntity(
                        tempHoldableEntity
                );
            }
        }
        ///////////////////////////////////////////////////
    }

    @Override
    public void tick() {
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.CHICKEN_COOP)) {
            return;
        }

        ///////////////
        world.tick();
        ///////////////

        checkTransferPoints();
    }

    private void checkTransferPoints() {
        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointChickenCoopToGame()) ) {
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
                handler.getStateManager().getIState(StateManager.GameState.CHICKEN_COOP)) {
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

} // **** end ChickenCoopState class ****