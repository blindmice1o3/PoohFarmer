package edu.pooh.entities.statics.produce_yields;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.entities.statics.statics2x2.ShippingBin;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.main.ISellable;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.DirtNotFarmableTile;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Milk extends StaticEntity
        implements IHoldable, ISellable {

    public enum MilkSize {
        SMALL, MEDIUM, LARGE;
    }

    private MilkSize milkSize;
    private int price;
    private boolean inShippingBin;
    private BufferedImage texture;

    public Milk(Handler handler, float x, float y, MilkSize milkSize) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        this.milkSize = milkSize;
        setPriceAndTextureByMilkSize();
        inShippingBin = false;
    } // **** end Milk(Handler, float, float) constructor

    private void setPriceAndTextureByMilkSize() {
        switch (milkSize) {
            case SMALL:
                setPrice(150);
                setTexture(Assets.milkSmall);
                break;
            case MEDIUM:
                setPrice(250);
                setTexture(Assets.milkMedium);
                break;
            case LARGE:
                setPrice(350);
                setTexture(Assets.milkLarge);
                break;
            default:
                setPrice(0);
                break;
        }
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void dropIntoShippingBin(ShippingBin shippingBin) {
        if (handler.getWorld().getWorldType() == World.WorldType.GAME) {
            setX(shippingBin.getX() + Tile.TILE_WIDTH);
            setY(shippingBin.getY() + Tile.TILE_HEIGHT);
            inShippingBin = true;
            shippingBin.addISellable(this);
        } else if ((handler.getWorld().getWorldType() == World.WorldType.CHICKEN_COOP) ||
                (handler.getWorld().getWorldType() == World.WorldType.COW_BARN)) {
            setX(shippingBin.getX());
            setY(shippingBin.getY() + Tile.TILE_HEIGHT);
            inShippingBin = true;
            shippingBin.addISellable(this);
        }
    }

    @Override
    public void tick() {
        return;
    }

    @Override
    public void render(Graphics g) {
        if (inShippingBin) {
            return;
        }

        g.drawImage(getTexture(), (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void hurt(int dmg) { return; }

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
            ///////////////////////////////
            tempTile.setStaticEntity(this);
            ///////////////////////////////
        } else if (t instanceof DirtNormalTile) {
            DirtNormalTile tempTile = (DirtNormalTile) t;

            x = tempTile.getX() * Tile.TILE_WIDTH;
            y = tempTile.getY() * Tile.TILE_HEIGHT;
            System.out.println("dropped DirtNormalTile's (x, y): (" + x + ", " + y + ")");
            ///////////////////////////////
            tempTile.setStaticEntity(this);
            ///////////////////////////////
        }
    }

    @Override
    public String toString() {
        return "Milk object with its milkSize set to: " + milkSize + ".";
    }

    // GETTERS and SETTERS

    public void setPrice(int price) {
        this.price = price;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

} // **** end Milk class ****