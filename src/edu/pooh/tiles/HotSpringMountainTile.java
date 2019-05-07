package edu.pooh.tiles;

import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;

import java.awt.image.BufferedImage;

public class HotSpringMountainTile extends SolidGenericTile
        implements IInvokable {

    private Handler handler;
    protected int x, y;

    public HotSpringMountainTile(Handler handler, int x, int y, BufferedImage texture) {
        super(texture);

        this.handler = handler;
        this.x = x;
        this.y = y;
    } // **** end HotSpringMountainTile(Handler, int, int BufferedImage) constructor ****

    @Override
    public void execute() {
        if (handler.getWorld().getEntityManager().getPlayer().getX() < (x * Tile.TILE_WIDTH)) {
            // Move player sprite into hot spring.
            handler.getWorld().getEntityManager().getPlayer().setX((x * Tile.TILE_WIDTH) + Tile.TILE_WIDTH + 1);

            // Increase player's staminaCurrent by 24.
            ///////////////////////////////////////////////////////////////////////////////////////////
            handler.getWorld().getEntityManager().getPlayer().increaseStaminaCurrent(24);
            ///////////////////////////////////////////////////////////////////////////////////////////
        } else {
            handler.getWorld().getEntityManager().getPlayer().setX((x * Tile.TILE_WIDTH) - Tile.TILE_WIDTH - 1);
        }

        System.out.println("HotSpringMountainTile execute() method called");
    }

} // **** end HotSpringMountainTile class ****