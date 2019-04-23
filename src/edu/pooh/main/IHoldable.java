package edu.pooh.main;

import edu.pooh.tiles.Tile;

public interface IHoldable {

    void setPosition(float x, float y);

    void pickedUp();
    void dropped(Tile t);

} // **** end IHoldable interface ****
