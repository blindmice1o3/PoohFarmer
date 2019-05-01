package edu.pooh.entities.statics.statics1x1;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class SignPost extends StaticEntity {

    public SignPost(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
    } // **** end SignPost(Handler, float, float) constructor ****

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.signPostTransparent, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void hurt(int dmg) {
        return;
    }

    @Override
    public void die() {
        setActive(false);
    }
}
