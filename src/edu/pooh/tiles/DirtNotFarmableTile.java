package edu.pooh.tiles;

import edu.pooh.entities.statics.StaticEntity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DirtNotFarmableTile extends Tile {

    private int x, y;
    private StaticEntity staticEntity;

    public DirtNotFarmableTile(int x, int y, BufferedImage texture) {
        super(texture,98);
        this.x = x;
        this.y = y;

        staticEntity = null;
    } // **** end DirtNotFarmableTile(int, int) constructor ****

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

} // **** end DirtNotFarmableTile class ****