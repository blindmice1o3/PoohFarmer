package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;
import edu.pooh.time.TimeManager;
import edu.pooh.worlds.World;

import java.awt.*;

public class GameState implements IState {

    private Handler handler;
    private World world;

    private Object[] args;
    private Player player;

    public GameState(Handler handler) {
        this.handler = handler;
        args = new Object[10];

        world = new World(handler, World.WorldType.GAME);
        handler.setWorld(world);    // IMPORTANT TO DO IN THIS ORDER, create world, then handler's setWorld().
                                    // NOW IT SETS the enum WorldType worldType for the world as well!!!!

        player = handler.getWorld().getEntityManager().getPlayer();
        args[1] = player.getX();
        args[2] = player.getY();
    } // **** end GameState(Handler) constructor ****


    @Override
    public void enter(Object[] args) {
        TimeManager.setClockRunningTrue();

        handler.setWorld(world);

        this.args = args;

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        player.setPosition((int)args[1], (int)args[2]);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////

        world.getEntityManager().addEntity(player);
        world.getEntityManager().setPlayer(player);
    }

    @Override
    public void exit() {
        args[0] = player;
        args[1] = (int)player.getX();
        args[2] = (int)player.getY();
    }

    @Override
    public void tick() {
        if (StateManager.getCurrentState() != handler.getGame().getGameState()) {
            return;
        }

        /////////////
        world.tick();
        /////////////

        checkTransferPoints();
    }

    private void checkTransferPoints() {
        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToHome()) ) {
            ///////////////////////////////////////////////////
            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((HomeState)handler.getGame().getHomeState()).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            StateManager.change(handler.getGame().getHomeState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToChickenCoop()) ) {
            ///////////////////////////////////////////////////
            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((ChickenCoopState)handler.getGame().getChickenCoopState()).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            StateManager.change(handler.getGame().getChickenCoopState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToCowBarn()) ) {
            ///////////////////////////////////////////////////
            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((CowBarnState)handler.getGame().getCowBarnState()).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            StateManager.change(handler.getGame().getCowBarnState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToToolShed()) ) {
            ///////////////////////////////////////////////////
            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((ToolShedState)handler.getGame().getToolShedState()).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            StateManager.change(handler.getGame().getToolShedState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToCrossroad()) ) {
            ///////////////////////////////////////////////////
            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((CrossroadState)handler.getGame().getCrossroadState()).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            StateManager.change(handler.getGame().getCrossroadState(),args);
        }
    }

    public void increaseCropEntityDaysWatered() {
        System.out.println("GameState.increaseCropEntityDaysWatered()");

        if (TimeManager.getNewDay()) {
            Tile[][] tempWorld = world.getTilesViaRGB();

            for (int x = 0; x < tempWorld.length; x++) {
                for (int y = 0; y < tempWorld[x].length; y++) {
                    if (tempWorld[x][y] instanceof DirtNormalTile) {
                        DirtNormalTile tempTile = (DirtNormalTile)tempWorld[x][y];
                        if ( (tempTile.getStaticEntity() != null) &&
                                (tempTile.getStaticEntity() instanceof CropEntity) ) {
                            // For grass, just increment daysWatered everyday (except during season == WINTER)
                            // whether its tile was watered or unwatered the previous day.
                            if (((CropEntity)tempTile.getStaticEntity()).getCropType() == CropEntity.CropType.GRASS) {
                                System.out.println("GameState.increaseCropEntityDaysWatered() successful.");
                                ///////////////////////////////////////////////////////////////
                                ((CropEntity)tempTile.getStaticEntity()).increaseDaysWatered();
                                ((CropEntity)tempTile.getStaticEntity()).incrementLifeCycleByDaysWatered();
                                ///////////////////////////////////////////////////////////////
                            }
                            // All other CropEntity need to have been watered the previous day
                            // to get its daysWatered incremented.
                            else if (tempTile.isWatered()) {
                                System.out.println("GameState.increaseCropEntityDaysWatered() successful.");
                                ///////////////////////////////////////////////////////////////
                                ((CropEntity)tempTile.getStaticEntity()).increaseDaysWatered();
                                ((CropEntity)tempTile.getStaticEntity()).incrementLifeCycleByDaysWatered();
                                ///////////////////////////////////////////////////////////////
                            }
                        }
                    }
                }
            }

        }
    }

    public void setAllDirtNormalTileWateredToFalse() {
        if (TimeManager.getNewDay()) {
            Tile[][] tempWorld = world.getTilesViaRGB();

            for (int x = 0; x < tempWorld.length; x++) {
                for (int y = 0; y < tempWorld[x].length; y++) {
                    if (tempWorld[x][y] instanceof DirtNormalTile) {
                        DirtNormalTile tempTile = (DirtNormalTile)tempWorld[x][y];

                        ///////////////////////////
                        tempTile.setWatered(false);
                        ///////////////////////////

                        //DirtState.SEEDED
                        if (tempTile.getDirtState() == DirtNormalTile.DirtState.TILLED) {
                            tempTile.setTexture(Assets.dirtTilledDry);
                        }
                        //DirtState.TILLED
                        else if (tempTile.getDirtState() == DirtNormalTile.DirtState.SEEDED) {
                            tempTile.setTexture(Assets.dirtSeededDry);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentState() != handler.getGame().getGameState()) {
            return;
        }

        ////////////////
        world.render(g);
        ////////////////
    }

    public World getWorld() {
        return world;
    }

} // **** end GameState class ****