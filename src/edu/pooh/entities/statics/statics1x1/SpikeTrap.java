package edu.pooh.entities.statics.statics1x1;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpikeTrap extends StaticEntity {

    private transient BufferedImage[] spikeTrapAnimation;
    private Rectangle walkableCollisionBounds;
    private boolean activate;

    public SpikeTrap(Handler handler, float x, float y) {
        super(handler, x, y, (2*Tile.TILE_WIDTH), (2*Tile.TILE_HEIGHT));
        setBoundsX(0);
        setBoundsY(0);
        setBoundsWidth(0);
        setBoundsHeight(0);

        walkableCollisionBounds = new Rectangle((int)x, (int)y+35, width, height-35);
        activate = false;

        initAnimations();
    } // **** end SpikeTrap(Handler, float, float) constructor ****

    public void initAnimations() {
        spikeTrapAnimation = Assets.spikeTrap;
    }

    private int trapIndex = 0;
    private int slowerDowner = 0;
    @Override
    public void tick() {
        if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(walkableCollisionBounds)) {
            activate = true;
        }

        if (activate && (trapIndex < 31)) {
            if (slowerDowner % 10 == 0) {
                trapIndex++;
            }

            slowerDowner++;

            if (trapIndex == 31) {
                trapIndex = 0;
                slowerDowner = 0;
                activate = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentImage(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private BufferedImage getCurrentImage() {
        if (activate && (trapIndex < 31)) {
            return spikeTrapAnimation[trapIndex];
        } else {
            return spikeTrapAnimation[0];
        }
    }

    @Override
    public void hurt(int dmg) { return; }

    @Override
    public void die() {
        setActive(false);
    }

} // **** end SpikeTrap class ****