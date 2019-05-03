package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Dog extends Creature implements IHoldable {

    private Animation animUp;
    private Animation animDown;
    private Animation animLeft;
    private Animation animRight;

    private Random random;
    private boolean pickedUp;

    public Dog(Handler handler, float x, float y) {
        super(handler, (x + (Tile.TILE_WIDTH/4)), (y + (Tile.TILE_HEIGHT/4)),
                (Tile.TILE_WIDTH / 2), (Tile.TILE_HEIGHT / 2));

        animUp = new Animation(400, Assets.dogUp);
        animDown = new Animation(400, Assets.dogDown);
        animLeft = new Animation(400, Assets.dogLeft);
        animRight = new Animation(400, Assets.dogRight);

        random = new Random();
        pickedUp = false;
    } // **** end Dog(Handler, float, float) constructor ****

    @Override
    public void tick() {
        if (!pickedUp) {
            animUp.tick();
            animDown.tick();
            animLeft.tick();
            animRight.tick();

            randomlyMove();
            move();
        }
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
            return Assets.dogDown[0];
        }
    }

    @Override
    public void hurt(int amt) { return; }

    @Override
    public void die() {
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
        pickedUp = true;
    }

    @Override
    public void dropped(Tile t) {
        Tile[][] tempTiles = handler.getWorld().getTilesViaRGB();
        for (int xx = 0; xx < tempTiles.length; xx++) {
            for (int yy = 0; yy < tempTiles[xx].length; yy++) {
                if (tempTiles[xx][yy] == t) {
                    x = xx * Tile.TILE_WIDTH;
                    y = yy * Tile.TILE_HEIGHT;

                    System.out.println("dropped(Tile) method successfully called.");
                }
            }
        }


        pickedUp = false;
    }

} // **** end Dog class ****