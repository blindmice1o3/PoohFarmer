package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TravelingFence extends Creature {

    // The Finn - a fence for stolen goods and one of Molly's old friends. (-William Gibson's Sprawl Trilogy)
    private String name;

    // ANIMATIONS
    private Animation animUp, animDown, animLeft, animRight;
    private Random random;

    public TravelingFence(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        name = "The Finn";

        // ANIMATIONS
        animUp = new Animation(60, Assets.hawkerUp);
        animDown = new Animation(60, Assets.hawkerDown);
        animLeft = new Animation(60, Assets.hawkerLeft);
        animRight = new Animation(60, Assets.hawkerRight);

        random = new Random();
    } // **** end TravelingFence(Handler, float, float) constructor ****

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // ANIMATIONS
        animUp.tick();
        animDown.tick();
        animLeft.tick();
        animRight.tick();

        // STARTING MOVEMENTS
        if (x < 445) {
            setxMove(1);
            move();
        } else if (y > 1220) {
            setyMove(-1);
            move();
        } else {

            if ((random.nextInt(100)) <= 1) {
                switch (random.nextInt(4)) {
                    case 0:
                        setCurrentDirection(DirectionFacing.DOWN);
                        break;
                    case 1:
                        setCurrentDirection(DirectionFacing.LEFT);
                        break;
                    case 2:
                        setCurrentDirection(DirectionFacing.UP);
                        break;
                    case 3:
                        setCurrentDirection(DirectionFacing.RIGHT);
                        break;
                    default:
                        setCurrentDirection(DirectionFacing.DOWN);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
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
        }

        // NON-ANIMATION for NO-INPUT DIRECTIONS
        switch (currentDirection) {
            case LEFT:
                return Assets.hawkerLeft[0];
            case RIGHT:
                return Assets.hawkerRight[0];
            case UP:
                return Assets.hawkerUp[0];
            case DOWN:
                return Assets.hawkerDown[0];
            default:
                return Assets.hawkerDown[0];
        }

    }

    @Override
    public void hurt(int amt) {
        return;
    }

    @Override
    public void die() {
        setActive(false);
    }

    // GETTERS & SETTERS

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

} // **** end TravelingFence class ****