package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Golum extends Creature {

    private transient Map<String, Animation> animations;
    private transient Animation[] animationsArray;

    private Random random;
    private int randomInt;

    public Golum(Handler handler, float x, float y) {
        super(handler, x, y, (int)(1.75*Tile.TILE_WIDTH), (int)(1.75*Tile.TILE_HEIGHT));

        initAnimations();

        setSpeed(5);

        random = new Random();
    } // **** end Golum(Handler, float, float) constructor ****

    @Override
    public void initAnimations() {
        //initialize animations HashMap.
        animations = new HashMap<String, Animation>();

        animations.put("animWalkDown", new Animation(300, Assets.golumWalkDown));
        animations.put("animWalkDownLeft", new Animation(300, Assets.golumWalkDownLeft));
        animations.put("animWalkLeft", new Animation(300, Assets.golumWalkLeft));
        animations.put("animWalkUpLeft", new Animation(300, Assets.golumWalkUpLeft));
        animations.put("animWalkUp", new Animation(300, Assets.golumWalkUp));
        animations.put("animWalkUpRight", new Animation(300, Assets.golumWalkUpRight));
        animations.put("animWalkRight", new Animation(300, Assets.golumWalkRight));
        animations.put("animWalkDownRight", new Animation(300, Assets.golumWalkDownRight));

        //initialize animationsArray array.
        animationsArray = new Animation[animations.values().size()];
        animations.values().toArray(animationsArray);
    }

    @Override
    public void tick() {
        for (Animation animation : animations.values()) {
            animation.tick();
        }

        randomlyMove();
        move();
    }

    private void randomlyMove() {
        if (random.nextInt(100) == 1) {
            int moveDir = random.nextInt( 9);

            switch (moveDir) {
                case 0:
                    yMove = 1;
                    break;
                case 1:
                    yMove = 1;
                    xMove = -1;
                    break;
                case 2:
                    xMove = -1;
                    break;
                case 3:
                    yMove = -1;
                    xMove = -1;
                    break;
                case 4:
                    yMove = -1;
                    break;
                case 5:
                    yMove = -1;
                    xMove = 1;
                    break;
                case 6:
                    xMove = 1;
                    break;
                case 7:
                    yMove = 1;
                    xMove = 1;
                    break;
                default:
                    xMove = 0;
                    yMove = 0;
                    randomInt = random.nextInt(animations.keySet().size());
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
        if ((yMove > 0) && (xMove < 0)) {               // Moving down-left.
            return animations.get("animWalkDownLeft").getCurrentFrame();
        } else if ((yMove < 0) && (xMove < 0)) {        // Moving up-left.
            return animations.get("animWalkUpLeft").getCurrentFrame();
        } else if ((yMove < 0) && (xMove > 0)) {        // Moving up-right.
            return animations.get("animWalkUpRight").getCurrentFrame();
        } else if ((yMove > 0) && (xMove > 0)) {        // Moving down-right.
            return animations.get("animWalkDownRight").getCurrentFrame();
        } else if (xMove < 0) {                         // Moving left.
            return animations.get("animWalkLeft").getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return animations.get("animWalkRight").getCurrentFrame();
        } else if (yMove < 0) {                         // Moving up.
            return animations.get("animWalkUp").getCurrentFrame();
        } else if (yMove > 0) {                         // Moving down.
            return animations.get("animWalkDown").getCurrentFrame();
        } else {
            return animationsArray[randomInt].getCurrentFrame();
        }
    }

    @Override
    public void hurt(int dmg) { return; }

    @Override
    public void die() {
        setActive(false);
    }

} // **** end Golum class ****