package edu.pooh.tiles;

import edu.pooh.gfx.Assets;

import java.awt.*;

public class Home5x4Tile extends Tile {

    public Home5x4Tile(int id) {
        super(Assets.home5x4[0][0], id);
    } // **** end Home5x4Tile(int) constructor ****

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, 1 * Tile.TILE_WIDTH, 1 * Tile.TILE_HEIGHT, null);
    }

} // **** end Home5x4Tile class ****