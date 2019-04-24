package edu.pooh.entities.statics.harvests;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HarvestEntity extends StaticEntity
        implements IHoldable {

    public enum HarvestType {
        CANNABIS_WILD,
        TURNIP,
        POTATO,
        TOMATO,
        CORN;
    }

    private HarvestType harvestType;
    private BufferedImage texture;

    public HarvestEntity(Handler handler, float x, float y) {
        super(handler, x, y, (int)(Tile.TILE_WIDTH * 0.5), (int)(Tile.TILE_HEIGHT * 0.5));
    }

    public void determineAndSetTexture() {
        if (harvestType != null) {
            switch (harvestType) {
                case CANNABIS_WILD:
                    setTexture(Assets.honeyPot);
                    break;
                case TURNIP:
                    setTexture(Assets.turnip0Whole);
                    break;
                case POTATO:
                    setTexture(Assets.potato0Whole);
                    break;
                case TOMATO:
                    setTexture(Assets.tomato0Whole);
                    break;
                case CORN:
                    setTexture(Assets.corn0Whole);
                    break;
                default:
                    setTexture(Assets.honeyPot);
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

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture, (int)(x - handler.getGameCamera().getxOffset() + (width / 2)),
                (int)(y - handler.getGameCamera().getyOffset() + (height / 2)), width, height, null);
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
        /*
        for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
            for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
                if (handler.getWorld().getTile(xx, yy) instanceof DirtNormalTile) {
                    if (((DirtNormalTile)handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {

                        fragmentedTimer(fragmentedTimeLimit);
                        ((DirtNormalTile)handler.getWorld().getTile(xx, yy)).setStaticEntity(null);

                    }
                }
            }
        }
        */

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

                setTexture(determineFragmentedTexture());

                tempTile.setStaticEntity(this);

                tempTile.checkRemoveFragmentedStaticEntity();
            }
        } else {
            Tile[][] tempTiles = handler.getWorld().getTilesViaRGB();
//            Tile[][] tempTiles = handler.getWorld().getTiles();

            for (int y = 0; y < handler.getWorld().getHeightInTiles(); y++) {
                for (int x = 0; x < handler.getWorld().getWidthInTiles(); x++) {
                    if (tempTiles[x][y].getId() == t.getId()) {
                        this.x = x * Tile.TILE_WIDTH;
                        this.y = y * Tile.TILE_HEIGHT;

                        /////////////////////////////////////////////////////////////////////////////
                        handler.getWorld().getEntityManager().getPlayer().increaseCannabisCollected();
                        /////////////////////////////////////////////////////////////////////////////

                        System.out.println("dropped Chest's (x, y): (" + this.x + ", " + this.y + ")");
                        die();
                    }
                }
            }
        }
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