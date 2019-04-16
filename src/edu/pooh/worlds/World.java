package edu.pooh.worlds;

import edu.pooh.entities.EntityManager;
import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.statics.Bush;
import edu.pooh.entities.statics.Rock;
import edu.pooh.entities.statics.Wood;
import edu.pooh.gfx.Assets;
import edu.pooh.items.ItemManager;
import edu.pooh.items.tier0.SeedsWild;
import edu.pooh.items.tier0.Shovel;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;
import edu.pooh.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class World {

    private Handler handler;

    private int widthInTiles;   // Width of world, in terms of number of tiles across.
    private int heightInTiles;  // Height of world, in terms of number of tiles down.
    private int spawnX; //NEED map to load before can be used.
    private int spawnY; //NEED map to load before can be used.

    private Tile[][] tiles;         // Multi-dimensional array of Tile objects loaded via int values.
    private Tile[][] tilesViaRGB;   // Multi-dimensional array of Tile objects loaded via RGB values.

    // ENTITIES
    private EntityManager entityManager;

    // ITEMS
    private ItemManager itemManager;

    public World(Handler handler, String path) {
        this.handler = handler;
        //giving a random hardcoded coordinate during Player instantiation BEFORE loadWorld(String)
        // AFTER loadWorld(String) the variables spawnX and spawnY are initialized from the text file.
        entityManager = new EntityManager(handler, new Player(handler, 100, 100));
        itemManager = new ItemManager(handler);
        entityManager.addEntity(new Bush(handler, 320, 1150));
        entityManager.addEntity(new Bush(handler, 320, 1250){
            @Override
            public void die() {
                SeedsWild temp = new SeedsWild(handler);
                temp.setPosition((int)x, (int)y);
                handler.getWorld().getItemManager().addItem( temp );
            }
        });
        entityManager.addEntity(new Bush(handler, 320, 1350) {
            @Override
            public void die() {
                SeedsWild temp = new SeedsWild(handler);
                temp.setPosition((int)x, (int)y);
                handler.getWorld().getItemManager().addItem( temp );
            }
        });
        entityManager.addEntity(new Rock(handler, 192, 1150));
        entityManager.addEntity(new Rock(handler, 192, 1250) {
            @Override
            public void die() {
                Shovel.getUniqueInstance(handler).setPosition((int)x, (int)y);
                handler.getWorld().getItemManager().addItem( Shovel.getUniqueInstance(handler) );
            }
        });
        entityManager.addEntity(new Rock(handler, 192, 1350));
        entityManager.addEntity(new Wood(handler, 64, 1150));
        entityManager.addEntity(new Wood(handler, 64, 1250));
        entityManager.addEntity(new Wood(handler, 64, 1350));



//        loadWorld(path);    // Initializes tiles (multi-dimensional int array), and 4 instance variables.
        loadWorldViaRGB(Assets.tilesViaRGB);
        loadEntitiesPlacedNonRandomlyViaRGB(Assets.entitiesViaRGB);
        loadEntitiesPlacedRandomly(Assets.tilesViaRGB, Assets.entitiesViaRGB);


        entityManager.getPlayer().setX(spawnX * Tile.TILE_WIDTH);   //convert number of tiles to pixels.
        entityManager.getPlayer().setY(spawnY * Tile.TILE_HEIGHT);  //convert number of tiles to pixels.
    } // **** end World(Handler, String) constructor ****

    public void tick() {
        itemManager.tick();
        entityManager.tick();
    }

    public void render(Graphics g) {
        // RENDER TILES
        ////////////////////////////////////////////////////////////////////////////////////////////
        // RENDERING EFFICIENCY from youtube's CodeNMore NEW Beginner 2D Game Programming series. //
        ////////////////////////////////////////////////////////////////////////////////////////////
        int xStart = (int)Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int)Math.min(widthInTiles,
                (handler.getGameCamera().getxOffset() + Game.WIDTH_OF_FRAME) / Tile.TILE_WIDTH + 1);
        int yStart = (int)Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int)Math.min(widthInTiles,
                (handler.getGameCamera().getyOffset() + Game.HEIGHT_OF_FRAME) / Tile.TILE_HEIGHT + 1);
        ////////////////////////////////////////////////////////////////////////////////////////////
        // RENDERING EFFICIENCY from youtube's CodeNMore NEW Beginner 2D Game Programming series. //
        ////////////////////////////////////////////////////////////////////////////////////////////

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x, y).render(g, (int)(x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (int)(y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset())); // Multiple to convert from
                                                                                          // x,y indexes to tile-size.
            }
        }

        // RENDER ITEMS
        itemManager.render(g);

        // RENDER ENTITIES
        entityManager.render(g);
    }

    public Tile getTile(int x, int y) {
        // This checks if the player is going outside the map's bound, returns a DirtWalkwayTile object as default.
        if (x < 0 || y < 0 || x >= widthInTiles || y >= heightInTiles) {
            return Tile.dirtWalkway;
        }



//        return tiles[x][y];
        return tilesViaRGB[x][y];


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

    private void loadWorldViaRGB(BufferedImage tilesViaRGB) {
        int[][][] rgbArrayRelativeToMap = Utils.transcribeRGBFromImage(tilesViaRGB);
        this.tilesViaRGB = translateTileFromRGB(rgbArrayRelativeToMap);

        spawnX = 7;
        spawnY = 18;
    }

    private Tile[][] translateTileFromRGB(int[][][] rgbArrayRelativeToMap) {
        widthInTiles = rgbArrayRelativeToMap.length;
        heightInTiles = rgbArrayRelativeToMap[0].length;

        Tile[][] tilesViaRGB = new Tile[widthInTiles][heightInTiles];

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

                    if (red == 255 && green == 255 && blue == 255) {        //DirtNormalTile
                        tilesViaRGB[xx][yy] = new DirtNormalTile(xx, yy);
                    } else if (red == 0 && green == 0 && blue == 0) {       //fence
                        tilesViaRGB[xx][yy] = Tile.tiles[1];
                    } else if (red == 136 && green == 0 && blue == 21) {    //dirtWalkway
                        tilesViaRGB[xx][yy] = Tile.tiles[2];
                    } else if (red == 0 && green == 162 && blue == 232) {   //signPost
                        tilesViaRGB[xx][yy] = Tile.tiles[3];
                    } else if (red == 163 && green == 73 && blue == 164) {  //home5x4
                        for (int y = 0; y < 4; y++) {
                            for (int x = 0; x < 5; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[100 + (y * 5) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[100];
                    } else if (red == 163 && green == 76 && blue == 164) {  //cowBarn5x5
                        for (int y = 0; y < 5; y++) {
                            for (int x = 0; x < 5; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[120 + (y * 5) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[120];
                    } else if (red == 163 && green == 77 && blue == 164) {  //silos5x6
                        for (int y = 0; y < 6; y++) {
                            for (int x = 0; x < 5; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[145 + (y * 5) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[145];
                    } else if (red == 163 && green == 78 && blue == 164) {  //chickenCoop4x5
                        for (int y = 0; y < 5; y++) {
                            for (int x = 0; x < 4; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[175 + (y * 4) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[175];
                    } else if (red == 163 && green == 79 && blue == 164) {  //toolShed5x5
                        for (int y = 0; y < 5; y++) {
                            for (int x = 0; x < 5; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[195 + (y * 5) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[195];
                    } else if (red == 163 && green == 75 && blue == 164) {  //stable2x3
                        for (int y = 0; y < 3; y++) {
                            for (int x = 0; x < 2; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[220 + (y * 2) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[220];
                    } else if (red == 163 && green == 74 && blue == 164) {  //building2x3
                        for (int y = 0; y < 3; y++) {
                            for (int x = 0; x < 2; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[226 + (y * 2) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[226];
                    } else if (red == 255 && green == 242 && blue == 0) {   //chest2x2
                        for (int y = 0; y < 2; y++) {
                            for (int x = 0; x < 2; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[232 + (y * 2) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[232];
                    } else if (red == 0 && green == 0 && blue == 255) {     //poolWater2x2
                        for (int y = 0; y < 2; y++) {
                            for (int x = 0; x < 2; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[236 + (y * 2) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[236];
                    } else if (red == 0 && green == 0 && blue == 254) {     //poolWater3x3
                        for (int y = 0; y < 3; y++) {
                            for (int x = 0; x < 3; x++) {
                                tilesViaRGB[xx + x][yy + y] = Tile.tiles[240 + (y * 3) + x];
                            }
                        }
                        //tilesViaRGB[xx][yy] = Tile.tiles[240];
                    } else {
                        tilesViaRGB[xx][yy] = Tile.tiles[2];
                    }
                }
            }
        }

        return tilesViaRGB;
    }

    public void loadEntitiesPlacedNonRandomlyViaRGB(BufferedImage entitiesViaRGB) {
        // TODO:
    }

    public void loadEntitiesPlacedRandomly(BufferedImage tilesViaRGB, BufferedImage entitiesViaRGB) {
        // TODO:
    }

    // GETTERS & SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public int getSpawnX() { return spawnX; }

    public int getSpawnY() { return spawnY; }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile[][] getTilesViaRGB() { return tilesViaRGB; }

    public EntityManager getEntityManager() { return entityManager; }

    public void setEntityManager(EntityManager entityManager) { this.entityManager = entityManager; }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public int getWidth() {
        return widthInTiles;
    }

    public int getHeight() {
        return heightInTiles;
    }

} // **** end World class ****