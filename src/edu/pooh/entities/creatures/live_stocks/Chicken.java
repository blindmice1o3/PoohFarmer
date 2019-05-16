package edu.pooh.entities.creatures.live_stocks;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Chicken extends Creature
        implements IHoldable {

    public enum ChickenState { CHICK, JUVENILE_NON_EGG_LAYING, ADULT_EGG_LAYING,
        JUVENILE_NON_EGG_LAYING_GRUMPY_1, JUVENILE_NON_EGG_LAYING_GRUMPY_2, JUVENILE_NON_EGG_LAYING_GRUMPY_3,
        ADULT_GRUMPY_1, ADULT_GRUMPY_2, ADULT_GRUMPY_3; }

    private Map<String, Animation> anim;

    private Random random;
    private int daysInstantiated;
    private ChickenState chickenState;
    private boolean pickedUp;

    public Chicken(Handler handler, float x, float y, ChickenState chickenState) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        initChickenAnimations();

        setSpeed(4);

        random = new Random();
        daysInstantiated = 0;
        this.chickenState = chickenState;
        pickedUp = false;
    } // **** end Chicken(Handler, float, float) constructor ****

    private void initChickenAnimations() {
        anim = new HashMap<String, Animation>();

        anim.put("animChickenUp", new Animation(400, Assets.chickenUp));
        anim.put("animChickenDown", new Animation(400, Assets.chickenDown));
        anim.put("animChickenLeft", new Animation(400, Assets.chickenLeft));
        anim.put("animChickenRight", new Animation(400, Assets.chickenRight));
        anim.put("animChickUp", new Animation(400, Assets.chickUp));
        anim.put("animChickDown", new Animation(400, Assets.chickDown));
        anim.put("animChickLeft", new Animation(400, Assets.chickLeft));
        anim.put("animChickRight", new Animation(400, Assets.chickRight));
    }

    public void incrementChickenStateByDaysInstantiated() {
        switch (daysInstantiated) {
            case 0:
            case 1:
            case 2:
                chickenState = ChickenState.CHICK;
                System.out.println("Chicken.incrementChickenStateByDaysInstantiated() daysInstantiated == [0-2] ChickenState.CHICK.");
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                chickenState = ChickenState.JUVENILE_NON_EGG_LAYING;
                System.out.println("Chicken.incrementChickenStateByDaysInstantiated() daysInstantiated == [3-9] ChickenState.JUVENILE_NON_EGG_LAYING.");
                break;
            default:
                chickenState = ChickenState.ADULT_EGG_LAYING;
                System.out.println("Chicken.incrementChickenStateByDaysInstantiated() switch-construct's default statement ChickenState.ADULT_EGG_LAYING.");
                break;
        }
    }

    @Override
    public void tick() {
        if (!pickedUp) {
            for (Animation tempAnim : anim.values()) {
                tempAnim.tick();
            }

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
        if ((xMove < 0) && (chickenState != ChickenState.CHICK)) {                                // Moving left.
            return anim.get("animChickenLeft").getCurrentFrame();
        } else if ((xMove > 0) && (chickenState != ChickenState.CHICK)) {                         // Moving right.
            return anim.get("animChickenRight").getCurrentFrame();
        } else if ((yMove < 0) && (chickenState != ChickenState.CHICK)) {                         // Moving up.
            return anim.get("animChickenUp").getCurrentFrame();
        } else if ((yMove > 0) && (chickenState != ChickenState.CHICK)) {                         // Moving down.
            return anim.get("animChickenDown").getCurrentFrame();
        } else if ((xMove < 0) && (chickenState == ChickenState.CHICK)) {
            return anim.get("animChickLeft").getCurrentFrame();
        } else if ((xMove > 0) && (chickenState == ChickenState.CHICK)) {
            return anim.get("animChickRight").getCurrentFrame();
        } else if ((yMove < 0) && (chickenState == ChickenState.CHICK)) {
            return anim.get("animChickUp").getCurrentFrame();
        } else if ((yMove > 0) && (chickenState == ChickenState.CHICK)) {
            return anim.get("animChickDown").getCurrentFrame();
        } else if ((xMove == 0) && (yMove == 0) & (chickenState == ChickenState.CHICK)){
            return Assets.chickDown[0];
        } else {
            return Assets.chickenDown[0];
        }
    }

    public void setChickenState(ChickenState chickenState) { this.chickenState = chickenState; }
    public ChickenState getChickenState() { return chickenState; }

    public int getDaysInstantiated() { return daysInstantiated; }
    public void setDaysInstantiated(int daysInstantiated) { this.daysInstantiated = daysInstantiated; }
    public void increaseDaysInstantiated() {
        daysInstantiated++;
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
        switch (handler.getWorld().getEntityManager().getPlayer().getCurrentDirection()) {
            case UP:
                x = handler.getWorld().getEntityManager().getPlayer().getX();
                y = handler.getWorld().getEntityManager().getPlayer().getY() - Tile.TILE_HEIGHT;
                break;
            case DOWN:
                x = handler.getWorld().getEntityManager().getPlayer().getX();
                y = handler.getWorld().getEntityManager().getPlayer().getY() + Tile.TILE_HEIGHT;
                break;
            case LEFT:
                x = handler.getWorld().getEntityManager().getPlayer().getX() - Tile.TILE_WIDTH;
                y = handler.getWorld().getEntityManager().getPlayer().getY();
                break;
            case RIGHT:
                x = handler.getWorld().getEntityManager().getPlayer().getX() + Tile.TILE_HEIGHT;
                y = handler.getWorld().getEntityManager().getPlayer().getY();
                break;
            default:
                System.out.println("Chicken.drop(Tile) switch construct's default option.");
                break;
        }

        if (!handler.getWorld().getEntityManager().getEntities().contains(this)) {
            handler.getWorld().getEntityManager().addEntity(this);
        }

        pickedUp = false;
    }

} // **** end Chicken class ****