package edu.pooh.entities.statics;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class Bush extends StaticEntity {

    public Bush(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
    } // **** end Bush(Handler, float, float) constructor ****

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.plantFlowering1, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

} // **** end Bush class ****