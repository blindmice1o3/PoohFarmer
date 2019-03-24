package edu.pooh.tiles;

import edu.pooh.gfx.Assets;

public class SignPostTile extends Tile {

    public SignPostTile(int id) {
        super(Assets.signPost, id);
    } // **** end SignPostTile(int) constructor ****

    @Override
    public boolean isSolid() {
        return true;
    }

} // **** end SignPostTile class ****