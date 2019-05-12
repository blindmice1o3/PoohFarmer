package edu.pooh.tiles;

import edu.pooh.entities.statics.statics1x1.Fodder;
import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;

import java.awt.image.BufferedImage;

public class FodderStashTile extends SolidGenericTile implements IInvokable {

    private Handler handler;
    private int x, y;

    public FodderStashTile(Handler handler, int x, int y, BufferedImage texture) {
        super(texture);

        this.handler = handler;
        this.x = x;
        this.y = y;
    } // **** end FodderStashTile(Handler, int, int, BufferedImage) constructor ****

    @Override
    public void execute() {
        if (handler.getWorld().getEntityManager().getPlayer().getHoldableObject() == null) {
            ////////////////////////////////////////////////////////////////////
            handler.getWorld().getEntityManager().getPlayer().setHoldableObject( new Fodder(handler, (float)x, (float)y) );
            ////////////////////////////////////////////////////////////////////

            handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(
                    (Fodder)handler.getWorld().getEntityManager().getPlayer().getHoldableObject()
            );
            handler.getWorld().getEntityManager().setToBeAdded(true);
        }
    }

} // **** end FodderStashTile class ****