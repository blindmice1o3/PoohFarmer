package edu.pooh.entities.statics.statics1x1;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.tiles.FodderDisplayerTile;
import edu.pooh.tiles.FodderExecutorTile;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;

public class Fodder extends StaticEntity
        implements IHoldable {

    public Fodder(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
    } // **** end Fodder(Handler, float, float) constructor ****

    @Override
    public void tick() {
        return;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.hay, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }
    @Override
    public void hurt(int dmg) {
        return;
    }

    @Override
    public void die() {
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
        System.out.println("Fodder.pickedUp() called but currently does nothing");

        return;
    }

    @Override
    public void dropped(Tile t) {
        System.out.println("Fodder.dropped(Tile) being called");

        if (t instanceof FodderExecutorTile) {
            System.out.println("Fodder.dropped(Tile) called on a FodderExecutorTile object");

            FodderExecutorTile tempFodderExecutorTile = (FodderExecutorTile)t;
            x = tempFodderExecutorTile.getX();
            y = tempFodderExecutorTile.getY();

            if (handler.getWorld().getWorldType() == World.WorldType.CHICKEN_COOP) {
                ////////////////////////////////////////////////////////////////////@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                if (handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)((y / Tile.TILE_HEIGHT) - 1))
                ////////////////////////////////////////////////////////////////////@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        instanceof FodderDisplayerTile) {
                    System.out.println("Fodder.dropped(Tile) method probably successful if we get here");

                    ((FodderDisplayerTile)handler.getWorld().getTile(
                            (int)(x / Tile.TILE_WIDTH), (int)((y / Tile.TILE_HEIGHT) - 1))
                    ).setActivated(true);
                }
            } else if (handler.getWorld().getWorldType() == World.WorldType.COW_BARN) {
                ////////////////////////////////////@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                if ((handler.getWorld().getTile((int)((x / Tile.TILE_WIDTH) - 1), (int)(y / Tile.TILE_HEIGHT))
                ////////////////////////////////////@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        instanceof FodderDisplayerTile)) {
                    System.out.println("Fodder.dropped(Tile) method probably successful if we get here");

                    ((FodderDisplayerTile)handler.getWorld().getTile(
                            (int)((x / Tile.TILE_WIDTH) - 1), (int)(y / Tile.TILE_HEIGHT))
                    ).setActivated(true);
                }
                /////////////////////////////////////////@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                else if ((handler.getWorld().getTile((int)((x / Tile.TILE_WIDTH) + 1), (int)(y / Tile.TILE_HEIGHT))
                /////////////////////////////////////////@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        instanceof FodderDisplayerTile)) {
                    System.out.println("Fodder.dropped(Tile) method probably successful if we get here");

                    ((FodderDisplayerTile)handler.getWorld().getTile(
                            (int)((x / Tile.TILE_WIDTH) + 1), (int)(y / Tile.TILE_HEIGHT))
                    ).setActivated(true);
                }
            }
        } else {
            die();
        }
    }

} // **** end Fodder class ****