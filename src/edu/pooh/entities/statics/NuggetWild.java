package edu.pooh.entities.statics;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.Holdable;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class NuggetWild extends StaticEntity
        implements Holdable {

    public NuggetWild(Handler handler, float x, float y) {
        super(handler, x, y, (int)(Tile.TILE_WIDTH * 0.5), (int)(Tile.TILE_HEIGHT * 0.5));
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.honeyPot, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() {

    }

    @Override
    public void hurt(int amt) {
        return;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void pickedUp() {

    }

    @Override
    public void dropped(Tile t) {

    }

} // **** end NuggetWild class ****