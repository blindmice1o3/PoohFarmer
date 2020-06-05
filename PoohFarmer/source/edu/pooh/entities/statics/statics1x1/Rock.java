package edu.pooh.entities.statics.statics1x1;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.tiles.DirtNotFarmableTile;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class Rock extends StaticEntity
        implements IHoldable {

    public Rock(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        bounds.x = 5;
        bounds.y = 10;
        setBoundsWidth(width - 10);
        setBoundsHeight(height - 20);
    } // **** end Rock(Handler, float, float) constructor ****

    @Override
    public void tick() {
        return;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.rock, (int)(x - handler.getGameCamera().getxOffset()),
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
                } else if (handler.getWorld().getTile(xx, yy) instanceof DirtNotFarmableTile) {
                    if (((DirtNotFarmableTile)handler.getWorld().getTile(xx, yy)).getStaticEntity() == this) {
                        ((DirtNotFarmableTile)handler.getWorld().getTile(xx, yy)).setStaticEntity(null);
                    }
                }
            }
        }

        setActive(false);
    }

    // IHOLDABLE INTERFACE

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
        if (t instanceof DirtNormalTile) {  //DirtNormalTile
            DirtNormalTile tempTile = (DirtNormalTile)t;

            //If a rock is dropped on to a DirtNormalTile object, it calls
            // its staticEntity's die() method (unless it's in package statics2x2).
            if (tempTile.getStaticEntity() != null) {
                tempTile.getStaticEntity().die();
            }

            System.out.println("dropped DirtNormalTile's (x, y): (" + x + ", " + y + ")");
            x = tempTile.getX() * Tile.TILE_WIDTH;
            y = tempTile.getY() * Tile.TILE_HEIGHT;
            tempTile.setStaticEntity(this);

            //If a rock is dropped on to a DirtNormalTile object, it changes its DirtState to NORMAL.
            ////////////////////////////////////////////////////////
            tempTile.setDirtState(DirtNormalTile.DirtState.NORMAL);
            //tempTile.setTexture(Assets.dirtNormal);
            /////////////////////////////////////////////////////////
        }
        else if (t instanceof DirtNotFarmableTile) {   //DirtNotFarmableTile
            DirtNotFarmableTile tempMountainTile = (DirtNotFarmableTile)t;

            if (tempMountainTile.getStaticEntity() != null) {
                return;
            }

            System.out.println("dropped DirtNotFarmableTile's (x, y): (" + x + ", " + y + ")");
            x = tempMountainTile.getX() * Tile.TILE_WIDTH;
            y = tempMountainTile.getY() * Tile.TILE_HEIGHT;
            tempMountainTile.setStaticEntity(this);
        }
        else {    //poolWater Tile
            if (t.getId() >= 236 && t.getId() <= 248) {
                die();
                System.out.println("dropped rock into poolWater's (x, y): (" + this.x + ", " + this.y + ")");
            }
        }
    }

} // **** end Rock class ****