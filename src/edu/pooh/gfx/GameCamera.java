package edu.pooh.gfx;

import edu.pooh.entities.Entity;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

public class GameCamera {

    private Handler handler;
    private float xOffset;
    private float yOffset;

    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    } // **** end GameCamera(Handler, float, float) constructor ****

    public void checkBlankSpace() {
        if (xOffset < 0) {
            xOffset = 0;
        } else if (xOffset > (handler.getWorld().getWidth() * Tile.TILE_WIDTH) - handler.getWidth()) {
            xOffset = (handler.getWorld().getWidth() * Tile.TILE_WIDTH) - handler.getWidth();
        }
        if (yOffset < 0) {
            yOffset = 0;
        } else if (yOffset > (handler.getWorld().getHeight() * Tile.TILE_HEIGHT) - handler.getHeight()) {
            yOffset = (handler.getWorld().getHeight() * Tile.TILE_HEIGHT) - handler.getHeight();
        }

    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - (Game.WIDTH_OF_FRAME / 2) + (e.getWidth() / 2);
        yOffset = e.getY() - (Game.HEIGHT_OF_FRAME / 2) + (e.getHeight() / 2);
        checkBlankSpace();
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
        checkBlankSpace();
    }

    // GETTERS & SETTERS

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

} // **** end GameCamera class ****