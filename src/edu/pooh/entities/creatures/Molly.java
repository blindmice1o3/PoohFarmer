package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Molly extends Creature {

    private Animation animWalkingUp;
    private Animation animWalkingDown;
    private Animation animWalkingLeft;
    private Animation animWalkingRight;

    private Random random;

    public Molly(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        animWalkingUp = new Animation(400, Assets.mollyWalkingUp);
        animWalkingDown = new Animation(400, Assets.mollyWalkingDown);
        animWalkingLeft = new Animation(400, Assets.mollyWalkingLeft);
        animWalkingRight = new Animation(400, Assets.mollyWalkingRight);

        random = new Random();
    } // **** end Molly(Handler, float, float) constructor ****

    @Override
    public void tick() {
        animWalkingUp.tick();
        animWalkingDown.tick();
        animWalkingLeft.tick();
        animWalkingRight.tick();

        randomlyMove();
        move();
    }

    private void randomlyMove() {
        if (random.nextInt(100) == 1) {
            int moveDir = random.nextInt(5);

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
            return animWalkingLeft.getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return animWalkingRight.getCurrentFrame();
        } else if (yMove < 0) {                         // Moving up.
            return animWalkingUp.getCurrentFrame();
        } else if (yMove > 0) {                         // Moving down.
            return animWalkingDown.getCurrentFrame();
        } else {
            return Assets.mollyWalkingDown[0];
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

} // **** end Molly class ****