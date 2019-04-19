package edu.pooh.tiles;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.entities.statics.harvests.HarvestEntity;
import edu.pooh.gfx.Assets;

import java.awt.*;

public class DirtNormalTile extends Tile {

    public enum DirtState {
        NORMAL, TILLED;
    }

    private int x, y;
    private DirtState dirtState;
    //private boolean seedable;
    private StaticEntity staticEntity;

    public DirtNormalTile(int x, int y) {
        super(Assets.dirtNormal, 0);
        this.x = x;
        this.y = y;
        dirtState = DirtState.NORMAL;

        //seedable = false;
        staticEntity = null;
    } // **** end DirtNormalTile(int, int) constructor ****

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        if (staticEntity != null) {
            staticEntity.render(g);
        } else {
            g.drawImage(texture, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        }
    }

    // GETTERS & SETTERS

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

    //public boolean getSeedable() {
    //    return seedable;
    //}

    //public void setSeedable(boolean seedable) {
    //    this.seedable = seedable;
    //}

    public StaticEntity getStaticEntity() {
        return staticEntity;
    }

    private long fragmentedPrevious;
    private long fragmentedElapsed = 0;
    private long fragmentedTimeLimit = 1000 * 3;

    public void fragmentedTimer(long timeLimit) {
        fragmentedPrevious = System.currentTimeMillis();

        while (fragmentedElapsed <= timeLimit) {
            fragmentedElapsed += System.currentTimeMillis() - fragmentedPrevious;
            fragmentedPrevious = System.currentTimeMillis();
        }

        fragmentedElapsed = 0;
    }

    public boolean checkFragmentedStaticEntity() {
        // Check if fragmented HarvestEntity... if so, set it to inactive.
        if (staticEntity instanceof HarvestEntity) {
            if((((HarvestEntity)staticEntity).getTexture() == Assets.turnip0Fragmented) ||
                    (((HarvestEntity)staticEntity).getTexture() == Assets.potato0Fragmented) ||
                    (((HarvestEntity)staticEntity).getTexture() == Assets.tomato0Fragmented) ||
                    (((HarvestEntity)staticEntity).getTexture() == Assets.corn0Fragmented)) {
                return true;
            } else { return false; }
        } else {
            return false;
        }
    }

    public void removeStaticEntity() {
        if (staticEntity != null) {
            staticEntity.setActive(false);
            staticEntity = null;
            setTexture(Assets.dirtNormal);
        }
    }

    public void setStaticEntity(StaticEntity staticEntity) {
        this.staticEntity = staticEntity;
    }

} // **** end DirtNormalTile class ****