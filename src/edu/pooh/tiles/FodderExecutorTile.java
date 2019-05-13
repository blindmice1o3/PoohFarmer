package edu.pooh.tiles;

import edu.pooh.entities.statics.statics1x1.Fodder;
import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;

import java.awt.image.BufferedImage;

public class FodderExecutorTile extends SolidGenericTile
        implements IInvokable {

    private Handler handler;
    private int x, y;

    public FodderExecutorTile(Handler handler, int x, int y, BufferedImage texture) {
        super(texture);

        this.handler = handler;
        this.x = x;
        this.y = y;
    } // **** end FodderExecutorTile(Handler, int, int, BufferedImage) constructor ****

    @Override
    public void execute() {
        if (handler.getWorld().getEntityManager().getPlayer().getHoldableObject() instanceof Fodder) {
            Tile tempFodderDisplayerTile = handler.getWorld().getTile(x, y-1);
            System.out.println("Suppose to be FodderDisplayerTile: " + handler.getWorld().getTile(x, y-1));

            if (tempFodderDisplayerTile instanceof FodderDisplayerTile) {
                ((FodderDisplayerTile)tempFodderDisplayerTile).setActivated(true);
            }

        }
    }

    public int getX() { return x; }
    public int getY() { return y; }

} // **** end FodderExecutorTile class ****