package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.creatures.live_stocks.Chicken;
import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.entities.statics.produce_yields.Egg;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.tiles.*;
import edu.pooh.time.TimeManager;
import edu.pooh.worlds.World;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class ChickenCoopState implements IState {

    private Handler handler;
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
        if (TimeManager.getNewDay()) {
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

                    if (tempEggIncubatorTile.getDaysIncubating() > 2) {
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

    public void instantiateEggBasedOnFodderDisplayerTile() {
        //TODO: Which is less, number of chicken (adult state and egg laying) or the number of feed?
        //int numberOfEgg = Math.min(getFodderDisplayerTotal(), ResourceManager.getChickenCounter());

        int numberOfEgg = getFodderDisplayerTotal();

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

    public World getWorld() {
        return world;
    }

} // **** end ChickenCoopState class ****