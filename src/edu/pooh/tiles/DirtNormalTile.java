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

    private StaticEntity staticEntity;

    public DirtNormalTile(int x, int y) {
        super(Assets.dirtNormal, 0);
        this.x = x;
        this.y = y;
        dirtState = DirtState.NORMAL;

        staticEntity = null;
    } // **** end DirtNormalTile(int, int) constructor ****

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
    public void checkRemoveFragmentedStaticEntity() {
        if (checkFragmentedStaticEntity()) {
            ((HarvestEntity)staticEntity).fragmentedTimer(HarvestEntity.FRAGMENTEDTIMELIMIT);
            setStaticEntity(null);
        }
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);

        if (staticEntity != null) {
            staticEntity.render(g);
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

    public StaticEntity getStaticEntity() {
        return staticEntity;
    }

    public void setStaticEntity(StaticEntity staticEntity) {
        this.staticEntity = staticEntity;
    }

} // **** end DirtNormalTile class ****