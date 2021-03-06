package edu.pooh.worlds;

import edu.pooh.entities.Entity;
import edu.pooh.entities.EntityManager;
import edu.pooh.entities.creatures.*;
import edu.pooh.entities.creatures.live_stocks.Chicken;
import edu.pooh.entities.creatures.live_stocks.Cow;
import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.statics.produce_yields.WildBerries;
import edu.pooh.entities.statics.statics1x1.*;
import edu.pooh.entities.statics.statics2x2.Boulder;
import edu.pooh.entities.statics.statics2x2.ShippingBin;
import edu.pooh.entities.statics.statics2x2.TreeStump;
import edu.pooh.gfx.Assets;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.items.ItemManager;
import edu.pooh.items.crops.tier0.SeedsWild;
import edu.pooh.items.crops.tier0.Shovel;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.states.StateManager;
import edu.pooh.tiles.*;
import edu.pooh.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

public class World
        implements Serializable {

    public enum WorldType {
        GAME, HOME, CHICKEN_COOP, COW_BARN, TOOL_SHED, CROSSROAD, MOUNTAIN, MENU, TRAVELING_FENCE, THE_WEST;
    }

    private transient Handler handler;
    private WorldType worldType;

    private int widthInTiles;   // Width of world, in terms of number of tiles across.
    private int heightInTiles;  // Height of world, in terms of number of tiles down.
    private int playerSpawnX;
    private int playerSpawnY;

    // TILES
    private Tile[][] tilesViaRGB;   // Multi-dimensional array of Tile objects loaded via RGB values.

    // ENTITIES
    private EntityManager entityManager;

    // ITEMS
    private ItemManager itemManager;

    // TRANSFER POINTS (AFTER MAP IS LOADED)
    private Rectangle transferPointGameToHome, transferPointGameToCowBarn, transferPointGameToChickenCoop,
            transferPointGameToToolShed, transferPointGameToCrossroad, transferPointHomeToGame,
            transferPointChickenCoopToGame, transferPointCowBarnToGame, transferPointToolShedToGame,
            transferPointCrossroadToGame, transferPointCrossroadToMountain, transferPointMountainToCrossroad,
            transferPointCrossroadToTheWest, transferPointTheWestToCrossroad;

    public World(Handler handler, WorldType worldType) {
        this.handler = handler;
        this.worldType = worldType;

        entityManager = new EntityManager(handler);
        itemManager = new ItemManager(handler);

        // ******************************************************************************************
        // |+|+|+|+|+|+|+| LOAD TILES and ENTITIES (non-randomly and randomly placed) |+|+|+|+|+|+|+|
        // ******************************************************************************************
        if (worldType == WorldType.GAME) {
            loadTilesViaRGB(Assets.tilesGameViaRGB);
            loadEntitiesPlacedNonRandomlyViaRGB(Assets.entitiesGameViaRGB);
            loadEntities2x2PlacedRandomly(Assets.tilesGameViaRGB, Assets.entitiesGameViaRGB);
            loadEntities1x1PlacedRandomly(Assets.tilesGameViaRGB, Assets.entitiesGameViaRGB);

            entityManager.locatePlayer();   // Sets EntityManager's player variable to player object created from rgb.
        } else if (worldType == WorldType.HOME) {
            loadTilesViaRGB(Assets.tilesHomeViaRGB);
            loadEntitiesPlacedNonRandomlyViaRGB(Assets.entitiesHomeViaRGB);
        } else if (worldType == WorldType.CHICKEN_COOP) {
            loadTilesViaRGB(Assets.tilesChickenCoopViaRGB);
            loadEntitiesPlacedNonRandomlyViaRGB(Assets.entitiesChickenCoopViaRGB);
        } else if (worldType == WorldType.COW_BARN) {
            loadTilesViaRGB(Assets.tilesCowBarnViaRGB);
            loadEntitiesPlacedNonRandomlyViaRGB(Assets.entitiesCowBarnViaRGB);
        } else if (worldType == WorldType.TOOL_SHED) {
            loadTilesViaRGB(Assets.tilesToolShedViaRGB);
            loadEntitiesPlacedNonRandomlyViaRGB(Assets.entitiesToolShedViaRGB);
        } else if (worldType == WorldType.CROSSROAD) {
            loadTilesViaRGB(Assets.tilesCrossroadViaRGB);
            loadEntitiesPlacedNonRandomlyViaRGB(Assets.entitiesCrossroadViaRGB);
        } else if (worldType == WorldType.MOUNTAIN) {
            loadTilesViaRGB(Assets.tilesMountainViaRGB);
            loadEntitiesPlacedNonRandomlyViaRGB(Assets.entitiesMountainViaRGB);
        } else if (worldType == WorldType.THE_WEST) {
            loadTilesViaRGB(Assets.tilesTheWestViaRGB);
            loadEntitiesPlacedNonRandomlyViaRGB(Assets.entitiesTheWestViaRGB);
        }

        // ****************************************************
        // |+|+|+|+|+|+|+| LOAD TRANSFER_POINTS |+|+|+|+|+|+|+|
        // ****************************************************
        if (worldType == WorldType.GAME) {
            transferPointGameToHome = new Rectangle(7 * Tile.TILE_WIDTH, 17 * Tile.TILE_HEIGHT,
                    Tile.TILE_WIDTH, (Tile.TILE_HEIGHT / 2));
            transferPointGameToCowBarn = new Rectangle(19*Tile.TILE_WIDTH, 17*Tile.TILE_HEIGHT,
                    Tile.TILE_WIDTH, (Tile.TILE_HEIGHT / 2));
            transferPointGameToChickenCoop = new Rectangle(28*Tile.TILE_WIDTH, 17*Tile.TILE_HEIGHT,
                    Tile.TILE_WIDTH, (Tile.TILE_HEIGHT / 2));
            transferPointGameToToolShed = new Rectangle(24*Tile.TILE_WIDTH, 25*Tile.TILE_HEIGHT,
                    Tile.TILE_WIDTH, (Tile.TILE_HEIGHT / 2));
            transferPointGameToCrossroad = new Rectangle(-Tile.TILE_WIDTH, 23*Tile.TILE_HEIGHT,
                    Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT);
        } else if (worldType == WorldType.HOME) {
            transferPointHomeToGame = new Rectangle((7 * Tile.TILE_WIDTH),
                    ((10 * Tile.TILE_HEIGHT) + (Tile.TILE_HEIGHT / 2)),
                    (2 * Tile.TILE_WIDTH), (Tile.TILE_HEIGHT / 2));
        } else if (worldType == WorldType.CHICKEN_COOP) {
            transferPointChickenCoopToGame = new Rectangle((7 * Tile.TILE_WIDTH),
                    ((13 * Tile.TILE_HEIGHT) + (Tile.TILE_HEIGHT / 2)),
                    (2 * Tile.TILE_WIDTH), (Tile.TILE_HEIGHT / 2));
        } else if (worldType == WorldType.COW_BARN) {
            transferPointCowBarnToGame = new Rectangle((7 * Tile.TILE_WIDTH),
                    ((22 * Tile.TILE_HEIGHT) + (Tile.TILE_HEIGHT / 2)),
                    (2 * Tile.TILE_WIDTH), (Tile.TILE_HEIGHT / 2));
        } else if (worldType == WorldType.TOOL_SHED) {
            transferPointToolShedToGame = new Rectangle((5 * Tile.TILE_WIDTH),
                    ((10 * Tile.TILE_HEIGHT) + (Tile.TILE_HEIGHT / 2)),
                    (2 * Tile.TILE_WIDTH), (Tile.TILE_HEIGHT / 2));
        } else if (worldType == WorldType.CROSSROAD) {
            transferPointCrossroadToGame = new Rectangle(((15 * Tile.TILE_WIDTH) + (Tile.TILE_WIDTH / 2)),
                    (5 * Tile.TILE_HEIGHT),
                    (Tile.TILE_WIDTH / 2), (4 * Tile.TILE_HEIGHT));
            transferPointCrossroadToMountain = new Rectangle((6 * Tile.TILE_WIDTH),
                    0,
                    (3 * Tile.TILE_WIDTH), (Tile.TILE_HEIGHT / 2));
            transferPointCrossroadToTheWest = new Rectangle(0,
                    (5 * Tile.TILE_HEIGHT),
                    (Tile.TILE_WIDTH / 4), (4 * Tile.TILE_HEIGHT));
        } else if (worldType == WorldType.MOUNTAIN) {
            transferPointMountainToCrossroad = new Rectangle((19 * Tile.TILE_WIDTH),
                    ((45 * Tile.TILE_HEIGHT) + (Tile.TILE_HEIGHT / 2)),
                    (4 * Tile.TILE_WIDTH), (Tile.TILE_HEIGHT / 2));
        } else if (worldType == WorldType.THE_WEST) {
            transferPointTheWestToCrossroad = new Rectangle(((28 * Tile.TILE_WIDTH) + ((3*Tile.TILE_WIDTH) / 4)),
                    (1 * Tile.TILE_HEIGHT),
                    (Tile.TILE_WIDTH / 4), (8 * Tile.TILE_HEIGHT));
        }

    } // **** end World(Handler, String) constructor ****

    public Rectangle getTransferPointGameToHome() {
        return transferPointGameToHome;
    }

    public Rectangle getTransferPointHomeToGame() {
        return transferPointHomeToGame;
    }

    public Rectangle getTransferPointChickenCoopToGame() { return transferPointChickenCoopToGame; }

    public Rectangle getTransferPointCowBarnToGame() { return transferPointCowBarnToGame; }

    public Rectangle getTransferPointToolShedToGame() { return transferPointToolShedToGame; }

    public Rectangle getTransferPointCrossroadToGame() { return transferPointCrossroadToGame; }

    public Rectangle getTransferPointCrossroadToMoutain() { return transferPointCrossroadToMountain; }

    public Rectangle getTransferPointCrossroadToTheWest() { return transferPointCrossroadToTheWest; }

    public Rectangle getTransferPointTheWestToCrossroad() { return transferPointTheWestToCrossroad; }

    public Rectangle getTransferPointMountainToCrossroad() { return transferPointMountainToCrossroad; }

    public void tick() {
        itemManager.tick();
        entityManager.tick();
    }

    public void render(Graphics g) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        // RENDERING EFFICIENCY from youtube's CodeNMore NEW Beginner 2D Game Programming series. //
        ////////////////////////////////////////////////////////////////////////////////////////////
        int xStart = (int)Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int)Math.min(widthInTiles,
                (handler.getGameCamera().getxOffset() + Game.WIDTH_OF_FRAME) / Tile.TILE_WIDTH + 1);
        int yStart = (int)Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int)Math.min(heightInTiles,
                (handler.getGameCamera().getyOffset() + Game.HEIGHT_OF_FRAME) / Tile.TILE_HEIGHT + 1);
        ////////////////////////////////////////////////////////////////////////////////////////////
        // RENDERING EFFICIENCY from youtube's CodeNMore NEW Beginner 2D Game Programming series. //
        ////////////////////////////////////////////////////////////////////////////////////////////

        // RENDER TILES
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                // Multiple to convert from x,y indexes to tile-size.
                getTile(x, y).render(g, (int)(x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (int)(y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }

        // RENDER TRANSFER POINTS
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //TODO: BOOKMARK to toggle renderTransferPoints(Graphics).
        renderTransferPoints(g);
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        // RENDER ITEMS
        itemManager.render(g);

        // RENDER ENTITIES
        entityManager.render(g);

    }

    private void renderTransferPoints(Graphics g) { //AND BEDTILE's bounding box for HomeState
        g.setColor(Color.GREEN);
        if (handler.getStateManager().getCurrentState() ==
                handler.getStateManager().getIState(StateManager.GameState.GAME)) {
            g.fillRect((int)(transferPointGameToHome.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointGameToHome.y - handler.getGameCamera().getyOffset()),
                    transferPointGameToHome.width, transferPointGameToHome.height);
            g.fillRect((int)(transferPointGameToCowBarn.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointGameToCowBarn.y - handler.getGameCamera().getyOffset()),
                    transferPointGameToCowBarn.width, transferPointGameToCowBarn.height);
            g.fillRect((int)(transferPointGameToChickenCoop.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointGameToChickenCoop.y - handler.getGameCamera().getyOffset()),
                    transferPointGameToChickenCoop.width, transferPointGameToChickenCoop.height);
            g.fillRect((int)(transferPointGameToToolShed.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointGameToToolShed.y - handler.getGameCamera().getyOffset()),
                    transferPointGameToToolShed.width, transferPointGameToToolShed.height);
            g.fillRect((int)(transferPointGameToCrossroad.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointGameToCrossroad.y - handler.getGameCamera().getyOffset()),
                    transferPointGameToCrossroad.width, transferPointGameToCrossroad.height);
        } else if (handler.getStateManager().getCurrentState() ==
                handler.getStateManager().getIState(StateManager.GameState.HOME)) {
            g.fillRect((int)(transferPointHomeToGame.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointHomeToGame.y - handler.getGameCamera().getyOffset()),
                    transferPointHomeToGame.width, transferPointHomeToGame.height);
            //g.fillRect((int)((bedTileX * Tile.TILE_WIDTH) - handler.getGameCamera().getxOffset()),
            //        (int)((bedTileY * Tile.TILE_HEIGHT) - handler.getGameCamera().getyOffset()),
            //        Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        } else if (handler.getStateManager().getCurrentState() ==
                handler.getStateManager().getIState(StateManager.GameState.COW_BARN)) {
            g.fillRect((int)(transferPointCowBarnToGame.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointCowBarnToGame.y - handler.getGameCamera().getyOffset()),
                    transferPointCowBarnToGame.width, transferPointCowBarnToGame.height);
        } else if (handler.getStateManager().getCurrentState() ==
                handler.getStateManager().getIState(StateManager.GameState.CHICKEN_COOP)) {
            g.fillRect((int)(transferPointChickenCoopToGame.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointChickenCoopToGame.y - handler.getGameCamera().getyOffset()),
                    transferPointChickenCoopToGame.width, transferPointChickenCoopToGame.height);
        } else if (handler.getStateManager().getCurrentState() ==
                handler.getStateManager().getIState(StateManager.GameState.TOOL_SHED)) {
            g.fillRect((int)(transferPointToolShedToGame.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointToolShedToGame.y - handler.getGameCamera().getyOffset()),
                    transferPointToolShedToGame.width, transferPointToolShedToGame.height);
        } else if (handler.getStateManager().getCurrentState() ==
                handler.getStateManager().getIState(StateManager.GameState.CROSSROAD)) {
            g.fillRect((int)(transferPointCrossroadToGame.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointCrossroadToGame.y - handler.getGameCamera().getyOffset()),
                    transferPointCrossroadToGame.width, transferPointCrossroadToGame.height);
            g.fillRect((int)(transferPointCrossroadToMountain.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointCrossroadToMountain.y - handler.getGameCamera().getyOffset()),
                    transferPointCrossroadToMountain.width, transferPointCrossroadToMountain.height);
            g.fillRect((int)(transferPointCrossroadToTheWest.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointCrossroadToTheWest.y - handler.getGameCamera().getyOffset()),
                    transferPointCrossroadToTheWest.width, transferPointCrossroadToTheWest.height);
        } else if (handler.getStateManager().getCurrentState() ==
                handler.getStateManager().getIState(StateManager.GameState.MOUNTAIN)) {
            g.fillRect((int)(transferPointMountainToCrossroad.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointMountainToCrossroad.y - handler.getGameCamera().getyOffset()),
                    transferPointMountainToCrossroad.width, transferPointMountainToCrossroad.height);
        } else if (handler.getStateManager().getCurrentState() ==
                handler.getStateManager().getIState(StateManager.GameState.THE_WEST)) {
            g.fillRect((int)(transferPointTheWestToCrossroad.x - handler.getGameCamera().getxOffset()),
                    (int)(transferPointTheWestToCrossroad.y - handler.getGameCamera().getyOffset()),
                    transferPointTheWestToCrossroad.width, transferPointTheWestToCrossroad.height);
        }
    }

    public Tile getTile(int x, int y) {
        // This checks if the player is going outside the map's bound, returns a DirtWalkwayTile object as default.
        if (x < 0 || y < 0 || x >= widthInTiles || y >= heightInTiles) {
            return Tile.dirtWalkway;
        }

        return tilesViaRGB[x][y];
    }

    private void loadTilesViaRGB(BufferedImage tilesViaRGB) {
        int[][][] rgbArrayRelativeToMap = Utils.transcribeRGBFromImage(tilesViaRGB);

        widthInTiles = rgbArrayRelativeToMap.length;
        heightInTiles = rgbArrayRelativeToMap[0].length;

        this.tilesViaRGB = new Tile[widthInTiles][heightInTiles];

        translateTileFromRGB(rgbArrayRelativeToMap);
    }

    private int bedTileX;
    private int bedTileY;
    private void translateTileFromRGB(int[][][] rgbArrayRelativeToMap) {
        int[] rgb;
        int red;
        int green;
        int blue;

        for (int xx = 0; xx < widthInTiles; xx++) {
            for (int yy = 0; yy < heightInTiles; yy++) {
                if (tilesViaRGB[xx][yy] == null) {
                    rgb = rgbArrayRelativeToMap[xx][yy];

                    red = rgb[0];
                    green = rgb[1];
                    blue = rgb[2];

                    //////////////////////////////////////////////////////////////////////////
                    //            Tile type determination based on rgb values               //
                    //////////////////////////////////////////////////////////////////////////
                    if (worldType == WorldType.GAME) {
                        if (red == 255 && green == 255 && blue == 255) {        //DirtNormalTile
                            tilesViaRGB[xx][yy] = new DirtNormalTile(xx, yy);
                        } else if (red == 0 && green == 0 && blue == 0) {       //fence
                            tilesViaRGB[xx][yy] = Tile.tiles[1];
                        } else if (red == 136 && green == 0 && blue == 21) {    //dirtWalkway
                            tilesViaRGB[xx][yy] = Tile.tiles[2];
                        } else if (red == 0 && green == 255 && blue == 255) {   //SignPostTile (dirtWalkway)
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.dirtWalkway, xx, yy,
                                    SignPostTile.SignPostType.SHIPPING_BIN);
                        } else if (red == 1 && green == 255 && blue == 255) {   //SignPostTile (dirtNormal)
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.dirtNormal, xx, yy,
                                    SignPostTile.SignPostType.RESOURCE_FODDER);
                        } else if (red == 2 && green == 255 && blue == 255) {   //SignPostTile (dirtNormal)
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.dirtNormal, xx, yy,
                                    SignPostTile.SignPostType.RESOURCE_WOOD);
                        } else if (red == 3 && green == 255 && blue == 255) {   //SignPostTile (dirtNormal)
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.dirtNormal, xx, yy,
                                    SignPostTile.SignPostType.HORSE_STABLE);
                        } else if (red == 163 && green == 73 && blue == 164) {  //home5x4
                            for (int y = 0; y < 4; y++) {
                                for (int x = 0; x < 5; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[100 + (y * 5) + x];
                                }
                            }
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[100];
                        } else if (red == 163 && green == 76 && blue == 164) {  //cowBarn5x5
                            for (int y = 0; y < 5; y++) {
                                for (int x = 0; x < 5; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[120 + (y * 5) + x];
                                }
                            }
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[120];
                        } else if (red == 163 && green == 77 && blue == 164) {  //silos5x6
                            for (int y = 0; y < 6; y++) {
                                for (int x = 0; x < 5; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[145 + (y * 5) + x];
                                }
                            }
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[145];
                        } else if (red == 163 && green == 78 && blue == 164) {  //chickenCoop4x5
                            for (int y = 0; y < 5; y++) {
                                for (int x = 0; x < 4; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[175 + (y * 4) + x];
                                }
                            }
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[175];
                        } else if (red == 163 && green == 79 && blue == 164) {  //toolShed5x5
                            for (int y = 0; y < 5; y++) {
                                for (int x = 0; x < 5; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[195 + (y * 5) + x];
                                }
                            }
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[195];
                        } else if (red == 163 && green == 75 && blue == 164) {  //stable2x3
                            for (int y = 0; y < 3; y++) {
                                for (int x = 0; x < 2; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[220 + (y * 2) + x];
                                }
                            }
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[220];
                        } else if (red == 163 && green == 74 && blue == 164) {  //building2x3
                            for (int y = 0; y < 2; y++) {
                                for (int x = 0; x < 2; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[226 + (y * 2) + x];
                                }
                            }
                            tilesViaRGB[xx + 0][yy + 2] = new WoodStashTile(handler, xx+0, yy+2, Assets.building2x3[2][0]);
                            tilesViaRGB[xx + 1][yy + 2] = new WoodStashTile(handler, xx+1, yy+2, Assets.building2x3[2][1]);
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[226];
                        } /*else if (red == 255 && green == 242 && blue == 0) {   //shippingBin2x2
                            for (int y = 0; y < 2; y++) {
                                for (int x = 0; x < 2; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[232 + (y * 2) + x];
                                }
                            }
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[232];
                        }*/ else if (red == 0 && green == 0 && blue == 255) {     //poolWater2x2
                            for (int y = 0; y < 2; y++) {
                                for (int x = 0; x < 2; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[236 + (y * 2) + x];
                                }
                            }
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[236];
                        } else if (red == 0 && green == 0 && blue == 254) {     //poolWater3x3
                            for (int y = 0; y < 3; y++) {
                                for (int x = 0; x < 3; x++) {
                                    tilesViaRGB[xx + x][yy + y] = Tile.tiles[240 + (y * 3) + x];
                                }
                            }
                            //tilesGameViaRGB[xx][yy] = Tile.tiles[240];
                        } else {
                            tilesViaRGB[xx][yy] = Tile.tiles[2];
                        }
                    }
                    /////////////////////
                    else if (worldType == WorldType.HOME) {
                        if (red == 0 && green == 0 && blue == 0) {              //wall - default is solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.homeStateBackground2);
                            //tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.homeStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.homeStateBackground2.getSubimage((xx * 59),
                                    (yy * 60), 59, 60));
                            //tilesViaRGB[xx][yy].resetTexture(Assets.homeStateBackground.getSubimage((xx * 59),
                            //        (yy * 60), 59, 60));
                        } else if (red == 255 & green == 255 && blue == 255) {  //floor - override solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.homeStateBackground2) {
                                //tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.homeStateBackground) {
                                @Override
                                public boolean isSolid() {
                                    return false;
                                }
                            };
                            tilesViaRGB[xx][yy].setTexture(Assets.homeStateBackground2.getSubimage((xx * 59),
                                    (yy * 60), 59, 60));
                            //tilesViaRGB[xx][yy].resetTexture(Assets.homeStateBackground.getSubimage((xx * 59),
                            //        (yy * 60), 59, 60));
                        } else if (red == 255 && green == 255 && blue == 0) { //bed - BedTile.
                            tilesViaRGB[xx][yy] = new BedTile(handler, Assets.homeStateBackground2);
                            bedTileX = xx;
                            bedTileY = yy;
                            tilesViaRGB[xx][yy].setTexture(Assets.homeStateBackground2.getSubimage((xx * 59),
                                    (yy * 60), 59, 60));
                        }
                    }
                    //////////////////////
                    else if (worldType == WorldType.CHICKEN_COOP) {
                        if (red == 0 && green == 0 && blue == 0) {             //wall - default is solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.chickenCoopStateBackground);
                            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                            // set texture for all tiles except the bottom row (TO BLOCK CREATURES FROM GOING OFF-MAP)!
                            if ( (yy != (heightInTiles-1)) ) {
                                tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage(
                                        (xx * 40), (yy * 40), 40, 40));
                            }
                            // set the bottom tile's texture to a black rectangle equivalent to the bottom half
                            // of the last row of the image.
                            else {
                                tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage(
                                        (1), (((heightInTiles-2) * 40) + 25), 40, 15 ));
                            }
                            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        } else if (red == 255 & green == 255 && blue == 255) {  //floor - override solid.
                            tilesViaRGB[xx][yy] = new DirtNotFarmableTile(xx, yy, Assets.chickenCoopStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        } else if (red == 0 && green == 0 && blue == 255) {     //signPostNotTransparent - solid, special.
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.chickenCoopStateBackground, xx, yy,
                                    SignPostTile.SignPostType.SHIPPING_BIN);
                            tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        } else if (red == 1 && green == 0 && blue == 255) {     //signPostNotTransparent - solid, special.
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.chickenCoopStateBackground, xx, yy,
                                    SignPostTile.SignPostType.RESOURCE_FODDER);
                            tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        } else if (red == 2 && green == 0 && blue == 255) {     //signPostNotTransparent - solid, special.
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.chickenCoopStateBackground, xx, yy,
                                    SignPostTile.SignPostType.CHICKEN_COOP_INCUBATOR);
                            tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        }

                        else if (red == 255 && green == 0 && blue == 0) {       //FodderExecutorTile.
                            tilesViaRGB[xx][yy] = new FodderExecutorTile(handler, xx, yy, Assets.chickenCoopStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        }
                        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        else if (red == 255 && blue == 255) {   //FodderDisplayerTile.
                            tilesViaRGB[xx][yy] = new FodderDisplayerTile(xx, yy,
                                    Assets.chickenCoopStateBackground, green);  //READING green FROM .PNG IMAGE FILE
                            tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        }
                        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        else if (red == 0 && green == 255 && blue == 255) {   //FodderStashTile - solid, special.
                            tilesViaRGB[xx][yy] = new FodderStashTile(handler, xx, yy, Assets.chickenCoopStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        }

                        else if (red == 255 && green == 255 && blue == 0) {     //EggIncubatorTile - solid, special.
                            tilesViaRGB[xx][yy] = new EggIncubatorTile(xx, yy, Assets.chickenCoopStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.chickenCoopStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        }
                    }
                    /////////////////////
                    else if (worldType == WorldType.COW_BARN) {
                        if (red == 0 && green == 0 && blue == 0) {              //wall - default is solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.cowBarnStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.cowBarnStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        } else if (red == 255 & green == 255 && blue == 255) {  //floor - override solid.
                            tilesViaRGB[xx][yy] = new DirtNotFarmableTile(xx, yy, Assets.cowBarnStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.cowBarnStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        } else if (red == 0 && green == 0 && blue == 255) {   //signPostNotTransparent - solid, special.
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.cowBarnStateBackground, xx, yy,
                                    SignPostTile.SignPostType.SHIPPING_BIN);
                            tilesViaRGB[xx][yy].setTexture(Assets.cowBarnStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        } else if (red == 1 && green == 0 && blue == 255) {   //signPostNotTransparent - solid, special.
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.cowBarnStateBackground, xx, yy,
                                    SignPostTile.SignPostType.RESOURCE_FODDER);
                            tilesViaRGB[xx][yy].setTexture(Assets.cowBarnStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        } else if (red == 2 && green == 0 && blue == 255) {   //signPostNotTransparent - solid, special.
                            tilesViaRGB[xx][yy] = new SignPostTile(handler, Assets.cowBarnStateBackground, xx, yy,
                                    SignPostTile.SignPostType.COW_BARN_INCUBATOR);
                            tilesViaRGB[xx][yy].setTexture(Assets.cowBarnStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        }

                        else if (red == 255 && green == 0 && blue == 255) {     //FodderExecutorTile.
                            tilesViaRGB[xx][yy] = new FodderExecutorTile(handler, xx, yy, Assets.cowBarnStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.cowBarnStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        }
                        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        ///////////////////////////////////@@@@@@@@@@@@@@@ //green<=12 BECAUSE THERE IS A POSSIBLE
                        else if (red == 255 && blue == 0 && (green < 13)) {     //FodderDisplayerTile.
                        ///////////////////////////////////@@@@@@@@@@@@@@@ //rgb-COMBINATION w red==255, green==255, blue==0.
                            tilesViaRGB[xx][yy] = new FodderDisplayerTile(xx, yy,
                                    Assets.cowBarnStateBackground, green); //READING green FROM .PNG IMAGE FILE
                            tilesViaRGB[xx][yy].setTexture(Assets.cowBarnStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                            System.out.println("FodderDisplayerTile's green value: " + green);
                        }
                        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        else if (red == 0 && green == 255 && blue == 255) {   //FodderStashTile - solid, special.
                            tilesViaRGB[xx][yy] = new FodderStashTile(handler, xx, yy, Assets.cowBarnStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.cowBarnStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        }

                        //cowIncubatorTile - !!!UNIQUE!!!
                        else if (red == 255 && green == 255 && blue == 0) {
                            tilesViaRGB[xx][yy] = new FodderExecutorTile(handler, xx, yy, Assets.cowBarnStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.cowBarnStateBackground.getSubimage((xx * 40),
                                    (yy * 40), 40, 40));
                        }
                    }
                    /////////////////////
                    else if (worldType == WorldType.TOOL_SHED) {
                        if (red == 0 && green == 0 && blue == 0) {              //wall - default is solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.toolShedStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.toolShedStateBackground.getSubimage((xx * 64),
                                    (yy * 64), 64, 64));
                        } else if (red == 255 & green == 255 && blue == 255) {  //floor - override solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.toolShedStateBackground) {
                                @Override
                                public boolean isSolid() {
                                    return false;
                                }
                            };
                            tilesViaRGB[xx][yy].setTexture(Assets.toolShedStateBackground.getSubimage((xx * 64),
                                    (yy * 64), 64, 64));
                        } else if (red == 0 && green == 0 && blue == 255) {   //doorToCave - solid, special.
                            //TODO: DoorToCaveTile tool shed
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.toolShedStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.toolShedStateBackground.getSubimage((xx * 64),
                                    (yy * 64), 64, 64));
                        }
                    }
                    /////////////////////
                    else if (worldType == WorldType.CROSSROAD) {
                        if (red == 0 && green == 0 && blue == 0) {              //wall - default is solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.crossroadStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.crossroadStateBackground.getSubimage((xx * 56),
                                    (yy * 56), 56, 56));
                        } else if (red == 255 & green == 255 && blue == 255) {  //floor - override solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.crossroadStateBackground) {
                                @Override
                                public boolean isSolid() {
                                    return false;
                                }
                            };
                            tilesViaRGB[xx][yy].setTexture(Assets.crossroadStateBackground.getSubimage((xx * 56),
                                    (yy * 56), 56, 56));
                        }
                    }
                    /////////////////////
                    else if (worldType == WorldType.MOUNTAIN) {
                        if (red == 0 && green == 0 && blue == 0) {              //wall - default is solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(
                                    Assets.mountainStateBackground.getSubimage((xx * 16), (yy * 16), 16, 16)
                            );
                        } else if (red == 255 & green == 255 && blue == 255) {  //floor - override solid.
                            tilesViaRGB[xx][yy] = new DirtNotFarmableTile( xx, yy,
                                    Assets.mountainStateBackground.getSubimage((xx * 16), (yy * 16), 16, 16)
                            );
                        } else if (red == 255 && green == 255 && blue == 0) {   //fence-like, execute - jump into hot spring.
                            tilesViaRGB[xx][yy] = new HotSpringMountainTile( handler, xx, yy,
                                    Assets.mountainStateBackground.getSubimage((xx * 16), (yy * 16), 16,16)
                            );
                        } else if (red == 255 && green == 0 && blue == 255) {
                            tilesViaRGB[xx][yy] = new SolidGenericTile(
                                    Assets.mountainStateBackground.getSubimage((xx * 16), (yy * 16), 16, 16)
                            ) {
                              @Override
                              public boolean isSolid() {
                                  return false;
                              }
                            };
                        } else if (red == 0 && green == 255 && blue == 255) {   //signPostNotTransparent.
                            tilesViaRGB[xx][yy] = new SignPostTile(
                                    handler, Assets.mountainStateBackground.getSubimage((xx * 16), (yy * 16), 16, 16),
                                    xx, yy, SignPostTile.SignPostType.MOUNTAIN_TODO
                            );
                        }
                    }
                    /////////////////////
                    else if (worldType == WorldType.THE_WEST) {
                        if (red == 0 && green == 0 && blue == 0) {              //wall - default is solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.theWestStateBackground);
                            tilesViaRGB[xx][yy].setTexture(Assets.theWestStateBackground.getSubimage((xx * 64),
                                    (yy * 64), 64, 64));
                        } else if (red == 255 & green == 255 && blue == 255) {  //floor - override solid.
                            tilesViaRGB[xx][yy] = new SolidGenericTile(Assets.theWestStateBackground) {
                                @Override
                                public boolean isSolid() {
                                    return false;
                                }
                            };
                            tilesViaRGB[xx][yy].setTexture(Assets.theWestStateBackground.getSubimage((xx * 64),
                                    (yy * 64), 64, 64));
                        }
                    }
                }
            }
        }
    }

    private void loadEntitiesPlacedNonRandomlyViaRGB(BufferedImage entitiesViaRGB) {
        int[][][] rgbArrayRelativeToMap = Utils.transcribeRGBFromImage(entitiesViaRGB);

        translateEntitiesFromRGB(rgbArrayRelativeToMap);
    }

    private void translateEntitiesFromRGB(int[][][] rgbArrayRelativeToMap) {
        int[] rgb;
        int red;
        int green;
        int blue;

        for (int xx = 0; xx < widthInTiles; xx++) {
            for (int yy = 0; yy < heightInTiles; yy++) {
                rgb = rgbArrayRelativeToMap[xx][yy];

                red = rgb[0];
                green = rgb[1];
                blue = rgb[2];

                //////////////////////////////////////////////////////////////////////////
                //       Entity type/position determination based on rgb values         //
                //////////////////////////////////////////////////////////////////////////
                if (worldType == WorldType.GAME) {
                    // STATIC_ENTITY
                    if (red == 0 && green == 0 && blue == 0) {        //Wood
                        if (getTile(xx, yy) instanceof DirtNormalTile) {
                            DirtNormalTile tempTile = (DirtNormalTile) getTile(xx, yy);
                            tempTile.setStaticEntity(new Wood(handler, (float) (xx * Tile.TILE_WIDTH), (float) (yy * Tile.TILE_HEIGHT)));

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 128 && green == 128 && blue == 128) { //Rock (Item drop -> Shovel)
                        if (getTile(xx, yy) instanceof DirtNormalTile) {
                            DirtNormalTile tempTile = (DirtNormalTile) getTile(xx, yy);
                            tempTile.setStaticEntity(new Rock(handler, (float) (xx * Tile.TILE_WIDTH), (float) (yy * Tile.TILE_HEIGHT)) {
                                @Override
                                public void die() {
                                    // The overridden die() method for this special Rock instance... drops loot: Shovel.
                                    Shovel.getUniqueInstance(handler).setPosition((int) x, (int) y);
                                    handler.getWorld().getItemManager().addItem(Shovel.getUniqueInstance(handler));

                                    // The die() method from Rock class that isn't overridden... to remove Rock instance.
                                    for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                                        for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
                                            if (handler.getWorld().getTile(xx, yy) instanceof DirtNormalTile) {
                                                if (((DirtNormalTile) handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {
                                                    ((DirtNormalTile) handler.getWorld().getTile(xx, yy)).setStaticEntity(null);
                                                }
                                            } else if (handler.getWorld().getTile(xx, yy) instanceof DirtNotFarmableTile) {
                                                if (((DirtNotFarmableTile)handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {
                                                    ((DirtNotFarmableTile)handler.getWorld().getTile(xx, yy)).setStaticEntity(null);
                                                }
                                            }
                                        }
                                    }

                                    setActive(false);
                                }
                            });

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 0 && green == 255 && blue == 1) { //Bush (Item drop -> SeedsWild w SeedType.CANNABIS_WILD)
                        if (getTile(xx, yy) instanceof DirtNormalTile) {
                            DirtNormalTile tempTile = (DirtNormalTile) getTile(xx, yy);
                            tempTile.setStaticEntity(new Bush(handler, (float) (xx * Tile.TILE_WIDTH), (float) (yy * Tile.TILE_HEIGHT)) {
                                @Override
                                public void die() {
                                    // The overridden die() method for this special Bush instance... drops loot: SeedsWild.
                                    SeedsWild temp = new SeedsWild(handler);
                                    temp.setPosition((int) x, (int) y);
                                    handler.getWorld().getItemManager().addItem(temp);

                                    // The die() method from Bush class that isn't overridden... to remove Bush instance.
                                    for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                                        for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
                                            if (handler.getWorld().getTile(xx, yy) instanceof DirtNormalTile) {
                                                if (((DirtNormalTile) handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {
                                                    ((DirtNormalTile) handler.getWorld().getTile(xx, yy)).setStaticEntity(null);
                                                }
                                            }
                                        }
                                    }

                                    setActive(false);
                                }
                            });

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 0 && green == 255 && blue == 2) { //Bush (Item drop -> SeedsWild w SeedType.TURNIP)
                        if (getTile(xx, yy) instanceof DirtNormalTile) {
                            DirtNormalTile tempTile = (DirtNormalTile) getTile(xx, yy);
                            tempTile.setStaticEntity(new Bush(handler, (float) (xx * Tile.TILE_WIDTH), (float) (yy * Tile.TILE_HEIGHT)) {
                                @Override
                                public void die() {
                                    // The overridden die() method for this special Bush instance... drops loot: SeedsWild.
                                    SeedsWild temp = new SeedsWild(handler);
                                    temp.setPosition((int) x, (int) y);
                                    temp.setName("Turnip Seeds");
                                    temp.setSeedType(SeedsWild.SeedType.TURNIP);
                                    handler.getWorld().getItemManager().addItem(temp);

                                    // The die() method from Bush class that isn't overridden... to remove Bush instance.
                                    for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                                        for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
                                            if (handler.getWorld().getTile(xx, yy) instanceof DirtNormalTile) {
                                                if (((DirtNormalTile) handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {
                                                    ((DirtNormalTile) handler.getWorld().getTile(xx, yy)).setStaticEntity(null);
                                                }
                                            }
                                        }
                                    }

                                    setActive(false);
                                }
                            });

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 0 && green == 255 && blue == 3) { //Bush (Item drop -> SeedsWild w SeedType.CORN)
                        if (getTile(xx, yy) instanceof DirtNormalTile) {
                            DirtNormalTile tempTile = (DirtNormalTile) getTile(xx, yy);
                            tempTile.setStaticEntity(new Bush(handler, (float) (xx * Tile.TILE_WIDTH), (float) (yy * Tile.TILE_HEIGHT)) {
                                @Override
                                public void die() {
                                    // The overridden die() method for this special Bush instance... drops loot: SeedsWild.
                                    SeedsWild temp = new SeedsWild(handler);
                                    temp.setPosition((int) x, (int) y);
                                    temp.setName("Corn Seeds");
                                    temp.setSeedType(SeedsWild.SeedType.CORN);
                                    handler.getWorld().getItemManager().addItem(temp);

                                    // The die() method from Bush class that isn't overridden... to remove Bush instance.
                                    for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                                        for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
                                            if (handler.getWorld().getTile(xx, yy) instanceof DirtNormalTile) {
                                                if (((DirtNormalTile) handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {
                                                    ((DirtNormalTile) handler.getWorld().getTile(xx, yy)).setStaticEntity(null);
                                                }
                                            }
                                        }
                                    }

                                    setActive(false);
                                }
                            });

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 255 && green == 255 && blue == 0) {   //ShippingBin
                        ShippingBin shippingBin = new ShippingBin(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(shippingBin);
                    } else if (red == 128 && green == 64 && blue == 0) { //Golum
                        Golum golum = new Golum(handler, (xx *Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(golum);
                    } else if (red == 0 && green == 255 && blue == 255) { //DeadCow
                        DeadCow deadCow = new DeadCow(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(deadCow);
                    } else if (red == 0 && green == 128 && blue == 128) { //SpikeTrap
                        SpikeTrap spikeTrap = new SpikeTrap(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(spikeTrap);
                    }
                    // CREATURE
                    else if (red == 255 && green == 0 && blue == 0) { //Player
                        Player player = new Player(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(player);
                        playerSpawnX = xx;
                        playerSpawnY = yy;
                    } else if (red == 0 && green == 0 & blue == 255) {  //XDog
                        XDog xDog = new XDog(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(xDog);
                    } else if (red == 255 && green == 0 && blue == 255) {   //TravelingFence
                        TravelingFence travelingFence = new TravelingFence(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(travelingFence);
                    }
                }
                /////////////////////////////////////////////////
                else if (worldType == WorldType.HOME) {
                    if (red == 255 && green == 0 && blue == 0) {    //Player
                        playerSpawnX = xx;
                        playerSpawnY = yy;
                    }
                }
                /////////////////////////////////////////////////
                else if (worldType == WorldType.CHICKEN_COOP) {
                    if (red == 255 && green == 0 && blue == 0) {    //Player
                        playerSpawnX = xx;
                        playerSpawnY = yy;
                    } else if (red == 0 && green == 255 && blue == 0) {   //ShippingBin
                        ShippingBin shippingBin = new ShippingBin(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        ((DirtNotFarmableTile)getTile(xx, yy)).setStaticEntity( shippingBin );

                        entityManager.addEntity(shippingBin);
                    } else if (red == 1 && green == 255 && blue == 0) { //ShippingBin (axillary)
                        ShippingBin tempShippingBin = null;

                        for (Entity e : entityManager.getEntities()) {
                            if (e instanceof ShippingBin) {
                                tempShippingBin = (ShippingBin)e;
                            }
                        }

                        if (tempShippingBin != null) {
                            ((DirtNotFarmableTile)getTile(xx, yy)).setStaticEntity(tempShippingBin);
                        }
                    } else if (red == 255 && green == 255 && blue == 0) { //Pikachu
                        Pikachu pikachu = new Pikachu(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(pikachu);
                    } else if (red == 2 && green == 255 && blue == 0) { //Dog
                        Dog dog = new Dog(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(dog);
                    } else if (red == 0 && green == 0 && blue == 255) { //Chicken (ChickenState.ADULT_EGG_LAYING)
                        Chicken chicken = new Chicken(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT),
                                Chicken.ChickenState.ADULT_EGG_LAYING);
                        chicken.setDaysInstantiated(7);
                        entityManager.addEntity(chicken);
                    }
                }
                /////////////////////////////////////////////////
                else if (worldType == WorldType.COW_BARN) {
                    if (red == 255 && green == 0 && blue == 0) {    //Player
                        playerSpawnX = xx;
                        playerSpawnY = yy;
                    } else if (red == 0 && green == 255 && blue == 0) {   //ShippingBin
                        ShippingBin shippingBin = new ShippingBin(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        ((DirtNotFarmableTile)getTile(xx, yy)).setStaticEntity( shippingBin );

                        entityManager.addEntity(shippingBin);
                    } else if (red == 1 && green == 255 && blue == 0) { //ShippingBin (axillary)
                        ShippingBin tempShippingBin = null;

                        for (Entity e : entityManager.getEntities()) {
                            if (e instanceof ShippingBin) {
                                tempShippingBin = (ShippingBin)e;
                            }
                        }

                        if (tempShippingBin != null) {
                            ((DirtNotFarmableTile)getTile(xx, yy)).setStaticEntity(tempShippingBin);
                        }
                    }
                    //////////////////////////////////////////////////////////////////////////////////
                    else if (red == 0 && green == 3 && blue == 255) { //Cow (CowState.BABY)
                        Cow cow = new Cow(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT),
                                Cow.CowState.BABY, green);
                        cow.setDaysInstantiated(0);
                        cow.setAffectionScore(0);
                        entityManager.addEntity(cow);
                    } else if (red == 1 && green == 4 && blue == 255) { //Cow (CowState.CALF)
                        Cow cow = new Cow(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT),
                                Cow.CowState.CALF, green);
                        cow.setDaysInstantiated(14);
                        cow.setAffectionScore(0);
                        entityManager.addEntity(cow);
                    } else if (red == 2 && green == 0 && blue == 255) { //Cow (CowState.ADULT_1)
                        Cow cow = new Cow(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT),
                                Cow.CowState.ADULT_1, green);
                        cow.setDaysInstantiated(35);
                        cow.setAffectionScore(0);
                        entityManager.addEntity(cow);
                    } else if (red == 2 && green == 1 && blue == 255) { //Cow (CowState.ADULT_2)
                        Cow cow = new Cow(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT),
                                Cow.CowState.ADULT_2, green);
                        cow.setDaysInstantiated(35);
                        cow.setAffectionScore(96);
                        entityManager.addEntity(cow);
                    } else if (red == 2 && green == 2 && blue == 255) { //Cow (CowState.ADULT_3)
                        Cow cow = new Cow(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT),
                                Cow.CowState.ADULT_3, green);
                        cow.setDaysInstantiated(35);
                        cow.setAffectionScore(192);
                        entityManager.addEntity(cow);
                    } else if (red == 3 && green == 12 && blue == 255) { //Cow (CowState.PREGNANT)
                        Cow cow = new Cow(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT),
                                Cow.CowState.PREGNANT, green);
                        cow.setDaysInstantiated(35);
                        cow.setAffectionScore(192);
                        handler.getResourceManager().increaseCowCounter(1);
                        cow.setDaysImpregnanted(19);
                        cow.increaseAffectionScore(10);
                        entityManager.addEntity(cow);
                    }
                    //////////////////////////////////////////////////////////////////////////////////
                    else if (red == 0 && green == 255 && blue == 255) { //Horse
                        Horse horse = new Horse(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(horse);
                    } else if (red == 128 && green == 128 && blue == 128) { //Wolf
                        Wolf wolf = new Wolf(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(wolf);
                    } else if (red == 255 && green == 0 && blue == 255) { //Jack
                        Jack jack = new Jack(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(jack);
                    } else if (red == 33 && green == 33 && blue == 33) { //Molly
                        Molly molly = new Molly(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(molly);
                    }
                }
                /////////////////////////////////////////////////
                else if (worldType == WorldType.TOOL_SHED) {
                    if (red == 255 && green == 0 && blue == 0) {    //Player
                        playerSpawnX = xx;
                        playerSpawnY = yy;
                    }
                }
                /////////////////////////////////////////////////
                else if (worldType == WorldType.CROSSROAD) {
                    if (red == 255 && green == 0 && blue == 0) {    //Player
                        playerSpawnX = xx;
                        playerSpawnY = yy;
                    }
                }
                /////////////////////////////////////////////////
                else if (worldType == WorldType.MOUNTAIN) {
                    if (red == 255 && green == 0 && blue == 0) {    //Player
                        playerSpawnX = xx;
                        playerSpawnY = yy;
                    } else if (red == 0 && green == 255 && blue == 0) {    //Bush
                        if (getTile(xx, yy) instanceof DirtNotFarmableTile) {
                            DirtNotFarmableTile tempTile = (DirtNotFarmableTile)getTile(xx, yy);
                            tempTile.setStaticEntity(
                                    new Bush(handler, (float) (xx * Tile.TILE_WIDTH), (float) (yy * Tile.TILE_HEIGHT))
                            );

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 255 && green == 1 && blue == 1) { //Pikachu
                        Pikachu pikachu = new Pikachu(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT));
                        entityManager.addEntity(pikachu);
                    } else if (red == 0 && green == 0 && blue == 255) {    //WildBerries
                        if (getTile(xx, yy) instanceof DirtNotFarmableTile) {
                            DirtNotFarmableTile tempTile = (DirtNotFarmableTile)getTile(xx, yy);
                            tempTile.setStaticEntity(
                                    new WildBerries(handler, (float) (xx * Tile.TILE_WIDTH), (float) (yy * Tile.TILE_HEIGHT))
                            );

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 128 && green == 128 && blue == 128) {    //Rock
                        if (getTile(xx, yy) instanceof DirtNotFarmableTile) {
                            DirtNotFarmableTile tempTile = (DirtNotFarmableTile)getTile(xx, yy);
                            tempTile.setStaticEntity(
                                    new RockMountain(handler, (float) (xx * Tile.TILE_WIDTH), (float) (yy * Tile.TILE_HEIGHT))
                            );

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 0 && green == 255 && blue == 255) {    //Flower (flowerType: YELLOW)
                        if (getTile(xx, yy) instanceof DirtNotFarmableTile) {
                            DirtNotFarmableTile tempTile = (DirtNotFarmableTile)getTile(xx, yy);
                            tempTile.setStaticEntity(
                                    new Flower(handler, (float) (xx * Tile.TILE_WIDTH),
                                            (float) (yy * Tile.TILE_HEIGHT), Flower.FlowerType.YELLOW)
                            );

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 255 && green == 0 && blue == 255) {    //Flower (flowerType: PURPLE)
                        if (getTile(xx, yy) instanceof DirtNotFarmableTile) {
                            DirtNotFarmableTile tempTile = (DirtNotFarmableTile)getTile(xx, yy);
                            tempTile.setStaticEntity(
                                    new Flower(handler, (float) (xx * Tile.TILE_WIDTH),
                                            (float) (yy * Tile.TILE_HEIGHT), Flower.FlowerType.PURPLE)
                            );

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 255 && green == 255 && blue == 0) {    //TreeStump (2x2)
                        if (getTile(xx, yy) instanceof DirtNotFarmableTile) {
                            DirtNotFarmableTile tempTile = (DirtNotFarmableTile)getTile(xx, yy);

                            TreeStump treeStump = new TreeStump(handler, (float) (xx * Tile.TILE_WIDTH),
                                    (float) (yy * Tile.TILE_HEIGHT));

                            tempTile.setStaticEntity(
                                    treeStump
                            );

                            ((DirtNotFarmableTile)getTile(xx+1, yy)).setStaticEntity(treeStump);
                            ((DirtNotFarmableTile)getTile(xx, yy+1)).setStaticEntity(treeStump);
                            ((DirtNotFarmableTile)getTile(xx+1, yy+1)).setStaticEntity(treeStump);

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    } else if (red == 0 && green == 0 && blue == 0) {    //Boulder (2x2)
                        if (getTile(xx, yy) instanceof DirtNotFarmableTile) {
                            DirtNotFarmableTile tempTile = (DirtNotFarmableTile)getTile(xx, yy);

                            Boulder boulder = new Boulder(handler, (float) (xx * Tile.TILE_WIDTH),
                                    (float) (yy * Tile.TILE_HEIGHT));

                            tempTile.setStaticEntity(
                                    boulder
                            );

                            ((DirtNotFarmableTile)getTile(xx+1, yy)).setStaticEntity(boulder);
                            ((DirtNotFarmableTile)getTile(xx, yy+1)).setStaticEntity(boulder);
                            ((DirtNotFarmableTile)getTile(xx+1, yy+1)).setStaticEntity(boulder);

                            entityManager.addEntity(tempTile.getStaticEntity());
                        }
                    }
                }
                /////////////////////////////////////////////////
                else if (worldType == WorldType.THE_WEST) {
                    if (red == 255 && green == 0 && blue == 0) {    //Player
                        playerSpawnX = xx;
                        playerSpawnY = yy;
                    }
                }
            }
        }
    }

    private void loadEntities2x2PlacedRandomly(BufferedImage tilesViaRGB, BufferedImage entitiesViaRGB) {
        int countTreeStump = 10;
        int countBoulder = 10;
        int x = 0;
        int y = 0;
        Random randX = new Random();
        Random randY = new Random();
        TreeStump tempTreeStump = null;
        Boulder tempBoulder = null;
        boolean[][] availablePosition = determineAvailablePosition(tilesViaRGB, entitiesViaRGB);

        if (availablePosition[2][16] && availablePosition[2+1][16] &&
                availablePosition[2][16+1] && availablePosition[2+1][16+1]) {
            if (getTile(2, 16) instanceof DirtNormalTile) {
                tempTreeStump = new TreeStump(handler, (2) * Tile.TILE_WIDTH, (16) * Tile.TILE_HEIGHT);

                ((DirtNormalTile) getTile(2, 16)).setStaticEntity(tempTreeStump);
                ((DirtNormalTile) getTile(3, 16)).setStaticEntity(tempTreeStump);
                ((DirtNormalTile) getTile(2, 17)).setStaticEntity(tempTreeStump);
                ((DirtNormalTile) getTile(3, 17)).setStaticEntity(tempTreeStump);
                entityManager.addEntity(tempTreeStump);
            }
        }

        while (countTreeStump > 0) {
            x = randX.nextInt(widthInTiles);
            y = randY.nextInt(heightInTiles);

            if (availablePosition[x][y] && availablePosition[x+1][y] &&
                    availablePosition[x][y+1] && availablePosition[x+1][y+1]) {
                if (getTile(x, y) instanceof DirtNormalTile && getTile(x+1, y) instanceof DirtNormalTile &&
                getTile(x, y+1) instanceof DirtNormalTile && getTile(x+1, y+1) instanceof DirtNormalTile) {
                    DirtNormalTile tempTile0 = (DirtNormalTile) (getTile(x, y));
                    DirtNormalTile tempTile1 = (DirtNormalTile) (getTile(x + 1, y));
                    DirtNormalTile tempTile2 = (DirtNormalTile) (getTile(x, y + 1));
                    DirtNormalTile tempTile3 = (DirtNormalTile) (getTile(x + 1, y + 1));

                    if (tempTile0.getStaticEntity() == null && tempTile1.getStaticEntity() == null &&
                            tempTile2.getStaticEntity() == null && tempTile3.getStaticEntity() == null) {
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                        tempTreeStump = new TreeStump(handler, (float) (x * Tile.TILE_WIDTH),
                                (float) (y * Tile.TILE_HEIGHT));
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                        tempTile0.setStaticEntity(tempTreeStump);
                        tempTile1.setStaticEntity(tempTreeStump);
                        tempTile2.setStaticEntity(tempTreeStump);
                        tempTile3.setStaticEntity(tempTreeStump);

                        entityManager.addEntity(tempTile0.getStaticEntity());
                        countTreeStump--;
                        availablePosition[x][y] = false;
                        availablePosition[x + 1][y] = false;
                        availablePosition[x][y + 1] = false;
                        availablePosition[x + 1][y + 1] = false;
                    }
                }
            }
        }

        while (countBoulder > 0) {
            x = randX.nextInt(widthInTiles);
            y = randY.nextInt(heightInTiles);

            if (availablePosition[x][y] && availablePosition[x+1][y] &&
                    availablePosition[x][y+1] && availablePosition[x+1][y+1]) {
                if (getTile(x, y) instanceof DirtNormalTile && getTile(x+1, y) instanceof DirtNormalTile &&
                        getTile(x, y+1) instanceof DirtNormalTile && getTile(x+1, y+1) instanceof DirtNormalTile) {
                    DirtNormalTile tempTile0 = (DirtNormalTile) (getTile(x, y));
                    DirtNormalTile tempTile1 = (DirtNormalTile) (getTile(x + 1, y));
                    DirtNormalTile tempTile2 = (DirtNormalTile) (getTile(x, y + 1));
                    DirtNormalTile tempTile3 = (DirtNormalTile) (getTile(x + 1, y + 1));

                    if (tempTile0.getStaticEntity() == null && tempTile1.getStaticEntity() == null &&
                            tempTile2.getStaticEntity() == null && tempTile3.getStaticEntity() == null) {
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                        tempBoulder = new Boulder(handler, (float) (x * Tile.TILE_WIDTH),
                                (float) (y * Tile.TILE_HEIGHT));
                        ///////////////////////////////////////////////////////////////////////////////////////////////
                        tempTile0.setStaticEntity(tempBoulder);
                        tempTile1.setStaticEntity(tempBoulder);
                        tempTile2.setStaticEntity(tempBoulder);
                        tempTile3.setStaticEntity(tempBoulder);

                        entityManager.addEntity(tempTile0.getStaticEntity());
                        countBoulder--;
                        availablePosition[x][y] = false;
                        availablePosition[x + 1][y] = false;
                        availablePosition[x][y + 1] = false;
                        availablePosition[x + 1][y + 1] = false;
                    }
                }
            }
        }
    }

    private void loadEntities1x1PlacedRandomly(BufferedImage tilesViaRGB, BufferedImage entitiesViaRGB) {
        int countRock = 30;
        int countBush = 30;
        int x;
        int y;
        Random randX = new Random();
        Random randY = new Random();

        boolean[][] availablePosition = determineAvailablePosition(tilesViaRGB, entitiesViaRGB);

        while (countRock > 0) {
            x = randX.nextInt(widthInTiles);
            y = randY.nextInt(heightInTiles);

            if (availablePosition[x][y]) {
                if (getTile(x, y) instanceof DirtNormalTile) {
                    if (((DirtNormalTile) getTile(x, y)).getStaticEntity() == null) {
                        DirtNormalTile tempTile = (DirtNormalTile) (getTile(x, y));
                        ///////////////////////////////////////////////////////////////////////////////////////////
                        tempTile.setStaticEntity(new Rock(handler, (float) (x * Tile.TILE_WIDTH), (float) (y * Tile.TILE_HEIGHT)));
                        ///////////////////////////////////////////////////////////////////////////////////////////
                        entityManager.addEntity(tempTile.getStaticEntity());
                        countRock--;
                        availablePosition[x][y] = false;
                    }
                }
            }
        }

        while (countBush > 0) {
            x = randX.nextInt(widthInTiles);
            y = randY.nextInt(heightInTiles);

            if (availablePosition[x][y]) {
                if (getTile(x, y) instanceof DirtNormalTile) {
                    if (((DirtNormalTile) getTile(x, y)).getStaticEntity() == null) {
                        DirtNormalTile tempTile = (DirtNormalTile) (getTile(x, y));
                        /////////////////////////////////////////////////////////////////////////////////////////////
                        tempTile.setStaticEntity(new Bush(handler, (float) (x * Tile.TILE_WIDTH), (float) (y * Tile.TILE_HEIGHT)));
                        /////////////////////////////////////////////////////////////////////////////////////////////
                        entityManager.addEntity(tempTile.getStaticEntity());
                        countBush--;
                        availablePosition[x][y] = false;
                    }
                }
            }
        }
    }

    private boolean[][] determineAvailablePosition(BufferedImage tilesViaRGB, BufferedImage entitiesViaRGB) {
        boolean[][] availablePosition = new boolean[widthInTiles][heightInTiles];

        int[][][] rgbArrayTiles = Utils.transcribeRGBFromImage(tilesViaRGB);
        int[][][] rgbArrayEntities = Utils.transcribeRGBFromImage(entitiesViaRGB);
        int[] rgbTempTiles = new int[3];
        int[] rgbTempEntities = new int[3];

        for (int xx = 0; xx < widthInTiles; xx++) {
            for (int yy = 0; yy < heightInTiles; yy++) {

                // get the int[] rgb for both tiles and entities loaded from images.
                for (int rgb = 0; rgb < 3; rgb++) {
                    rgbTempTiles[rgb] = rgbArrayTiles[xx][yy][rgb];
                    rgbTempEntities[rgb] = rgbArrayEntities[xx][yy][rgb];
                }

                // assign boolean[][] availablePosition elements based on tiles image having a DirtNormalTile
                // && entities image NOT having an entity in that tile position.
                availablePosition[xx][yy] = (
                        rgbTempTiles[0] == 255 && rgbTempTiles[1] == 255 && rgbTempTiles[2] == 255 &&
                  rgbTempEntities[0] == 255 && rgbTempEntities[1] == 255 && rgbTempEntities[2] == 255
                );

            }
        }

        return availablePosition;
    }

    // GETTERS & SETTERS

    public WorldType getWorldType() { return worldType; }

    public int getPlayerSpawnX() {
        return playerSpawnX;
    }

    public void setPlayerSpawnX(int playerSpawnX) {
        this.playerSpawnX = playerSpawnX;
    }

    public int getPlayerSpawnY() {
        return playerSpawnY;
    }

    public void setPlayerSpawnY(int playerSpawnY) {
        this.playerSpawnY = playerSpawnY;
    }

    public Rectangle getTransferPointGameToCowBarn() {
        return transferPointGameToCowBarn;
    }

    public Rectangle getTransferPointGameToToolShed() {
        return transferPointGameToToolShed;
    }

    public Rectangle getTransferPointGameToChickenCoop() {
        return transferPointGameToChickenCoop;
    }

    public Rectangle getTransferPointGameToCrossroad() {
        return transferPointGameToCrossroad;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Tile[][] getTilesViaRGB() { return tilesViaRGB; }

    public void setTilesViaRGB(Tile[][] tilesViaRGB) {
        this.tilesViaRGB = tilesViaRGB;
    }

    public EntityManager getEntityManager() { return entityManager; }

    public void setEntityManager(EntityManager entityManager) { this.entityManager = entityManager; }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public int getWidthInTiles() {
        return widthInTiles;
    }

    public int getHeightInTiles() {
        return heightInTiles;
    }

} // **** end World class ****

// An implementation for loading the collection of Tile objects,
// referred to by the player when checking for valid moves, this
// version loads a multi-dimensional array of Tile based on a
// text file containing int values representing the tile's
// final int id.
/*
    private int spawnX; //NEED map to load before can be used.
    private int spawnY; //NEED map to load before can be used.

    private Tile[][] tiles;         // Multi-dimensional array of Tile objects loaded via int values.

    public World(Handler handler, String path) {
        //giving a random hardcoded coordinate during Player instantiation BEFORE loadWorld(String)
        // AFTER loadWorld(String) the variables spawnX and spawnY are initialized from the text file.

        // Initializes tiles (multi-dimensional int array), and 4 instance variables.
        loadWorld(path);

        entityManager.locatePlayer().setPosition(spawnX, spawnY);
    }

    private void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");   // Any amount of white space is a separator.

        widthInTiles = Utils.parseInt(tokens[0]);
        heightInTiles = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new Tile[widthInTiles][heightInTiles];

        for (int y = 0; y < heightInTiles; y++) {
            for (int x = 0; x < widthInTiles; x++) {
                // If it's suppose to be a dirtNormalTile... instantiate new non-static Tile object.
                if (Utils.parseInt( tokens[x + (y * widthInTiles) + 4] ) == 0) {
                    tiles[x][y] = new DirtNormalTile(x, y);
                } else {    // use Tile class's static Tile[] array's static Tile object.
                    tiles[x][y] = Tile.tiles[ Utils.parseInt( tokens[x + (y * widthInTiles) + 4] ) ];
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        // This checks if the player is going outside the map's bound, returns a DirtWalkwayTile object as default.
        if (x < 0 || y < 0 || x >= widthInTiles || y >= heightInTiles) {
            return Tile.dirtWalkway;
        }

        return tiles[x][y];
    }

    // GETTERS & SETTERS

    public int getSpawnX() { return spawnX; }

    public int getSpawnY() { return spawnY; }

    public Tile[][] getTiles() { return tiles; }
 */

