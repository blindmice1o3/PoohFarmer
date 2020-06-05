package edu.pooh.entities.statics.statics1x1;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.tiles.DirtNotFarmableTile;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class RockMountain extends StaticEntity
        implements IHoldable {

    public RockMountain(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
    } // **** end RockMountain(Handler, float, float) constructor ****

    @Override
    public void tick() {
        return;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rockMountain, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() {
        for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
            for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
                if (handler.getWorld().getTile(xx, yy) instanceof DirtNotFarmableTile) {
                    if (((DirtNotFarmableTile)handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {
                        ((DirtNotFarmableTile)handler.getWorld().getTile(xx, yy)).setStaticEntity(null);
                    }
                }
            }
        }

        setActive(false);
    }

    // IHOLDABLE INTERFACE

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void pickedUp() {
        Tile[][] tempLevel = handler.getWorld().getTilesViaRGB();
        for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
            for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                if (tempLevel[xx][yy] instanceof DirtNotFarmableTile) {
                    if (((DirtNotFarmableTile)tempLevel[xx][yy]).getStaticEntity() == this) {
                        ((DirtNotFarmableTile)tempLevel[xx][yy]).setStaticEntity(null);
                    }
                }
            }
        }
    }

    @Override
    public void dropped(Tile t) {
        if (t instanceof DirtNotFarmableTile) {
            DirtNotFarmableTile tempTile = (DirtNotFarmableTile)t;
            x = tempTile.getX() * Tile.TILE_WIDTH;
            y = tempTile.getY() * Tile.TILE_HEIGHT;
            System.out.println("dropped DirtNotFarmableTile's (x, y): (" + x + ", " + y + ")");
            tempTile.setStaticEntity(this);
        }
    }

} // **** end RockMountain class ****