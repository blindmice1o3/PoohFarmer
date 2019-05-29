package edu.pooh.tiles;

import edu.pooh.entities.statics.statics1x1.Fodder;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;
import edu.pooh.worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FodderExecutorTile extends SolidGenericTile
        implements IInvokable {

    private Handler handler;
    private int x, y;

    protected boolean specialActive;

    public FodderExecutorTile(Handler handler, int x, int y, BufferedImage texture) {
        super(texture);

        this.handler = handler;
        this.x = x;
        this.y = y;

        specialActive = false;
    } // **** end FodderExecutorTile(Handler, int, int, BufferedImage) constructor ****

    @Override
    public void execute() {
        if (handler.getWorld().getEntityManager().getPlayer().getHoldableObject() instanceof Fodder) {
            if (handler.getWorld().getWorldType() == World.WorldType.CHICKEN_COOP) {
                Tile tempFodderDisplayerTile = handler.getWorld().getTile(x, y - 1);
                System.out.println("Suppose to be FodderDisplayerTile: " + handler.getWorld().getTile(x, y - 1));

                if (tempFodderDisplayerTile instanceof FodderDisplayerTile) {
                    ((FodderDisplayerTile)tempFodderDisplayerTile).setActivated(true);
                }
            } else if (handler.getWorld().getWorldType() == World.WorldType.COW_BARN) {
                Tile tempFodderDisplayerTileRight = handler.getWorld().getTile(x + 1, y);
                System.out.println("Suppose to be FodderDisplayerTile: " + handler.getWorld().getTile(x + 1, y));

                Tile tempFodderDisplayerTileLeft = handler.getWorld().getTile(x - 1, y);
                System.out.println("Suppose to be FodderDisplayerTile: " + handler.getWorld().getTile(x - 1, y));

                if (tempFodderDisplayerTileRight instanceof FodderDisplayerTile) {
                    ((FodderDisplayerTile)tempFodderDisplayerTileRight).setActivated(true);
                } else if (tempFodderDisplayerTileLeft instanceof FodderDisplayerTile) {
                    ((FodderDisplayerTile)tempFodderDisplayerTileLeft).setActivated(true);
                }
                //@@@@@@ COW INCUBATOR'S IS DIFFERENT!!! NOT FODDER DISPLAYER TILE!!! @@@@@@@
                else if (tempFodderDisplayerTileLeft instanceof SolidGenericTile) {
                    specialActive = true;
                }
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            }
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        super.render(g, x, y);

        if (specialActive) {
            g.drawImage(Assets.hay, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isSpecialActive() { return specialActive; }
    public void setSpecialActive(boolean specialActive) { this.specialActive = specialActive; }

} // **** end FodderExecutorTile class ****