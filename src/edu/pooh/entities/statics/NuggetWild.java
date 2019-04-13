package edu.pooh.entities.statics;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.Holdable;
import edu.pooh.tiles.DirtNormalTile;
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
        setActive(false);
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
        if (t instanceof DirtNormalTile) {
            DirtNormalTile tempTile = (DirtNormalTile) t;
            x = tempTile.getX() * Tile.TILE_WIDTH;
            y = tempTile.getY() * Tile.TILE_HEIGHT;
            System.out.println("dropped DirtNormalTile's (x, y): (" + x + ", " + y + ")");
        } else {
            Tile[][] tempTiles = handler.getWorld().getTilesViaRGB();
//            Tile[][] tempTiles = handler.getWorld().getTiles();

            for (int y = 0; y < handler.getWorld().getHeight(); y++) {
                for (int x = 0; x < handler.getWorld().getWidth(); x++) {
                    if (tempTiles[x][y].getId() == t.getId()) {
                        this.x = x * Tile.TILE_WIDTH;
                        this.y = y * Tile.TILE_HEIGHT;
                        System.out.println("dropped Chest's (x, y): (" + this.x + ", " + this.y + ")");

                        die();
                        /////////////////////////////////////////////////////////////////////////////
                        handler.getWorld().getEntityManager().getPlayer().increaseCannabisCollected();
                        /////////////////////////////////////////////////////////////////////////////
                    }
                }
            }
        }
    }

} // **** end NuggetWild class ****