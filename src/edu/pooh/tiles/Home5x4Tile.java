package edu.pooh.tiles;

import edu.pooh.gfx.Assets;

public class Home5x4Tile extends Tile {

    public Home5x4Tile(int id) {
        super(Assets.home5x4, id);
    } // **** end Home5x4Tile(int) constructor ****

    @Override
    public boolean isSolid() {
        return true;
    }

} // **** end Home5x4Tile class ****