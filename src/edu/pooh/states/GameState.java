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
            StateManager.change(handler.getGame().getHomeState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToChickenCoop()) ) {
            StateManager.change(handler.getGame().getChickenCoopState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToCowBarn()) ) {
            StateManager.change(handler.getGame().getCowBarnState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToToolShed()) ) {
            StateManager.change(handler.getGame().getToolShedState(), args);
        } else if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointGameToCrossroad()) ) {
            StateManager.change(handler.getGame().getCrossroadState(),args);
        }
    }

    public void increaseCropEntityDaysWatered() {
        if (TimeManager.getNewDay()) {
            Tile[][] tempWorld = handler.getWorld().getTilesViaRGB();

            for (Tile[] tArray : tempWorld) {
                for (Tile t : tArray) {
                    if (t instanceof DirtNormalTile) {
                        DirtNormalTile tempTile = (DirtNormalTile)t;
                        if ( (tempTile.getStaticEntity() != null) &&
                                (tempTile.getStaticEntity() instanceof CropEntity) &&
                                (tempTile.isWatered()) ) {
                            ///////////////////////////////////////////////////////////////
                            ((CropEntity)tempTile.getStaticEntity()).increaseDaysWatered();
                            ///////////////////////////////////////////////////////////////
                        }
                    }
                }
            }

        }
    }

    //TODO: watered and dried version of textures for CropEntity's turnip, potato, tomato, corn (maybe NOT: tile vs entity).
    public void setAllDirtNormalTileWateredToFalse() {
        if (TimeManager.getNewDay()) {
            Tile[][] tempWorld = handler.getWorld().getTilesViaRGB();

            for (Tile[] tArray : tempWorld) {
                for (Tile t : tArray) {
                    if (t instanceof DirtNormalTile) {
                        DirtNormalTile tempTile = (DirtNormalTile)t;

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

} // **** end GameState class ****