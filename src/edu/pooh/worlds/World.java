package edu.pooh.worlds;

import edu.pooh.main.Game;
import edu.pooh.tiles.Tile;
import edu.pooh.utils.Utils;

import java.awt.*;

public class World {

    private Game game;
    private int widthInTiles;   // Width of world, in terms of how many tiles across.
    private int heightInTiles;  // Height of world, in terms of how many tiles down.
    private int spawnX;
    private int spawnY;

    private int[][] tiles;      // Multidimensional array of int storing <Tile id>.

    public World(Game game, String path) {
        this.game = game;
        loadWorld(path);
    } // **** end World(Game, String) constructor ****

    public void tick() {

    }

    public void render(Graphics g) {
        for (int y = 0; y < heightInTiles; y++) {
            for (int x = 0; x < widthInTiles; x++) {
                getTile(x, y).render(g, (int)(x * Tile.TILE_WIDTH - game.getGameCamera().getxOffset()),
                        (int)(y * Tile.TILE_HEIGHT - game.getGameCamera().getyOffset())); // Multiple to convert from
                                                                                          // x,y indexes to tile-size.
            }
        }
    }

    public Tile getTile(int x, int y) {
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

} // **** end World class ****