package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

// TODO: Implement IHoldable interface. Also, Player class's render() will have to sync with Dog class's being-held-images.
public class Dog extends Creature {

    private Animation animUp;
    private Animation animDown;
    private Animation animLeft;
    private Animation animRight;
    private Animation animLeftPee;

    private Random random;

    public Dog(Handler handler, float x, float y) {
        super(handler, (x + (Tile.TILE_WIDTH/4)), (y + (Tile.TILE_HEIGHT/4)), (Tile.TILE_WIDTH / 2), (Tile.TILE_HEIGHT / 2));

        animUp = new Animation(400, Assets.dogUp);
        animDown = new Animation(400, Assets.dogDown);
        animLeft = new Animation(400, Assets.dogLeft);
        animRight = new Animation(400, Assets.dogRight);
        animLeftPee = new Animation(400, Assets.dogLeftPee);

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