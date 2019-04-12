package edu.pooh.tiles;

import edu.pooh.gfx.Assets;

public class DirtNormalTile extends Tile {

    private boolean seedable;

    public DirtNormalTile() {
        super(Assets.dirtNormal, 0);
        seedable = true;
    } // **** end DirtNormalTile() constructor ****

    @Override
    public boolean isSolid() {
        return false;
    }

    public boolean getSeedable() {
        return seedable;
    }

    public void setSeedable(boolean seedable) {
        this.seedable = seedable;
    }

} // **** end DirtNormalTile class ****