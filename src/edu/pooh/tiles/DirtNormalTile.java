package edu.pooh.tiles;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.entities.statics.produce_yields.HarvestEntity;
import edu.pooh.gfx.Assets;

import java.awt.*;

public class DirtNormalTile extends Tile {

    public enum DirtState {
        NORMAL, TILLED, SEEDED;
    }

    private int x, y;
    private DirtState dirtState;
    private boolean watered;

    private StaticEntity staticEntity;

    public DirtNormalTile(int x, int y) {
        super(Assets.dirtNormal, 0);
        this.x = x;
        this.y = y;
        dirtState = DirtState.NORMAL;
        watered = false;

        staticEntity = null;
    } // **** end DirtNormalTile(int, int) constructor ****

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        switch (dirtState) {
            case NORMAL:
                g.drawImage(Assets.dirtNormal, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
                break;
            case TILLED:
                if (!watered) {
                    g.drawImage(Assets.dirtTilledDry, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
                } else {
                    g.drawImage(Assets.dirtTilledWatered, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
                }
                break;
            case SEEDED:
                if (!watered) {
                    g.drawImage(Assets.dirtSeededDry, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
                } else {
                    g.drawImage(Assets.dirtSeededWatered, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
                }
                break;
            default:
                System.out.println("DirtNormalTile.render(Graphics, int, int) switch(dirtState) construct's default.");
                break;
        }

        /*
        if (staticEntity != null) {
            staticEntity.render(g);
        }
        */
    }

    // GETTERS & SETTERS

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

    public DirtState getDirtState() {
        return dirtState;
    }

    public void setDirtState(DirtState dirtState) {
        this.dirtState = dirtState;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public StaticEntity getStaticEntity() {
        return staticEntity;
    }

    public void setStaticEntity(StaticEntity staticEntity) {
        this.staticEntity = staticEntity;
    }

} // **** end DirtNormalTile class ****