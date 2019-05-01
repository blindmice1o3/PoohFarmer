package edu.pooh.entities.statics.statics1x1;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtMountainTile;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class Bush extends StaticEntity {

    public Bush(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        bounds.x = 5;
        bounds.y = 5;
        setBoundsWidth(width - 10);
        setBoundsHeight(height - 10);
    } // **** end Bush(Handler, float, float) constructor ****

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.plantFlowering1, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);

        //g.setColor(Color.RED);
        //g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
        //        (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
        //        bounds.width, bounds.height);
    }

    @Override
    public void die() {
        for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
            for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
                if (handler.getWorld().getTile(xx, yy) instanceof DirtNormalTile) {
                    if (((DirtNormalTile)handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {
                        ((DirtNormalTile)handler.getWorld().getTile(xx, yy)).setStaticEntity(null);
                    }
                } else if (handler.getWorld().getTile(xx, yy) instanceof DirtMountainTile) {
                    if (((DirtMountainTile)handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {
                        ((DirtMountainTile)handler.getWorld().getTile(xx, yy)).setStaticEntity(null);
                    }
                }
            }
        }

        setActive(false);
        //handler.getWorld().getItemManager().addItem(Item.shovelItem.createNew((int)x, (int)y));
    }

} // **** end Bush class ****