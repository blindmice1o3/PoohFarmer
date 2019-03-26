package edu.pooh.worlds;

import edu.pooh.tiles.Tile;

import java.awt.*;

public class World {

    private int widthInTiles;   // Width of world, in terms of how many tiles across.
    private int heightInTiles;  // Height of world, in terms of how many tiles down.

    private int[][] tiles;      // Multidimensional array of int storing <Tile id>.

    public World(String path) {
        loadWorld(path);
    } // **** end World(String) constructor ****

    public void tick() {

    }

    public void render(Graphics g) {
        for (int y = 0; y < heightInTiles; y++) {
            for (int x = 0; x < widthInTiles; x++) {
                getTile(x, y).render(g, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT); // Multiple to convert from
                                                                                          // x,y indexes to tile-size.
            }
        }
    }

    public Tile getTile(int x, int y) {
        Tile t = Tile.tiles[ tiles[x][y] ]; //The inner tiles[][] is World class's that stores int Tile id.

        if (t == null) {
            return Tile.dirtNormalTile;
        }

        return t;
    }

    public void loadWorld(String path) {
        widthInTiles = 5;
        heightInTiles = 5;
        tiles = new int[widthInTiles][heightInTiles];

        for (int x = 0; x < widthInTiles; x ++) {
            for (int y = 0; y < heightInTiles; y++) {
                tiles[x][y] = 0;    // id for dirt tile.
            }
        }
    }

} // **** end World class ****