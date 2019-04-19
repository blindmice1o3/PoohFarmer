package edu.pooh.entities.statics.harvests;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.Holdable;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static edu.pooh.entities.statics.harvests.HarvestEntity.HarvestType.*;

public class HarvestEntity extends StaticEntity
        implements Holdable {

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
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
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
        g.drawImage(texture, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
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
        for (int xx = 0; xx < handler.getWorld().getWidth(); xx++) {
            for (int yy = 0; yy < handler.getWorld().getHeight(); yy++) {
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
                if (tempTile.checkFragmentedStaticEntity()) {
                    tempTile.fragmentedTimer(3000); //3000 milliseconds == 3 seconds???
                    tempTile.removeStaticEntity();
                }
                System.out.println("dropped DirtNormalTile's (x, y): (" + x + ", " + y + ")");
            }
        } else {
            Tile[][] tempTiles = handler.getWorld().getTilesViaRGB();
//            Tile[][] tempTiles = handler.getWorld().getTiles();

            for (int y = 0; y < handler.getWorld().getHeight(); y++) {
                for (int x = 0; x < handler.getWorld().getWidth(); x++) {
                    if (tempTiles[x][y].getId() == t.getId()) {
                        this.x = x * Tile.TILE_WIDTH;
                        this.y = y * Tile.TILE_HEIGHT;
                        System.out.println("dropped Chest's (x, y): (" + this.x + ", " + this.y + ")");

                        die();
                        /////////////////////////////////////////////////////////////////////////////
                        handler.getWorld().getEntityManager().getPlayer().increaseCannabisCollected();
                        /////////////////////////////////////////////////////////////////////////////
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