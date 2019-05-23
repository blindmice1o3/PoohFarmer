package edu.pooh.entities.statics.produce_yields;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.entities.statics.statics2x2.ShippingBin;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.main.ISellable;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HarvestEntity extends StaticEntity
        implements IHoldable, ISellable {

    public enum HarvestType {
        CANNABIS_WILD, TURNIP, POTATO, TOMATO, CORN;
    }

    private HarvestType harvestType;
    private BufferedImage texture;
    private int price;
    private boolean inShippingBin;

    public HarvestEntity(Handler handler, float x, float y) {
        super(handler, (int)(x + (Tile.TILE_WIDTH * 0.125)), (int)(y + (Tile.TILE_HEIGHT * 0.125)),
                (int)(Tile.TILE_WIDTH * 0.75), (int)(Tile.TILE_HEIGHT * 0.75));

        inShippingBin = false;
    }

    public void setPrice(int price) {
        this.price = price;
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
        } else if (handler.getWorld().getWorldType() == World.WorldType.CHICKEN_COOP) {
            setX(shippingBin.getX());
            setY(shippingBin.getY() + Tile.TILE_HEIGHT);
            inShippingBin = true;
            shippingBin.addISellable(this);
        }
    }

    public void setTextureAndPrice() {
        if (harvestType != null) {
            switch (harvestType) {
                case CANNABIS_WILD:
                    setTexture(Assets.honeyPot);
                    setPrice(80);
                    break;
                case TURNIP:
                    setTexture(Assets.turnip0Whole);
                    setPrice(60);
                    break;
                case POTATO:
                    setTexture(Assets.potato0Whole);
                    setPrice(80);
                    break;
                case TOMATO:
                    setTexture(Assets.tomato0Whole);
                    setPrice(100);
                    break;
                case CORN:
                    setTexture(Assets.corn0Whole);
                    setPrice(120);
                    break;
                default:
                    setTexture(Assets.honeyPot);
                    setPrice(80);
                    break;
            }
        }
    }

    public BufferedImage determineFragmentedTexture() {
        switch (harvestType) {
            case CANNABIS_WILD:
                return Assets.honeyPot;
            case TURNIP:
                return Assets.turnip0Fragmented;
            case POTATO:
                return Assets.potato0Fragmented;
            case TOMATO:
                return Assets.tomato0Fragmented;
            case CORN:
                return Assets.corn0Fragmented;
            default:
                return Assets.waterFX;
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

        g.drawImage(texture, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private long fragmentedPrevious;
    private long fragmentedElapsed = 0;
    public static final long FRAGMENTEDTIMELIMIT = 1000 * 2;

    public void fragmentedTimer(long timeLimit) {
        fragmentedPrevious = System.currentTimeMillis();

        while (fragmentedElapsed <= timeLimit) {
            fragmentedElapsed += System.currentTimeMillis() - fragmentedPrevious;
            fragmentedPrevious = System.currentTimeMillis();
        }

        fragmentedElapsed = 0;

        die();
    }

    @Override
    public void die() {
        setActive(false);
    }

    @Override
    public void hurt(int amt) {
        return;
    }

    // HOLDABLE INTERFACE

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
                if (tempLevel[xx][yy] instanceof DirtNormalTile) {
                    if (((DirtNormalTile)tempLevel[xx][yy]).getStaticEntity() == this) {
                        ((DirtNormalTile)tempLevel[xx][yy]).setStaticEntity(null);
                    }
                }
            }
        }
    }

    @Override
    public void dropped(Tile t) {
        if (t instanceof DirtNormalTile) {
            DirtNormalTile tempTile = (DirtNormalTile) t;

            if (tempTile.getStaticEntity() == null) {
                x = tempTile.getX() * Tile.TILE_WIDTH;
                y = tempTile.getY() * Tile.TILE_HEIGHT;

                tempTile.setStaticEntity(this);
            }
        } else {    //poolWater Tile
            if (t.getId() >= 236 && t.getId() <= 248) {
                die();
                System.out.println("dropped HarvestEntity into poolWater's (x, y): (" + this.x + ", " + this.y + ")");
            }
        }
    }

    @Override
    public String toString() {
        return "HarvestEntity object with its harvestType set to: " + harvestType + ".";
    }

    // GETTERS & SETTERS

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public HarvestType getHarvestType() {
        return harvestType;
    }

    public void setHarvestType(HarvestType harvestType) {
        this.harvestType = harvestType;
    }

} // **** end HarvestEntity class ****