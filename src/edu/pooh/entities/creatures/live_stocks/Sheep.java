package edu.pooh.entities.creatures.live_stocks;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class Sheep extends Creature {

    public Sheep(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        //initAnimations();
    } // **** end Sheep(Handler, float, float) constructor ****

    @Override
    public void initAnimations() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void die() {
        active = false;
    }

} // **** end Sheep class ****