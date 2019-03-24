package edu.pooh.tiles;

import edu.pooh.gfx.Assets;

public class FenceTile extends Tile {

    public FenceTile(int id) {
        super(Assets.fence, id);
    } // **** end FenceTile(int) constructor ****

    @Override
    public boolean isSolid() {
        return true;
    }

} // **** end FenceTile class ****
