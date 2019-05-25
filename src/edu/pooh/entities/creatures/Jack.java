package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Jack extends Creature {

    private Animation animWalkingUp;
    private Animation animWalkingDown;
    private Animation animWalkingLeft;
    private Animation animWalkingRight;

    private Animation animRunningUp;
    private Animation animRunningDown;
    private Animation animRunningLeft;
    private Animation animRunningRight;

    private Random random;

    public Jack(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        setBoundsX(1);
        setBoundsY(1);
        setBoundsWidth(62);
        setBoundsHeight(62);

        animWalkingUp = new Animation(400, Assets.jackWalkingUp);
        animWalkingDown = new Animation(400, Assets.jackWalkingDown);
        animWalkingLeft = new Animation(400, Assets.jackWalkingLeft);
        animWalkingRight = new Animation(400, Assets.jackWalkingRight);

        animRunningUp = new Animation(400, Assets.jackRunningUp);
        animRunningDown = new Animation(400, Assets.jackRunningDown);
        animRunningLeft = new Animation(400, Assets.jackRunningLeft);
        animRunningRight = new Animation(400, Assets.jackRunningRight);

        random = new Random();
    } // **** end Jack(Handler, float, float) constructor ****

    @Override
    public void tick() {
        animWalkingUp.tick();
        animWalkingDown.tick();
        animWalkingLeft.tick();
        animWalkingRight.tick();

        animRunningUp.tick();
        animRunningDown.tick();
        animRunningLeft.tick();
        animRunningRight.tick();

        randomlyMove();
        move();
    }

    private void randomlyMove() {
        if (random.nextInt(100) == 1) {
            int moveDir = random.nextInt(9);

            switch (moveDir) {
                case 0:
                    xMove = -1;
                    break;
                case 1:
                    xMove = 1;
                    break;
                case 2:
                    yMove = -1;
                    break;
                case 3:
                    yMove = 1;
                    break;
                case 4:
                    xMove = -2;
                    break;
                case 5:
                    xMove = 2;
                    break;
                case 6:
                    yMove = -2;
                    break;
                case 7:
                    yMove = 2;
                    break;
                default:
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
            if (xMove == -1) {   // Walk or run.
                return animWalkingLeft.getCurrentFrame();
            } else {
                return animRunningLeft.getCurrentFrame();
            }
        } else if (xMove > 0) {                         // Moving right.
            if (xMove == 1) {   // Walk or run.
                return animWalkingRight.getCurrentFrame();
            } else {
                return animRunningRight.getCurrentFrame();
            }
        } else if (yMove < 0) {                         // Moving up.
            if (yMove == -1) {  // Walk or run.
                return animWalkingUp.getCurrentFrame();
            } else {
                return animRunningUp.getCurrentFrame();
            }
        } else if (yMove > 0) {                         // Moving down.
            if (yMove == 1) {   // Walk or run.
                return animWalkingDown.getCurrentFrame();
            } else {
                return animRunningDown.getCurrentFrame();
            }
        } else {
            return Assets.jackWalkingDown[0];
        }
    }

    @Override
    public void hurt(int amt) { return; }

    @Override
    public void die() {
        setActive(false);
    }

} // **** end Jack class ****