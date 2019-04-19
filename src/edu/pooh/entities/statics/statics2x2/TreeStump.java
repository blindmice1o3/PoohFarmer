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

    private BufferedImage[][] texture;
    private Bush wood0, wood1, wood2, wood3;   // Bush: non-Holdable StaticEntity that can hurt(int dmg).

    public TreeStump(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);

        texture = Assets.treeStump2x2;

        wood0 = new Bush(handler, x, y) {
            @Override
            public void render(Graphics g) {
                g.drawImage(texture[0][0], (int)(x - handler.getGameCamera().getxOffset()),
                        (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
            }
            @Override
            public void die() {
                Wood tempWood0 = new Wood(handler, x, y);

                ((DirtNormalTile)handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH),
                        (int)(y / Tile.TILE_HEIGHT))).setStaticEntity( tempWood0 );
                handler.getWorld().getEntityManager().addEntity( tempWood0 );

                setActive(false);
            }
        };
        wood1 = new Bush(handler, (x + Tile.TILE_WIDTH), y) {
            @Override
            public void render(Graphics g) {
                g.drawImage(texture[0][1], (int)(x - handler.getGameCamera().getxOffset()),
                        (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
            }
            @Override
            public void die() {
                Wood tempWood1 = new Wood(handler, x, y);

                ((DirtNormalTile)handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH),
                        (int)(y / Tile.TILE_HEIGHT))).setStaticEntity( tempWood1 );
                handler.getWorld().getEntityManager().addEntity( tempWood1 );

                setActive(false);
            }
        };
        wood2 = new Bush(handler, x, (y + Tile.TILE_HEIGHT)) {
            @Override
            public void render(Graphics g) {
                g.drawImage(texture[1][0], (int)(x - handler.getGameCamera().getxOffset()),
                        (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
            }
            @Override
            public void die() {
                Wood tempWood2 = new Wood(handler, x, y);

                ((DirtNormalTile)handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH),
                        (int)(y / Tile.TILE_HEIGHT))).setStaticEntity( tempWood2 );
                handler.getWorld().getEntityManager().addEntity( tempWood2 );

                setActive(false);
            }
        };
        wood3 = new Bush(handler, (x + Tile.TILE_WIDTH), (y + Tile.TILE_HEIGHT)) {
            @Override
            public void render(Graphics g) {
                g.drawImage(texture[1][1], (int)(x - handler.getGameCamera().getxOffset()),
                        (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
            }
            @Override
            public void die() {
                Wood tempWood3 = new Wood(handler, x, y);

                ((DirtNormalTile)handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH),
                        (int)(y / Tile.TILE_HEIGHT))).setStaticEntity( tempWood3 );
                handler.getWorld().getEntityManager().addEntity( tempWood3 );

                setActive(false);
            }
        };
        ((DirtNormalTile)handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT))).setStaticEntity(wood0);
        ((DirtNormalTile)handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH) + 1, (int)(y / Tile.TILE_HEIGHT))).setStaticEntity(wood1);
        ((DirtNormalTile)handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH), (int)(y / Tile.TILE_HEIGHT) + 1)).setStaticEntity(wood2);
        ((DirtNormalTile)handler.getWorld().getTile((int)(x / Tile.TILE_WIDTH) + 1, (int)(y / Tile.TILE_HEIGHT) + 1)).setStaticEntity(wood3);

    } // **** end TreeStump(Handler, float, float, int, int) constructor ****

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void die() {
        wood0.die();
        wood1.die();
        wood2.die();
        wood3.die();

        setActive(false);
    }

} // **** end TreeStump class ****