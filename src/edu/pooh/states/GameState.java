package edu.pooh.states;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;
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
        handler.getTimeManager().setClockRunningTrue();

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
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.GAME)) {
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
                    ((HomeState)handler.getStateManager().getIState(StateManager.GameState.HOME)).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            handler.getStateManager().change( StateManager.GameState.HOME, args );
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToChickenCoop()) ) {
            ///////////////////////////////////////////////////
            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((ChickenCoopState)handler.getStateManager().getIState(StateManager.GameState.CHICKEN_COOP)).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            handler.getStateManager().change( StateManager.GameState.CHICKEN_COOP, args );
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToCowBarn()) ) {
            ///////////////////////////////////////////////////
            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((CowBarnState)handler.getStateManager().getIState(StateManager.GameState.COW_BARN)).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            handler.getStateManager().change( StateManager.GameState.COW_BARN, args );
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToToolShed()) ) {
            ///////////////////////////////////////////////////
            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((ToolShedState)handler.getStateManager().getIState(StateManager.GameState.TOOL_SHED)).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            handler.getStateManager().change( StateManager.GameState.TOOL_SHED, args );
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToCrossroad()) ) {
            ///////////////////////////////////////////////////
            if ((player.getHoldableObject() != null) && (player.getHoldableObject() instanceof Entity)) {
                Entity tempHoldableEntity = (Entity) player.getHoldableObject();

                if (world.getEntityManager().getEntities().remove(player.getHoldableObject())) {
                    ((CrossroadState)handler.getStateManager().getIState(StateManager.GameState.CROSSROAD)).getWorld().getEntityManager().addEntity(
                            tempHoldableEntity
                    );
                }
            }
            ///////////////////////////////////////////////////
            handler.getStateManager().change( StateManager.GameState.CROSSROAD, args );
        }
    }

    public void increaseCropEntityDaysWatered() {
        System.out.println("GameState.increaseCropEntityDaysWatered()");

        if (handler.getTimeManager().getNewDay()) {
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
        if (handler.getTimeManager().getNewDay()) {
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
        /*
        if (handler.getStateManager().getCurrentState() !=
                handler.getStateManager().getIState(StateManager.GameState.GAME)) {
            return;
        }
        */

        ////////////////
        world.render(g);
        ////////////////
    }

    public World getWorld() {
        return world;
    }

    public Object[] getArgs() { return args; }

} // **** end GameState class ****