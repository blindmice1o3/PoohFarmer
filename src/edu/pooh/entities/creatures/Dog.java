package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Dog extends Creature {

    private Animation animUp;
    private Animation animDown;
    private Animation animLeft;
    private Animation animRight;
    private Animation animLeftPee;

    private BufferedImage currentImage;
    private Random random;

    public Dog(Handler handler, float x, float y) {
        super(handler, (x + (Tile.TILE_WIDTH/4)), (y + (Tile.TILE_HEIGHT/4)), (Tile.TILE_WIDTH / 2), (Tile.TILE_HEIGHT / 2));

        animUp = new Animation(400, Assets.dogUp);
        animDown = new Animation(400, Assets.dogDown);
        animLeft = new Animation(400, Assets.dogLeft);
        animRight = new Animation(400, Assets.dogRight);
        animLeftPee = new Animation(400, Assets.dogLeftPee);

        currentImage = animDown.getCurrentFrame();
        random = new Random();
    } //  **** end Dog(Handler, float, float) constructor ****

    @Override
    public void tick() {
        animUp.tick();
        animDown.tick();
        animLeft.tick();
        animRight.tick();
        animLeftPee.tick();

        randomlyMove();
    }

    private void randomlyMove() {


        if (random.nextInt(1000) < 4) {
            int moveDir = random.nextInt(4);

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

            move();
        }
    }


    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        if (xMove > 0) {
            g2d.drawImage(getCurrentAnimationFrame(), AffineTransform.getRotateInstance(180), null);
        //    g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
        //            (int) (y - handler.getGameCamera().getyOffset()), width, height,
        //            getCurrentAnimationFrame().getWidth(), 0, -getCurrentAnimationFrame().getWidth(),
        //            getCurrentAnimationFrame().getHeight(), null);
        } else {
            g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    private BufferedImage getCurrentAnimationFrame() {
        // ANIMATION MOVEMENTS
        if (xMove < 0) {                                // Moving left.
            return animLeft.getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return animRight.getCurrentFrame();
        } else if (yMove < 0) {                         // Moving up.
            return animUp.getCurrentFrame();
        } else if (yMove > 0) {                         // Moving down.
            return animDown.getCurrentFrame();
        } else {
            return animLeftPee.getCurrentFrame();
        }
    }

    @Override
    public void hurt(int amt) { return; }

    @Override
    public void die() {
        setActive(false);
    }

} //  **** end Dog class ****