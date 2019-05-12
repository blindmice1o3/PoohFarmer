package edu.pooh.tiles;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FodderDisplayerTile extends SolidGenericTile {

    private Handler handler;
    private int x, y;
    private boolean activated;

    public FodderDisplayerTile(Handler handler, int x, int y, BufferedImage texture) {
        super(texture);

        this.handler = handler;
        this.x = x;
        this.y = y;
        activated = false;
    } // **** end FodderDisplayerTile(BufferedImage) constructor ****

    @Override
    public void render(Graphics g, int x, int y) {
        super.render(g, x, y);

        if (activated) {
            g.drawImage(Assets.hay, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        }
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

} // **** end FodderDisplayerTile class ****