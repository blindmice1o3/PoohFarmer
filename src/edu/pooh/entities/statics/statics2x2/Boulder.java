package edu.pooh.entities.statics.statics2x2;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNotFarmableTile;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class Boulder extends StaticEntity {

    public Boulder(Handler handler, float x, float y) {
        super(handler, x, y, (2*Tile.TILE_WIDTH), (2*Tile.TILE_HEIGHT));
    } // **** end Boulder(Handler, float, float) constructor ****

    @Override
    public void tick() {
        return;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.boulder2x2[0][0], (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        g.drawImage(Assets.boulder2x2[0][1], (int)(x - handler.getGameCamera().getxOffset() + Tile.TILE_WIDTH),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        g.drawImage(Assets.boulder2x2[1][0], (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset() + Tile.TILE_HEIGHT), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        g.drawImage(Assets.boulder2x2[1][1], (int)(x - handler.getGameCamera().getxOffset() + Tile.TILE_WIDTH),
                (int)(y - handler.getGameCamera().getyOffset() + Tile.TILE_HEIGHT), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
    }

    @Override
    public void die() {
        if (handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT)) instanceof DirtNormalTile) {
            if (((DirtNormalTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT))).getStaticEntity() == this) {
                ((DirtNormalTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT))).setStaticEntity(null);
                ((DirtNormalTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH)+1, (int)(y / Tile.TILE_HEIGHT))).setStaticEntity(null);
                ((DirtNormalTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT)+1)).setStaticEntity(null);
                ((DirtNormalTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH)+1, (int)(y / Tile.TILE_HEIGHT)+1)).setStaticEntity(null);
            }
        } else if (handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT)) instanceof DirtNotFarmableTile) {
            if (((DirtNotFarmableTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT))).getStaticEntity() == this) {
                ((DirtNotFarmableTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT))).setStaticEntity(null);
                ((DirtNotFarmableTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH)+1, (int)(y / Tile.TILE_HEIGHT))).setStaticEntity(null);
                ((DirtNotFarmableTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT)+1)).setStaticEntity(null);
                ((DirtNotFarmableTile) handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH)+1, (int)(y / Tile.TILE_HEIGHT)+1)).setStaticEntity(null);
            }
        }

        setActive(false);
    }

    /*
    @Override
    public void hurt(int dmg) {
        return;
    }
     */

} // **** end Boulder class ****