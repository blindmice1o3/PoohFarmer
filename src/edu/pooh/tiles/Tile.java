package edu.pooh.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    // STATIC STUFF HERE

    public static Tile[] tiles = new Tile[256];
    public static Tile dirtNormalTile = new DirtNormalTile(0);
    public static Tile fenceTile = new FenceTile(1);
    public static Tile dirtWalkway = new DirtWalkwayTile(2);
    public static Tile home5x4 = new Home5x4Tile(8);
    public static Tile nullTile = new NullTile(9);

    public static Tile signPostTile = new SignPostTile(3);

    // CLASS

    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;

    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;

        // CLEVER! confusing!
        tiles[id] = this;
    } // **** end Tile(BufferedImage, int) constructor ****

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean isSolid() {
        return false;
    }

    public int getId() {
        return id;
    }

} // **** end Tile class ****