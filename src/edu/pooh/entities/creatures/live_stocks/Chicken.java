package edu.pooh.entities.creatures.live_stocks;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.ResourceManager;
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

    public enum ChickenState { CHICK, ADULT_EGG_LAYING, ADULT_GRUMPY_1, ADULT_GRUMPY_2, ADULT_GRUMPY_3; }

    private Map<String, Animation> anim;

    private int daysInstantiated;
    private ChickenState chickenState;

    private Random random;
    private boolean pickedUp;

    public Chicken(Handler handler, float x, float y, ChickenState chickenState) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        setSpeed(4);

        initChickenAnimations();

        daysInstantiated = 0;
        this.chickenState = chickenState;

        random = new Random();
        pickedUp = false;

        ////////////////////////////////////////////////////////////////////////
        System.out.println("Increasing ResourceManager's chickenCounter by 1");
        handler.getResourceManager().increaseChickenCounter(1);
        ////////////////////////////////////////////////////////////////////////
    } // **** end Chicken(Handler, float, float, ChickenState) constructor ****

    private void initChickenAnimations() {
        anim = new HashMap<String, Animation>();

        anim.put("animChickenUp", new Animation(400, Assets.chickenAdultUp));
        anim.put("animChickenDown", new Animation(400, Assets.chickenAdultDown));
        anim.put("animChickenLeft", new Animation(400, Assets.chickenAdultLeft));
        anim.put("animChickenRight", new Animation(400, Assets.chickenAdultRight));
        anim.put("animChickUp", new Animation(400, Assets.chickenYoungUp));
        anim.put("animChickDown", new Animation(400, Assets.chickenYoungDown));
        anim.put("animChickLeft", new Animation(400, Assets.chickenYoungLeft));
        anim.put("animChickRight", new Animation(400, Assets.chickenYoungRight));
    }

    public void increaseDaysInstantiated() {
        daysInstantiated++;
    }

    public void incrementChickenStateByDaysInstantiated() {
        if (daysInstantiated == 7) {
            chickenState = ChickenState.ADULT_EGG_LAYING;
            System.out.println("Chicken.incrementChickenStateByDaysInstantiated()... set ChickenState.ADULT_EGG_LAYING.");
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
        Text.drawString(g, daysInstantiated + ": " + chickenState, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), false, Color.BLUE, Assets.font14);
    }

    private BufferedImage getCurrentAnimationFrame() {
        // ANIMATION MOVEMENTS
            // ADULT_EGG_LAYING, ADULT_GRUMPY_1, ADULT_GRUMPY_2, ADULT_GRUMPY_3
        if ((xMove < 0) && (chickenState != ChickenState.CHICK)) {                                // Moving left.
            return anim.get("animChickenLeft").getCurrentFrame();
        } else if ((xMove > 0) && (chickenState != ChickenState.CHICK)) {                         // Moving right.
            return anim.get("animChickenRight").getCurrentFrame();
        } else if ((yMove < 0) && (chickenState != ChickenState.CHICK)) {                         // Moving up.
            return anim.get("animChickenUp").getCurrentFrame();
        } else if ((yMove > 0) && (chickenState != ChickenState.CHICK)) {                         // Moving down.
            return anim.get("animChickenDown").getCurrentFrame();
        }
            // CHICK
        else if ((xMove < 0) && (chickenState == ChickenState.CHICK)) {
            return anim.get("animChickLeft").getCurrentFrame();
        } else if ((xMove > 0) && (chickenState == ChickenState.CHICK)) {
            return anim.get("animChickRight").getCurrentFrame();
        } else if ((yMove < 0) && (chickenState == ChickenState.CHICK)) {
            return anim.get("animChickUp").getCurrentFrame();
        } else if ((yMove > 0) && (chickenState == ChickenState.CHICK)) {
            return anim.get("animChickDown").getCurrentFrame();
        }
            // NON-MOVING CHICK
        else if ((xMove == 0) && (yMove == 0) & (chickenState == ChickenState.CHICK)){
            return Assets.chickenYoungDown[0];
        }
            // NON-MOVING ADULT
        else {
            return Assets.chickenAdultDown[0];
        }
    }

    public ChickenState getChickenState() { return chickenState; }

    public void setChickenState(ChickenState chickenState) { this.chickenState = chickenState; }

    public int getDaysInstantiated() { return daysInstantiated; }

    public void setDaysInstantiated(int daysInstantiated) { this.daysInstantiated = daysInstantiated; }

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