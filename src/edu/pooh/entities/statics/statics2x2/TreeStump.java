package edu.pooh.entities.statics.statics2x2;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.entities.statics.statics1x1.Bush;
import edu.pooh.entities.statics.statics1x1.Wood;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TreeStump extends StaticEntity {

    public TreeStump(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.treeStump2x2[0][0], (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        g.drawImage(Assets.treeStump2x2[0][1], (int)(x - handler.getGameCamera().getxOffset() + Tile.TILE_WIDTH),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        g.drawImage(Assets.treeStump2x2[1][0], (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset() + Tile.TILE_HEIGHT), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        g.drawImage(Assets.treeStump2x2[1][1], (int)(x - handler.getGameCamera().getxOffset() + Tile.TILE_WIDTH),
                (int)(y - handler.getGameCamera().getyOffset() + Tile.TILE_HEIGHT), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
    }

    @Override
    public void die() {

    }

} // **** end TreeStump class ****