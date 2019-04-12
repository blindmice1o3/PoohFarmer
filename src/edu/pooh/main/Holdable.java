package edu.pooh.main;

import edu.pooh.tiles.Tile;

public interface Holdable {

    void setPosition(float x, float y);

    void pickedUp();
    void dropped(Tile t);

} // **** end Holdable interface ****
