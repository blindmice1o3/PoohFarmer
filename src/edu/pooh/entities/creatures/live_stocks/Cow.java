package edu.pooh.entities.creatures.live_stocks;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Cow extends Creature {

    public enum CowState { BABY, CALF, ADULT_1, ADULT_2, ADULT_3, PREGNANT; }
    public enum CowHealth { HEALTHY, CRANKY, SICK; }

    private transient Map<String, Animation> anim;

    private int fodderDisplayerTileArrayIndex;  //feeding stall index

    private int daysInstantiated;
    private int daysImpregnanted;

    private CowState cowState;
    private CowHealth cowHealth;

    private int affectionScore;
    private boolean brushed;
    private boolean milked;

    private Random random;

    public Cow(Handler handler, float x, float y, CowState cowState, int fodderDisplayerTileArrayIndex) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        setSpeed(2);

        initCowAnimations();

        this.fodderDisplayerTileArrayIndex = fodderDisplayerTileArrayIndex;

        daysInstantiated = 0;
        daysImpregnanted = 0;

        this.cowState = cowState;
        cowHealth = CowHealth.HEALTHY;

        affectionScore = 0;
        brushed = false;
        milked = false;

        random = new Random();

        ////////////////////////////////////////////////////////////////////////
        System.out.println("Increasing ResourceManager's cowCounter by 1");
        handler.getResourceManager().increaseCowCounter(1);
        ////////////////////////////////////////////////////////////////////////
    } // **** end Cow(Handler, float, float, cowState) constructor ****

    private void initCowAnimations() {
        anim = new HashMap<String, Animation>();

        anim.put("animCowBabyUp", new Animation(400, Assets.cowBabyUp));
        anim.put("animCowBabyDown", new Animation(400, Assets.cowBabyDown));
        anim.put("animCowBabyLeft", new Animation(400, Assets.cowBabyLeft));
        anim.put("animCowBabyRight", new Animation(400, Assets.cowBabyRight));

        anim.put("animCowCalfUp", new Animation(400, Assets.cowCalfUp));
        anim.put("animCowCalfDown", new Animation(400, Assets.cowCalfDown));
        anim.put("animCowCalfLeft", new Animation(400, Assets.cowCalfLeft));
        anim.put("animCowCalfRight", new Animation(400, Assets.cowCalfRight));

        anim.put("animCowAdultUp", new Animation(400, Assets.cowAdultUp));
        anim.put("animCowAdultDown", new Animation(400, Assets.cowAdultDown));
        anim.put("animCowAdultLeft", new Animation(400, Assets.cowAdultLeft));
        anim.put("animCowAdultRight", new Animation(400, Assets.cowAdultRight));

        anim.put("animCowPregnantUp", new Animation(400, Assets.cowPregnantUp));
        anim.put("animCowPregnantDown", new Animation(400, Assets.cowPregnantDown));
        anim.put("animCowPregnantLeft", new Animation(400, Assets.cowPregnantLeft));
        anim.put("animCowPregnantRight", new Animation(400, Assets.cowPregnantRight));
    }

    public void increaseDaysInstantiated() {
        daysInstantiated++;
    }

    public void increaseDaysImpregnanted() { daysImpregnanted++; }

    public void increaseAffectionScore(int increasedAffection) {
        affectionScore += increasedAffection;
    }

    public void decreaseAffectionScore(int decreasedAffection) { affectionScore -= decreasedAffection; }

    public void incrementCowStateByDaysInstantiated() {
        if (cowState != CowState.PREGNANT) {
            if (daysInstantiated >= 35) {
                if (affectionScore >= 192) {
                    cowState = CowState.ADULT_3;
                    System.out.println("Cow.incrementCowStateByDaysInstantiated()... set CowState.ADULT_3.");
                } else if ((affectionScore >= 96) && (affectionScore < 192)) {
                    cowState = CowState.ADULT_2;
                    System.out.println("Cow.incrementCowStateByDaysInstantiated()... set CowState.ADULT_2.");
                } else {
                    cowState = CowState.ADULT_1;
                    System.out.println("Cow.incrementCowStateByDaysInstantiated()... set CowState.ADULT_1.");
                }
            } else if (daysInstantiated == 14) {
                cowState = CowState.CALF;
                System.out.println("Cow.incrementCowStateByDaysInstantiated()... set CowState.CALF.");
            } else if (daysInstantiated == 0) {
                cowState = CowState.BABY;
                System.out.println("Cow.incrementCowStateByDaysInstantiated()... set CowState.BABY.");
            }
        }
    }

    @Override
    public void tick() {
        for (Animation tempAnim : anim.values()) {
            tempAnim.tick();
        }

        randomlyMove();
        //@@@move();
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

        if (cowState == CowState.PREGNANT) {
            Text.drawString(g, "daysImpregnanted: " + daysImpregnanted, (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset() - 50), false, Color.PINK, Assets.font14);
        }

        if (cowHealth == CowHealth.HEALTHY) {
            Text.drawString(g, "cowHealth: " + cowHealth, (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset() - 40), false, Color.GREEN, Assets.font14);
        } else {
            Text.drawString(g, "cowHealth: " + cowHealth, (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset() - 40), false, Color.RED, Assets.font14);
        }

        //not milked and milkable (later account for cowHealth)
        if ((!milked) && ((cowState == CowState.ADULT_1) || (cowState == CowState.ADULT_2) || (cowState == CowState.ADULT_3))) {
            Text.drawString(g, "milked: " + milked, (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset() - 30), false, Color.YELLOW, Assets.font14);
        }
        //milked and milkable
        else if ((cowState == CowState.ADULT_1) || (cowState == CowState.ADULT_2) || (cowState == CowState.ADULT_3)) {
            Text.drawString(g, "milked: " + milked, (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset() - 30), false, Color.RED, Assets.font14);
        }
        //not milkable (BABY, CALF, PREGNANT)
        else {
            Text.drawString(g, "NOT milkable", (int)(x - handler.getGameCamera().getxOffset()),
                    (int)(y - handler.getGameCamera().getyOffset() - 30), false, Color.RED, Assets.font14);
        }

        Text.drawString(g, "affectionScore: " + affectionScore, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset() - 20), false, Color.BLUE, Assets.font14);

        if (!brushed) {
            Text.drawString(g, "brushed: " + brushed, (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset() - 10), false, Color.YELLOW, Assets.font14);
        } else {
            Text.drawString(g, "brushed: " + brushed, (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset() - 10), false, Color.RED, Assets.font14);
        }

        Text.drawString(g, "stall " + fodderDisplayerTileArrayIndex + ": " +
                        cowState + " (" + daysInstantiated + ")",
                (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), false, Color.BLUE, Assets.font14);
    }

    private BufferedImage getCurrentAnimationFrame() {
        // ANIMATION MOVEMENTS
            // BABY
        if ((xMove < 0) && (cowState == CowState.BABY)) {                            // Moving left.
            return anim.get("animCowBabyLeft").getCurrentFrame();
        } else if ((xMove > 0) && (cowState == CowState.BABY)) {                     // Moving right.
            return anim.get("animCowBabyRight").getCurrentFrame();
        } else if ((yMove < 0) && (cowState == CowState.BABY)) {                     // Moving up.
            return anim.get("animCowBabyUp").getCurrentFrame();
        } else if ((yMove > 0) && (cowState == CowState.BABY)) {                     // Moving down.
            return anim.get("animCowBabyDown").getCurrentFrame();
        }
            // CALF
        else if ((xMove < 0) && (cowState == CowState.CALF)) {
            return anim.get("animCowCalfLeft").getCurrentFrame();
        } else if ((xMove > 0) && (cowState == CowState.CALF)) {
            return anim.get("animCowCalfRight").getCurrentFrame();
        } else if ((yMove < 0) && (cowState == CowState.CALF)) {
            return anim.get("animCowCalfUp").getCurrentFrame();
        } else if ((yMove > 0) && (cowState == CowState.CALF)) {
            return anim.get("animCowCalfDown").getCurrentFrame();
        }
            // ADULT
        else if ( (xMove < 0) &&
                ((cowState == CowState.ADULT_1) || (cowState == CowState.ADULT_2) || (cowState == CowState.ADULT_3)) ) {
            return anim.get("animCowAdultLeft").getCurrentFrame();
        } else if ((xMove > 0) &&
                ((cowState == CowState.ADULT_1) || (cowState == CowState.ADULT_2) || (cowState == CowState.ADULT_3)) ) {
            return anim.get("animCowAdultRight").getCurrentFrame();
        } else if ((yMove < 0) &&
                ((cowState == CowState.ADULT_1) || (cowState == CowState.ADULT_2) || (cowState == CowState.ADULT_3)) ) {
            return anim.get("animCowAdultUp").getCurrentFrame();
        } else if ((yMove > 0) &&
                ((cowState == CowState.ADULT_1) || (cowState == CowState.ADULT_2) || (cowState == CowState.ADULT_3)) ) {
            return anim.get("animCowAdultDown").getCurrentFrame();
        }
            // PREGNANT
        else if ((xMove < 0) && (cowState == CowState.PREGNANT)) {
            return anim.get("animCowPregnantLeft").getCurrentFrame();
        } else if ((xMove > 0) && (cowState == CowState.PREGNANT)) {
            return anim.get("animCowPregnantRight").getCurrentFrame();
        } else if ((yMove < 0) && (cowState == CowState.PREGNANT)) {
            return anim.get("animCowPregnantUp").getCurrentFrame();
        } else if ((yMove > 0) && (cowState == CowState.PREGNANT)) {
            return anim.get("animCowPregnantDown").getCurrentFrame();
        }
        // NON-MOVING BABY
        else if ((xMove == 0) && (yMove == 0) & (cowState == CowState.BABY)){
            return Assets.cowBabyDown[0];
        }
        // NON-MOVING CALF
        else if ((xMove == 0) && (yMove == 0) & (cowState == CowState.CALF)){
            return Assets.cowCalfDown[0];
        }
            // NON-MOVING PREGNANT
        else if ((xMove == 0) && (yMove == 0) & (cowState == CowState.PREGNANT)){
            return Assets.cowPregnantDown[0];
        }
            // NON-MOVING ADULT
        else {
            return Assets.cowAdultDown[0];
        }
    }

    @Override
    public void hurt(int amt) { return; }

    @Override
    public void die() {
        setActive(false);
    }

    // GETTERS AND SETTERS

    public int getDaysInstantiated() { return daysInstantiated; }

    public void setDaysInstantiated(int daysInstantiated) { this.daysInstantiated = daysInstantiated; }

    public int getDaysImpregnanted() { return daysImpregnanted; }

    public void setDaysImpregnanted(int daysImpregnanted) { this.daysImpregnanted = daysImpregnanted; }

    public boolean isBrushed() { return brushed; }

    public void setBrushed(boolean brushed) { this.brushed = brushed; }

    public boolean isMilked() { return milked; }

    public void setMilked(boolean milked) { this.milked = milked; }

    public int getAffectionScore() { return affectionScore; }

    public void setAffectionScore(int affectionScore) { this.affectionScore = affectionScore; }

    public CowState getCowState() { return cowState; }

    public void setCowState(CowState cowState) { this.cowState = cowState; }

    public CowHealth getCowHealth() { return cowHealth; }

    public void setCowHealth(CowHealth cowHealth) { this.cowHealth = cowHealth; }

    public Random getRandom() { return random; }

    public int getFodderDisplayerTileArrayIndex() { return fodderDisplayerTileArrayIndex; }

    public void setFodderDisplayerTileArrayIndex(int fodderDisplayerTileArrayIndex) {
        this.fodderDisplayerTileArrayIndex = fodderDisplayerTileArrayIndex;
    }

} // **** end Cow class ****