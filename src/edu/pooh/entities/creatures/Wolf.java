package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Wolf extends Creature {

    private transient Animation animRight;
    private transient Animation animLeft;

    private Random random;

    public Wolf(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        setBoundsX(5);
        setBoundsY(5);
        setBoundsWidth(width-10);
        setBoundsHeight(height-10);

        initAnimations();

        setSpeed(6);

        random = new Random();
    } // **** end Wolf(Handler, float, float) constructor ****

    @Override
    public void initAnimations() {
        animRight = new Animation(200, Assets.wolfRight);
        animLeft = new Animation(200, Assets.wolfLeft);
    }

    @Override
    public void tick() {
        animRight.tick();
        animLeft.tick();

        randomlyMove();
        move();
    }

    private void randomlyMove() {
        if (random.nextInt(100) == 1) {
            int moveDir = random.nextInt(2);

            switch (moveDir) {
                case 0:
                    xMove = -1;
                    break;
                case 1:
                    xMove = 1;
                    break;
                default:    //should never end up here (no standing still/idle image).
                    xMove = 0;
                    yMove = 0;
                    break;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
        // ANIMATION MOVEMENTS
        if (xMove < 0) {                                // Moving left.
            return animLeft.getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return animRight.getCurrentFrame();
        } else {        //should never end up here (no standing still/idle image).
            return Assets.wolfRight[0];
        }
    }

    @Override
    public void hurt(int dmg) {
        return;
    }

    @Override
    public void die() {
        setActive(false);
    }

} // **** end Wolf class ****