package edu.pooh.tiles;

import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.statics.statics1x1.Wood;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;

import java.awt.image.BufferedImage;

public class WoodStashTile extends SolidGenericTile
        implements IInvokable {

    private transient Handler handler;
    private int x, y;

    public WoodStashTile(Handler handler, int x, int y, BufferedImage texture) {
        super(texture);

        this.handler = handler;
        this.x = x;
        this.y = y;
    } // **** end WoodStashTile(Handler, int, int, BufferedImage) constructor ****

    @Override
    public void execute() {
        System.out.println("WoodStashTile.execute() called by player's KeyEvent.VK_COMMA");
        Player tempPlayer = handler.getWorld().getEntityManager().getPlayer();

        if ((tempPlayer.getHoldableObject() == null) && (handler.getResourceManager().getWoodCount() > 0)) {
            ////////////////////////////////////////////////////////////////////
            System.out.println("Instantiating new Wood object and setting it as player's holdableObject");
            Wood tempWood = new Wood(handler, (x * Tile.TILE_WIDTH), (y * Tile.TILE_HEIGHT));

            handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(
                    tempWood
            );
            handler.getWorld().getEntityManager().setToBeAdded(true);
            tempPlayer.setHoldableObject(tempWood);
            tempPlayer.setHolding(true);

            System.out.println("woodCount BEFORE to WoodStashTie.execute(): " + handler.getResourceManager().getWoodCount());
            handler.getResourceManager().decreaseWoodCount(1);
            System.out.println("woodCount AFTER to WoodStashTie.execute(): " + handler.getResourceManager().getWoodCount());
            ////////////////////////////////////////////////////////////////////
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end WoodStashTile class ****