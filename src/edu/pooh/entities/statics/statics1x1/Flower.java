package edu.pooh.entities.statics.statics1x1;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.DirtNotFarmableTile;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Flower extends StaticEntity
        implements IHoldable {

    public enum FlowerType {
        PURPLE, YELLOW;
    }

    private FlowerType flowerType;

    public Flower(Handler handler, float x, float y, FlowerType flowerType) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        this.flowerType = flowerType;
    } // **** end Flower(Handler, float, float, FlowerType) constructor ****

    @Override
    public void tick() {
        return;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getFlowerTexture(flowerType), (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public BufferedImage getFlowerTexture(FlowerType flowerType) {
        switch (flowerType) {
            case PURPLE:
                return Assets.flowerPurple;
            case YELLOW:
                return Assets.flowerYellow;
            default:
                return Assets.waterFX;
        }
    }

    @Override
    public void hurt(int dmg) {
        return;
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

            if (tempTile.getStaticEntity() == null) {
                x = tempTile.getX() * Tile.TILE_WIDTH;
                y = tempTile.getY() * Tile.TILE_HEIGHT;
                System.out.println("dropped Flower onto DirtNotFarmableTile (x, y): (" + x + ", " + y + ")");
                tempTile.setStaticEntity(this);
            }
        } else if (t instanceof DirtNormalTile) {
            DirtNormalTile tempTile = (DirtNormalTile)t;

            if (tempTile.getStaticEntity() == null) {
                x = tempTile.getX() * Tile.TILE_WIDTH;
                y = tempTile.getY() * Tile.TILE_HEIGHT;
                System.out.println("dropped Flower onto DirtNormalTile (x, y): (" + x + ", " + y + ")");
                tempTile.setStaticEntity(this);
            }
        }
    }

    // GETTERS & SETTERS

    public FlowerType getFlowerType() { return flowerType; }

    public void setFlowerType(FlowerType flowerType) { this.flowerType = flowerType; }

} // **** end Flower class ****