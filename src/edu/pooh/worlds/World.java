package edu.pooh.worlds;

import edu.pooh.entities.EntityManager;
import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.statics.Bush;
import edu.pooh.entities.statics.Rock;
import edu.pooh.items.Item;
import edu.pooh.items.ItemManager;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.utils.Utils;

import java.awt.*;

public class World {

    private Handler handler;

    private int widthInTiles;   // Width of world, in terms of number of tiles across.
    private int heightInTiles;  // Height of world, in terms of number of tiles down.
    private int spawnX; //NEED map to load before can be used.
    private int spawnY; //NEED map to load before can be used.

    private int[][] tiles;      // Multidimensional array of int storing <Tile id>.

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
        entityManager.addEntity(new Bush(handler, 320, 1250));
        entityManager.addEntity(new Bush(handler, 320, 1350) {
            @Override
            public void die() {
                handler.getWorld().getItemManager().addItem(Item.shovelItem.createNew((int)x, (int)y));
            }
        });
        entityManager.addEntity(new Rock(handler, 192, 1150));
        entityManager.addEntity(new Rock(handler, 192, 1250) {
            @Override
            public void die() {
                handler.getWorld().getItemManager().addItem(Item.wateringCanItem.createNew((int)x, (int)y));
            }
        });
        entityManager.addEntity(new Rock(handler, 192, 1350));


        loadWorld(path);    // Initializes tiles (multi-dimensional int array), and 4 instance variables.

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

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Tile getTile(int x, int y) {
        // This checks if the player is going outside the map's bound, returns a DirtWalkwayTile object as default.
        if (x < 0 || y < 0 || x >= widthInTiles || y >= heightInTiles) {
            return Tile.dirtWalkway;
        }

        Tile t = Tile.tiles[ tiles[x][y] ]; // The inner tiles[][] is World class's that stores int Tile id.

        if (t == null) {
            return Tile.dirtNormalTile;
        }

        return t;
    }

    public void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");   // Any amount of white space is a separator.

        widthInTiles = Utils.parseInt(tokens[0]);
        heightInTiles = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[widthInTiles][heightInTiles];

        for (int y = 0; y < heightInTiles; y++) {
            for (int x = 0; x < widthInTiles; x++) {
                // Converting from 1-D array to 2-D array means we need to convert the positioning inside tokens array.
                tiles[x][y] = Utils.parseInt( tokens[(x + (y * widthInTiles)) + 4] );
            }
        }
    }

    public int getWidth() {
        return widthInTiles;
    }

    public int getHeight() {
        return heightInTiles;
    }

    public int getSpawnX() { return spawnX; }

    public int getSpawnY() { return spawnY; }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
} // **** end World class ****