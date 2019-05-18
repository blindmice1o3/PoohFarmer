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

public class Pikachu extends Creature {

    private Map<String, Animation> animations;
    private Animation[] animationsArray;

    private Random random;
    private int randomInt;

    public Pikachu(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        //initialize animations HashMap.
        initAnimations();
        //initialize animationsArray array.
        animationsArray = new Animation[animations.values().size()];
        animations.values().toArray(animationsArray);

        setSpeed(5);

        random = new Random();
    } // **** end Pikachu(Handler, float, float) constructor ****

    public void initAnimations() {
        animations = new HashMap<String, Animation>();

        animations.put("pikachuWalkSW", new Animation(400, Assets.pikachuWalkSW));
        animations.put("pikachuWalkNW", new Animation(400, Assets.pikachuWalkNW));
        animations.put("pikachuWalkNE", new Animation(400, Assets.pikachuWalkNE));
        animations.put("pikachuWalkSE", new Animation(400, Assets.pikachuWalkSE));

        animations.put("pikachuRunSW", new Animation(400, Assets.pikachuRunSW));
        animations.put("pikachuRunNW", new Animation(400, Assets.pikachuRunNW));
        animations.put("pikachuRunNE", new Animation(400, Assets.pikachuRunNE));
        animations.put("pikachuRunSE", new Animation(400, Assets.pikachuRunSE));

        animations.put("pikachuAttackRegSW", new Animation(400, Assets.pikachuAttackRegSW));
        animations.put("pikachuAttackRegNW", new Animation(400, Assets.pikachuAttackRegNW));
        animations.put("pikachuAttackRegNE", new Animation(400, Assets.pikachuAttackRegNE));
        animations.put("pikachuAttackRegSE", new Animation(400, Assets.pikachuAttackRegSE));

        animations.put("pikachuAttackSpecialSW", new Animation(400, Assets.pikachuAttackSpecialSW));
        animations.put("pikachuAttackSpecialNW", new Animation(400, Assets.pikachuAttackSpecialNW));
        animations.put("pikachuAttackSpecialNE", new Animation(400, Assets.pikachuAttackSpecialNE));
        animations.put("pikachuAttackSpecialSE", new Animation(400, Assets.pikachuAttackSpecialSE));
    }

    @Override
    public void tick() {
        for (Animation animation : animations.values()) {
            animation.tick();
        }

        randomlyMove();
        move();
    }

    public void randomlyMove() {
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
        if (xMove < 0) {                                // Moving left.
            return animations.get("pikachuWalkSW").getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return animations.get("pikachuWalkNW").getCurrentFrame();
        } else if (yMove < 0) {                         // Moving up.
            return animations.get("pikachuWalkNE").getCurrentFrame();
        } else if (yMove > 0) {                         // Moving down.
            return animations.get("pikachuWalkSE").getCurrentFrame();
        } else {
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

} // **** end Pikachu class ****