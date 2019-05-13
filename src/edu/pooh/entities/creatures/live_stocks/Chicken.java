package edu.pooh.entities.creatures.live_stocks;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Chicken extends Creature {

    public enum ChickenState { CHICK, ADULT_EGG_LAYING, ADULT_GRUMPY_1, ADULT_GRUMPY_2, ADULT_GRUMPY_3; }

    private Animation animUp;
    private Animation animDown;
    private Animation animLeft;
    private Animation animRight;
    private Animation animChickUp;
    private Animation animChickDown;
    private Animation animChickLeft;
    private Animation animChickRight;

    private Random random;
    private int daysInstantiated;
    private ChickenState chickenState;

    public void incrementChickenStateByDaysInstantiated() {
        switch (daysInstantiated) {
            case 0:
            case 1:
            case 2:
                chickenState = ChickenState.CHICK;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                chickenState = ChickenState.ADULT_EGG_LAYING;
                break;
            default:
                System.out.println("Chicken.incrementChickenStateByDaysInstanted() switch-construct's default statement.");
                break;
        }
    }

    public Chicken(Handler handler, float x, float y, ChickenState chickenState) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        animUp = new Animation(400, Assets.chickenUp);
        animDown = new Animation(400, Assets.chickenDown);
        animLeft = new Animation(400, Assets.chickenLeft);
        animRight = new Animation(400, Assets.chickenRight);
        animChickUp = new Animation(400, Assets.chickUp);
        animChickDown = new Animation(400, Assets.chickDown);
        animChickLeft = new Animation(400, Assets.chickLeft);
        animChickRight = new Animation(400, Assets.chickRight);

        setSpeed(4);

        random = new Random();
        daysInstantiated = 0;
        this.chickenState = chickenState;
    } // **** end Chicken(Handler, float, float) constructor ****

    @Override
    public void tick() {
        animUp.tick();
        animDown.tick();
        animLeft.tick();
        animRight.tick();
        animChickUp.tick();
        animChickDown.tick();
        animChickLeft.tick();
        animChickRight.tick();

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
        if ((xMove < 0) && (chickenState != ChickenState.CHICK)) {                                // Moving left.
            return animLeft.getCurrentFrame();
        } else if ((xMove > 0) && (chickenState != ChickenState.CHICK)) {                         // Moving right.
            return animRight.getCurrentFrame();
        } else if ((yMove < 0) && (chickenState != ChickenState.CHICK)) {                         // Moving up.
            return animUp.getCurrentFrame();
        } else if ((yMove > 0) && (chickenState != ChickenState.CHICK)) {                         // Moving down.
            return animDown.getCurrentFrame();
        } else if ((xMove < 0) && (chickenState == ChickenState.CHICK)) {
            return animChickLeft.getCurrentFrame();
        } else if ((xMove > 0) && (chickenState == ChickenState.CHICK)) {
            return animChickRight.getCurrentFrame();
        } else if ((yMove < 0) && (chickenState == ChickenState.CHICK)) {
            return animChickUp.getCurrentFrame();
        } else if ((yMove > 0) && (chickenState == ChickenState.CHICK)) {
            return animChickDown.getCurrentFrame();
        } else if ((xMove == 0) && (yMove == 0) & (chickenState == ChickenState.CHICK)){
            return Assets.chickDown[0];
        } else {
            return Assets.chickenDown[0];
        }
    }

    @Override
    public void hurt(int amt) { return; }

    @Override
    public void die() {
        setActive(false);
    }

    public void setChickenState(ChickenState chickenState) { this.chickenState = chickenState; }
    public ChickenState getChickenState() { return chickenState; }

    public int getDaysInstantiated() { return daysInstantiated; }
    public void setDaysInstantiated(int daysInstantiated) { this.daysInstantiated = daysInstantiated; }
    public void increaseDaysInstantiated() {
        daysInstantiated++;
    }

} // **** end Chicken class ****