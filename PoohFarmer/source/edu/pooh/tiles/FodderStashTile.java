package edu.pooh.tiles;

import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.statics.statics1x1.Fodder;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;

import java.awt.image.BufferedImage;

public class FodderStashTile extends SolidGenericTile
        implements IInvokable {

    private transient Handler handler;
    private int x, y;

    public FodderStashTile(Handler handler, int x, int y, BufferedImage texture) {
        super(texture);

        this.handler = handler;
        this.x = x;
        this.y = y;
    } // **** end FodderStashTile(Handler, int, int, BufferedImage) constructor ****

    @Override
    public void execute() {
        System.out.println("FodderStashTile.execute() called by player's KeyEvent.VK_COMMA");
        Player tempPlayer = handler.getWorld().getEntityManager().getPlayer();

        if ((tempPlayer.getHoldableObject() == null) && (handler.getResourceManager().getFodderCount() > 0)) {
            ////////////////////////////////////////////////////////////////////
            System.out.println("Instantiating new Fodder object and setting it as player's holdableObject");
            Fodder tempFodder = new Fodder(handler, (x * Tile.TILE_WIDTH),(y * Tile.TILE_HEIGHT));

            handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(
                    tempFodder
            );
            handler.getWorld().getEntityManager().setToBeAdded(true);
            tempPlayer.setHoldableObject(tempFodder);
            tempPlayer.setHolding(true);

            System.out.println("fodderCount BEFORE to FodderStashTie.execute(): " + handler.getResourceManager().getFodderCount());
            handler.getResourceManager().decreaseFodderCount(1);
            System.out.println("fodderCount AFTER to FodderStashTie.execute(): " + handler.getResourceManager().getFodderCount());
            ////////////////////////////////////////////////////////////////////
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end FodderStashTile class ****