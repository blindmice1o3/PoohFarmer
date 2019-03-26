package edu.pooh.tiles;

import java.awt.*;

public class NullTile extends Tile {

    public NullTile(int id) {
        super(null, id);
    } // **** end NullTile(int) constructor ****

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        // INTENTIONALLY DO NOTHING.
    }

} // **** end NullTile class ****