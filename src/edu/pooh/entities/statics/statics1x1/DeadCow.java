package edu.pooh.entities.statics.statics1x1;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DeadCow extends StaticEntity {

    private boolean clicked;
    private BufferedImage before, after;
    private BufferedImage[] deadCowExplosion;

    public DeadCow(Handler handler, float x, float y) {
        super(handler, x, y, (3*Tile.TILE_WIDTH), (3*Tile.TILE_HEIGHT));
        setBoundsX(30);
        setBoundsY(90);
        setBoundsWidth(130);
        setBoundsHeight(80);

        clicked = false;
        initAnimations();
    } // **** end DeadCow(Handler, float, float) constructor ****

    private void initAnimations() {
        before = Assets.deadCowBefore;
        after = Assets.deadCowAfter;
        deadCowExplosion = Assets.deadCowExplosion;
    }

    private int explosionIndex = 0;
    private int slowerDowner = 0;
    @Override
    public void tick() {
        if (clicked && (explosionIndex < 22)) {
            if (slowerDowner % 5 == 0) {
                explosionIndex++;
            }
            slowerDowner++;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentImage(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    private BufferedImage getCurrentImage() {
        if (clicked && (explosionIndex < 22)) {
            return deadCowExplosion[explosionIndex];
        } else if (clicked && (explosionIndex == 22)) {
            return after;
        } else {
            return before;
        }
    }

    @Override
    public void hurt(int dmg) { return; }

    @Override
    public void die() {
        setActive(false);
    }

    // GETTERS AND SETTERS

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

} // **** end DeadCow class ****