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

public class Molly extends Creature {

    private Map<String, Animation> animations;
    Animation[] animationsArray;
/*
    private Animation animWalkingUp, animWalkingDown, animWalkingLeft, animWalkingRight,
    animHoldingUp, animHoldingDown, animHoldingLeft, animHoldingRight,
    animTakeUp, animTakeDown, animTakeLeft, animTakeRight,
    animPatBrow, animPantingSweating, animStumble, animCollapse,
    animEating, animSeeds,
    animRidingHorseUp, animRidingHorseDown, animRidingHorseLeft, animRidingHorseRight,
    animMountingHorseUp, animMountingHorseDown, animMountingHorseLeft, animMountingHorseRight;
*/
    private Random random;
    private int randomInt;

    public Molly(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        //initialize animations HashMap.
        initAnimations();
        //initialize animationsArray array.
        animationsArray = new Animation[animations.values().size()];
        animations.values().toArray(animationsArray);

        random = new Random();
    } // **** end Molly(Handler, float, float) constructor ****

    private void initAnimations() {
        animations = new HashMap<String, Animation>();

        animations.put("animWalkingUp", new Animation(400, Assets.mollyWalkingUp));
        animations.put("animWalkingDown", new Animation(400, Assets.mollyWalkingDown));
        animations.put("animWalkingLeft", new Animation(400, Assets.mollyWalkingLeft));
        animations.put("animWalkingRight", new Animation(400, Assets.mollyWalkingRight));

        animations.put("animHoldingUp", new Animation(400, Assets.mollyHoldingUp));
        animations.put("animHoldingDown", new Animation(400, Assets.mollyHoldingDown));
        animations.put("animHoldingLeft", new Animation(400, Assets.mollyHoldingLeft));
        animations.put("animHoldingRight", new Animation(400, Assets.mollyHoldingRight));

        animations.put("animTakeUp", new Animation(400, Assets.mollyTakeUp));
        animations.put("animTakeDown", new Animation(400, Assets.mollyTakeDown));
        animations.put("animTakeLeft", new Animation(400, Assets.mollyTakeLeft));
        animations.put("animTakeRight", new Animation(400, Assets.mollyTakeRight));

        animations.put("animPatBrow", new Animation(400, Assets.mollyPatBrow));
        animations.put("animPantingSweating", new Animation(400, Assets.mollyPantingSweating));
        animations.put("animStumble", new Animation(400, Assets.mollyStumble));
        animations.put("animCollapse", new Animation(400, Assets.mollyCollapse));

        animations.put("animEating", new Animation(400, Assets.mollyEating));
        animations.put("animSeeds", new Animation(400, Assets.mollySeeds));

        animations.put("animRidingHorseUp", new Animation(400, Assets.mollyRidingHorseUp));
        animations.put("animRidingHorseDown", new Animation(400, Assets.mollyRidingHorseDown));
        animations.put("animRidingHorseLeft", new Animation(400, Assets.mollyRidingHorseLeft));
        animations.put("animRidingHorseRight", new Animation(400, Assets.mollyRidingHorseRight));

        animations.put("animMountingHorseUp", new Animation(400, Assets.mollyMountingHorseUp));
        animations.put("animMountingHorseDown", new Animation(400, Assets.mollyMountingHorseDown));
        animations.put("animMountingHorseLeft", new Animation(400, Assets.mollyMountingHorseLeft));
        animations.put("animMountingHorseRight", new Animation(400, Assets.mollyMountingHorseRight));
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
            int moveDir = random.nextInt( 5);

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
        ////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////

        // ANIMATION MOVEMENTS
        if (xMove < 0) {                                // Moving left.
            return animations.get("animWalkingLeft").getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return animations.get("animWalkingRight").getCurrentFrame();
        } else if (yMove < 0) {                         // Moving up.
            return animations.get("animWalkingUp").getCurrentFrame();
        } else if (yMove > 0) {                         // Moving down.
            return animations.get("animWalkingDown").getCurrentFrame();
        } else {                                        //
            return animationsArray[randomInt].getCurrentFrame();
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