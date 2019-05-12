package edu.pooh.entities.creatures;

import edu.pooh.entities.Entity;
import edu.pooh.entities.statics.statics1x1.Bush;
import edu.pooh.entities.statics.statics1x1.Rock;
import edu.pooh.entities.statics.statics1x1.Wood;
import edu.pooh.entities.statics.statics2x2.Boulder;
import edu.pooh.entities.statics.statics2x2.ShippingBin;
import edu.pooh.entities.statics.statics2x2.TreeStump;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.Inventory;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.items.Item;
import edu.pooh.items.tier0.WateringCan;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.main.ISellable;
import edu.pooh.states.GameState;
import edu.pooh.time.DateDisplayer;
import edu.pooh.time.TimeManager;
import edu.pooh.sfx.SoundManager;
import edu.pooh.states.StateManager;
import edu.pooh.tiles.*;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Creature {
    // TODO: Implement stamina feature (eating, sleeping/newDay, hot spring, power berry, tool usage).
    public static final AudioClip sfxCannabisCollected = SoundManager.sounds[0];
    public static final AudioClip sfxBButtonPressed = SoundManager.sounds[1];

    // CANNABIS COUNTER
    private int cannabisCollected;

    // STAMINA TRACKER
    private int staminaBase;
    private int staminaCurrent;
    public void decreaseStaminaCurrent(int staminaUsage) {
        staminaCurrent = Math.max((staminaCurrent - staminaUsage), 0);
    }
    public void increaseStaminaCurrent(int staminaGained) {
        staminaCurrent = Math.min((staminaCurrent + staminaGained), staminaBase);
    }
    public void resetStaminaCurrent() {
        System.out.println("Player.resetStaminaCurrent()");
        staminaCurrent = staminaBase;
    }

    // ANIMATIONS
    private Map<String, Animation> animations;

    // ATTACK TIMER
    private long lastAttackTimer;
    private long attackCooldown = 800;  // 800 milliseconds
    private long attackTimer = attackCooldown;

    // INVENTORY
    private Inventory inventory;

    // DATE DISPLAYER
    private DateDisplayer dateDisplayer;
    private boolean executedSleep, executed6pm, executed5pm, executed3pm, executed12pm, executed9am, executed6am;

    public void executeSleep() {
        TimeManager.setNewDayTrue();

        if (!executed6am) {
            execute6am();
            executed6am = true;
        }
        if (!executed9am) {
            execute9am();
            executed9am = true;
        }
        if (!executed12pm) {
            execute12pm();
            executed12pm = true;
        }
        if (!executed3pm) {
            execute3pm();
            executed3pm = true;
        }
        ////////////////////////
        if (!executed5pm) {
            execute5pm();
            executed5pm = true;
        }
        ////////////////////////
        if (!executed6pm) {
            execute6pm();
            executed6pm = true;
        }

        setAllTimeRelatedBooleansToFalse();
        resetStaminaCurrent();

        System.out.println("Player.executeSleep()");
        executedSleep = true;
    }

    public void execute6pm() {

        System.out.println("Player.execute6pm()");
        executed6pm = true;
    }

    public void execute5pm() {
        for (Entity e : ((GameState)handler.getGame().getGameState()).getWorld().getEntityManager().getEntities()) {
            if (e.equals(this)) { continue; }

            if (e instanceof ShippingBin) {
                //////////////////////////////////////////////////////////
                System.out.println("FOUND ShippingBin object!");

                ResourceManager.increaseCurrencyUnitCount( ((ShippingBin)e).calculateTotal() );
                ((ShippingBin)e).emptyShippingBin();
                //////////////////////////////////////////////////////////
                break;
            } else {
                System.out.println("COULD NOT FIND ShippingBin object!!!!");
            }
        }

        System.out.println("Player.execute5pm()");
        executed5pm = true;
    }

    public void execute3pm() {

        System.out.println("Player.execute3pm()");
        executed3pm = true;
    }

    public void execute12pm() {

        System.out.println("Player.execute12pm()");
        executed12pm = true;
    }

    public void execute9am() {

        System.out.println("Player.execute9am()");
        executed9am = true;
    }

    public void execute6am() {

        System.out.println("Player.execute6am()");
        executed6am = true;
    }

    // HOLDING (composed with IHoldable type)
    private IHoldable holdableObject;
    private Rectangle hr; // holding-rectangle
    private int hrSize = 30;
    private boolean holding = false;

    // MELEE ATTACK
    private Rectangle cb; // player's collision/bounding box
    private Rectangle ar; // attack-rectangle
    private int arSize = 20;
    private boolean attacking = false;

    public void setAllTimeRelatedBooleansToFalse() {
        System.out.println("Player.setAllTimeRelatedBooleansToFalse()");

        executed6am = false;
        executed9am = false;
        executed12pm = false;
        executed3pm = false;
        executed5pm = false;
        executed6pm = false;
    }

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        bounds.x = 15;
        bounds.y = 20;
        bounds.width = 34;
        bounds.height = 44;

        // CANNABIS COUNTER
        cannabisCollected = 0;

        // STAMINA TRACKER
        staminaBase = 100;
        staminaCurrent = 100;

        // ANIMATIONS
        initAnimations();

        // INVENTORY
        inventory = new Inventory(handler);
        inventory.addItem(WateringCan.getUniqueInstance(handler));
        inventory.getItem(0).setPickedUp(true);

        // DATE DISPLAYER
        dateDisplayer = new DateDisplayer(handler);
        setAllTimeRelatedBooleansToFalse();

        // HOLDING
        holdableObject = null;
        hr = new Rectangle();
        hr.width = hrSize;
        hr.height = hrSize;

        // MELEE ATTACK
        ar = new Rectangle();
        ar.width = arSize;
        ar.height = arSize;
    } // **** end Player(Handler, float, float) constructor ****

    private void initAnimations() {
        animations = new HashMap<String, Animation>();

        animations.put("animDown", new Animation(60, Assets.playerDown));
        animations.put("animUp", new Animation(60, Assets.playerUp));
        animations.put("animLeft", new Animation(60, Assets.playerLeft));
        animations.put("animRight", new Animation(60, Assets.playerRight));
        animations.put("animUpRight", new Animation(60, Assets.playerUpRight));
        animations.put("animUpLeft", new Animation(60, Assets.playerUpLeft));
        animations.put("animDownRight", new Animation(60, Assets.playerDownRight));
        animations.put("animDownLeft", new Animation(60, Assets.playerDownLeft));
    }

    private void checkTimeRelatedActions() {
        // Within it's hourly range AND have not executed (e.g. will only run if executed6am is false).
        if (TimeManager.elapsedRealSeconds >= 0 && TimeManager.elapsedRealSeconds < 180 && !executed6am) {
            execute6am();
        } else if (TimeManager.elapsedRealSeconds >= 180 && TimeManager.elapsedRealSeconds < 360 && !executed9am) {
            execute9am();
        } else if (TimeManager.elapsedRealSeconds >= 360 && TimeManager.elapsedRealSeconds < 540 && !executed12pm) {
            execute12pm();
        } else if (TimeManager.elapsedRealSeconds >= 540 && TimeManager.elapsedRealSeconds < 660 && !executed3pm) {
            execute3pm();
        }
        // ****************** | 5pm | *********************
        else if (TimeManager.elapsedRealSeconds >= 660 && TimeManager.elapsedRealSeconds < 720 && !executed5pm) {
            execute5pm();
        }
        // ************************************************
        else if (TimeManager.elapsedRealSeconds == 720 && !executed6pm) {
            execute6pm();
        }
    }

    private void checkWinningConditions() {
        if (cannabisCollected == 3000) {
            sfxBButtonPressed.play();

            System.out.println("game stopping");
            handler.getGame().gameStop();
        }
    }

    @Override
    public void tick() {
        // CANNABIS COUNTER ( !!!!! checks for WINNER STATE !!!!! )
        checkWinningConditions();

        // TIME SPECIFIC ACTIONS (e.g. meal time, shipping bin collection time)
        checkTimeRelatedActions();

        // ANIMATIONS
        for (Animation anim : animations.values()) {
            anim.tick();
        }

        // MOVEMENT
        getInput(); // Sets the xMove and yMove variables.
        move();     // Changes the x and y coordinates of the player based on xMove and yMove variables.
        handler.getGameCamera().centerOnEntity(this);

        // ATTACK
        if (!holding) {         //if holding from GameState, moved into MountainState... cannot put down... cannot attack.
            checkAttacks();
        }
        // HOLDING (at this point: holding is true)
        // but we do another (similar, possibly redundant) check... holdableObject should not be null.
        else if (holdableObject != null) {
            holdableObject.setPosition(x + 10, y - 15);  // Moves image of holdableObject w/ player's.
        }

        // INVENTORY
        inventory.tick();
    }

    private void checkAttacks() {
        ////////////////////////////////////////////////////////////////////////////////////
        // Attack timer to check eligibility for new attack.
        attackTimer += System.currentTimeMillis() - lastAttackTimer;    // time elapsed
        lastAttackTimer = System.currentTimeMillis();

        // Time elapsed has not reached targeted attackCooldown, exit this method early.
        if (attackTimer < attackCooldown) {
            return;
        }
        ////////////////////////////////////////////////////////////////////////////////////

        // IF AT THIS LINE, targeted attackCooldown has been reached, ELIGIBLE TO ATTACK.
        attacking = false;

        // (UNLESS THE INVENTORY IS TOGGLED ON)
        if (inventory.isActive()) {
            return;
        }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // player's collision box (center square in color-penciled drawing/notes).
        cb = getCollisionBounds(0, 0);
        // Set the coordinates of the attack rectangle (attacking is one-direction-at-a-time])
        if (handler.getKeyManager().aUp) {
            ar.x = cb.x + (cb.width / 2) - (arSize / 2);   // center x coordinate of our player's collision box
            ar.y = cb.y - arSize;
            attacking = true;
        } else if (handler.getKeyManager().aDown) {
            ar.x = cb.x + (cb.width / 2) - (arSize / 2);   // center x coordinate of our player's collision box
            ar.y = cb.y + cb.height;
            attacking = true;
        } else if (handler.getKeyManager().aLeft) {
            ar.x = cb.x - arSize;
            ar.y = cb.y + (cb.height / 2) - (arSize / 2);  // center y coordinate of collision box
            attacking = true;
        } else if (handler.getKeyManager().aRight) {
            ar.x = cb.x + cb.width;
            ar.y = cb.y + (cb.height / 2) - (arSize / 2);  // center y coordinate of collision box
            attacking = true;
        } else {
            return; // if none of the attack keys are being called, don't continue on with the rest of this method.
        }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // RESET attackTimer.
        attackTimer = 0;

        // The above attack rectangle coordinates were set to some values (did not exit this method early).
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            // If player, skip this for-loop iteration and move on to the next Entity object in the entities ArrayList.
            if (e.equals(this)) {
                continue;
            }

            // We have an Entity object that isn't the player, check if it intersects with the attack rectangle.
            if (e.getCollisionBounds(0, 0).intersects(ar)) {
                e.hurt(4);      // Successful attack collision, invoke hurt() method.
                return;
            }
        }
    }

    private int speedMax = 10;
    private void getInput() {
        // Important to reset xMove and yMove to 0 at start of getInput().
        xMove = 0;
        yMove = 0;

        // INVENTORY CHECK
        if (inventory.isActive()) {
            return;
        }

        // RUNNING
        if (handler.getKeyManager().running) {  //KeyEvent.VK_CONTROL
            if ((speed *= 2) <= speedMax) {
                speed *= 2;
            } else {
                speed = speedMax;
            }
        } else {
            speed = Creature.DEFAULT_SPEED;
        }

        // KEY INPUT to SET MOVEMENT
        if (handler.getKeyManager().up) {
            yMove = -speed;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
        }

        ///////////////// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ /////////////////

        // KeyEvent.VK_ESCAPE
        if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) &&
                (getTileCurrentlyFacing() instanceof SignPostTile)) {
            ((SignPostTile) getTileCurrentlyFacing()).setExecuting(false);
        }

        // B BUTTON
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            inventory.incrementSelectedItem();
            sfxBButtonPressed.play();
        }

        // A BUTTON
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {

            // TRAVELINGFENCE CHECK
            if (StateManager.getCurrentState() == handler.getGame().getGameState() &&
                    checkForTravelingFence()) {
                System.out.println("FOUND: The Finn!");

                Object[] args = new Object[10];
                args[0] = this;
                args[1] = (int) x;
                args[2] = (int) y;
                /////////////////////////////////////////////////////////////////////
                StateManager.change(handler.getGame().getTravelingFenceState(), args);
                /////////////////////////////////////////////////////////////////////
                return;
            }
            // SIGNPOSTTILE CHECK
            else if (getTileCurrentlyFacing() instanceof SignPostTile) {
                ((SignPostTile)getTileCurrentlyFacing()).execute();
            }
            // FODDERSTASHTILE CHECK
            else if (getTileCurrentlyFacing() instanceof FodderStashTile) {
                ((FodderStashTile)getTileCurrentlyFacing()).execute();
            }

            // HOLDING CHECK
            if (holding) {  // Already holding, can only drop the holdableObject.
                // if ShippingBin tile... store in ArrayList<HarvestEntity> until 5pm.
                if (getEntityCurrentlyFacing() != null && getEntityCurrentlyFacing() instanceof ShippingBin) {
                    if (holdableObject instanceof ISellable) {
                        /////////////////////////////////////////////////////
                        // TODO: HORSE SADDLE BAG - MOVEABLE SHIPPING BIN. //
                        /////////////////////////////////////////////////////
                        ((ISellable) holdableObject).dropIntoShippingBin((ShippingBin) getEntityCurrentlyFacing());
                        setHoldableObject(null);
                        holding = false;
                    }
                } else {
                    if (checkDropableTile()) {
                        /////////////@@@@@@@@@@@@@@@@@@@@////////////////
                        holdableObject.dropped(getTileCurrentlyFacing());
                        setHoldableObject(null);
                        holding = false;
                        /////////////////////////////////////////////////
                    }
                }
                // TODO: Dropped HarvestEntity Object should render an image of itself broken and then setActive(false).
            } else {        // Not holding IHoldable.
                if (checkForHoldable()) {   // Check if IHoldable in front, pick up if true.
                    if (!holding) {
                        //////////////////////////////////////
                        setHoldableObject(pickUpHoldable());
                        holdableObject.pickedUp();
                        holding = true;
                        //////////////////////////////////////
                    }
                } else if (getTileCurrentlyFacing() instanceof SolidGenericTile) {
                    if (getTileCurrentlyFacing() instanceof SignPostTile) {
                        ((SignPostTile) getTileCurrentlyFacing()).execute();
                    } else if (getTileCurrentlyFacing() instanceof BedTile) {
                        ((BedTile) getTileCurrentlyFacing()).execute();
                    } else if (getTileCurrentlyFacing() instanceof HotSpringMountainTile) {
                        ((HotSpringMountainTile) getTileCurrentlyFacing()).execute();
                    }
                } else { // Not holding IHoldable, no IHoldable in front, not bed tile in front, use selected item.

                    // |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|
                    inventory.getItem(inventory.getIndex()).execute();
                    decreaseStaminaCurrent(2);      // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                    // |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|

                }
            }
        }
    }

    private boolean checkForTravelingFence() {
        return (getEntityCurrentlyFacing() instanceof TravelingFence);
    }

    private boolean checkDropableTile() {
        Tile tempTileInFront = getTileCurrentlyFacing();

        // If DirtNormalTile.
        if (tempTileInFront instanceof DirtNormalTile) {
            if (((DirtNormalTile)tempTileInFront).getStaticEntity() != null) {
                if ((((DirtNormalTile)tempTileInFront).getStaticEntity() instanceof TreeStump) ||
                        (((DirtNormalTile)tempTileInFront).getStaticEntity() instanceof Boulder) ||
                        (((DirtNormalTile)tempTileInFront).getStaticEntity() instanceof Wood) ||
                        (((DirtNormalTile)tempTileInFront).getStaticEntity() instanceof Rock) ||
                        (((DirtNormalTile)tempTileInFront).getStaticEntity() instanceof Bush)) {
                    return false;
                }
            }
            return true;
        }
        // If DirtNotFarmableTile.
        else if (tempTileInFront instanceof DirtNotFarmableTile) {
            return (((DirtNotFarmableTile)tempTileInFront).getStaticEntity() == null);
        }
        // If poolWater Tile.
        else if (tempTileInFront.getId() >= 236 && tempTileInFront.getId() <= 248) {
            return true;
        }
        // If holding a move-able Entity and the tile in front of player is NOT solid.
        // @@@@@@@@@@@@@
        else if (holdableObject instanceof Creature && !tempTileInFront.isSolid()) {
            return true;
        }
        // @@@@@@@@@@@@@
        return false;
    }

    private void setHRPosition() {
        cb = getCollisionBounds(0, 0);    // player's collision box (center square)

        // Setting the coordinate of the holding rectangle
        switch (currentDirection) {
            case UP:
                hr.x = cb.x + (cb.width / 2) - (hrSize / 2);   // center x coordinate of our player's collision box
                hr.y = cb.y - hrSize;
                break;
            case DOWN:
                hr.x = cb.x + (cb.width / 2) - (hrSize / 2);   // center x coordinate of our player's collision box
                hr.y = cb.y + cb.height;
                break;
            case LEFT:
                hr.x = cb.x - hrSize;
                hr.y = cb.y + (cb.height / 2) - (hrSize / 2);  // center y coordinate of collision box
                break;
            case RIGHT:
                hr.x = cb.x + cb.width;
                hr.y = cb.y + (cb.height / 2) - (hrSize / 2);  // center y coordinate of collision box
                break;
            default: // if not facing UDLR-directions, give bottom-left tile of the world map.
                hr.x = 0;
                hr.y = handler.getWorld().getHeightInTiles() - Tile.TILE_HEIGHT;
                break;
        }
    }

    private IHoldable pickUpHoldable() {
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            // If the player, skip the rest of the code, move on to the next Entity in the entities ArrayList.
            if (e.equals(this)) { continue; }

            // We have an Entity object that isn't the player, check if it intersects with the holding rectangle.
            if (e.getCollisionBounds(0, 0).intersects(hr)) {
                if (e instanceof IHoldable) {
                    //////
                    return (IHoldable)e;
                }
            }
        }
        return null;
    }

    private boolean checkForHoldable() {
        setHRPosition();

        // The above holding rectangle coordinates were set to some UDLR-directional values
        if (hr.y != (handler.getWorld().getHeightInTiles() - Tile.TILE_HEIGHT)) {

            for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
                // If the player, skip the rest of the code, move on to the next Entity in the entities ArrayList.
                if (e.equals(this)) { continue; }

                // We have an Entity object that isn't the player, check if it intersects with the holding rectangle.
                if (e.getCollisionBounds(0, 0).intersects(hr)) {
                    if (e instanceof IHoldable) {
                        System.out.print("IHoldable object in front of player.");
                        return true;
                    }
                }
            }

        }

        return false;
    }

    @Override
    public void render(Graphics g) {
        // MOVEMENT
        g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);

        // COLLISION BOX
        //g.setColor(Color.RED);
        //g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
        //        (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
        //        bounds.width, bounds.height);

        // MELEE ATTACK
        if (attacking) {
            g.setColor(Color.RED);
            g.fillRect((int)(ar.x - handler.getGameCamera().getxOffset()),
                    (int)(ar.y - handler.getGameCamera().getyOffset()), ar.width, ar.height);
        }

        // HUD (Head-Up-Display)
        renderHUD(g);
    }

    public void postRender(Graphics g) {
        inventory.render(g);                // KeyEvent.VK_I
        dateDisplayer.render(g);            // KeyEvent.VK_SHIFT
    }

    public void renderHUD(Graphics g) {
        // CANNABIS COUNTER VISUAL (TOP-LEFT CORNER)
        g.setColor(Color.BLUE);
        g.drawRect((25 - 2), (25 - 2), (Item.ITEM_WIDTH + 3), (Item.ITEM_HEIGHT + 3));
        Text.drawString(g, Integer.toString(ResourceManager.getCurrencyUnitCount()),
                (25 + (Item.ITEM_WIDTH / 2)), (25 + (Item.ITEM_HEIGHT / 2)), true, Color.YELLOW, Assets.font28);

        // IN-GAME TIME (YELLOW) and REAL-LIFE ELAPSED SECONDS (BLUE) (TOP-CENTER OF SCREEN)
        Text.drawString(g, TimeManager.translateElapsedRealSecondsToGameHoursMinutes(),
                (handler.getWidth() / 2), 30, true, Color.YELLOW, Assets.font28);
        Text.drawString(g, Integer.toString(TimeManager.elapsedRealSeconds),
                (handler.getWidth() / 2), 55, true, Color.BLUE, Assets.font28);

        // CURRENT SELECTED ITEM FROM INVENTORY (TOP-RIGHT CORNER)
        g.setColor(Color.BLUE);
        g.drawRect((Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) - 2), 25 - 2,
                (Item.ITEM_WIDTH + 3), (Item.ITEM_HEIGHT + 3));
        g.drawImage( inventory.getItem(inventory.getIndex()).getTexture(),
                (Game.  WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH)), 25,
                Item.ITEM_WIDTH, Item.ITEM_HEIGHT, null);
        if (inventory.getItem(inventory.getIndex()).getId() == Item.ID.WATERING_CAN) {
            WateringCan temp = (WateringCan)inventory.getItem(inventory.getIndex());
            Text.drawString(g, Integer.toString(temp.getCountWater()),
                    (Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) + (Item.ITEM_WIDTH / 2)),
                    25 + Item.ITEM_HEIGHT + 15, true, Color.BLUE, Assets.font28);
        } else {
            Text.drawString(g, Integer.toString(inventory.getItem(inventory.getIndex()).getCount()),
                    (Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) + (Item.ITEM_WIDTH / 2)),
                    25 + Item.ITEM_HEIGHT + 15, true, Color.YELLOW, Assets.font28);
        }

        // STAMINA TRACKER VISUAL (LEFT TOP-ISH OF SCREEN)
        g.setColor(Color.BLUE);
        g.fillRect(33, 81, 15, staminaBase + 4);
        g.setColor(Color.YELLOW);
        g.fillRect(35, 83 + (staminaBase - staminaCurrent), 11, staminaCurrent);
    }

    private BufferedImage getCurrentAnimationFrame() {
        // Horizontal (x axis) over vertical (y axis).

        // ANIMATION MOVEMENTS
        if (yMove < 0 && xMove > 0) {                   // Moving up-right.
            return animations.get("animUpRight").getCurrentFrame();
        } else if (yMove < 0 && xMove < 0) {            // Moving up-left.
            return animations.get("animUpLeft").getCurrentFrame();
        } else if (yMove > 0 && xMove > 0) {            // Moving down-right.
            return animations.get("animDownRight").getCurrentFrame();
        } else if (yMove > 0 && xMove < 0) {            // Moving down-left.
            return animations.get("animDownLeft").getCurrentFrame();
        } else if (xMove < 0) {                         // Moving left.
            return animations.get("animLeft").getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return animations.get("animRight").getCurrentFrame();
        } else if (yMove < 0) {                         // Moving up.
            return animations.get("animUp").getCurrentFrame();
        } else if (yMove > 0) {                         // Moving down.
            return animations.get("animDown").getCurrentFrame();
        }

        // NON-ANIMATION for NO-INPUT DIRECTIONS
        switch (currentDirection) {
            case UPRIGHT:
                return Assets.playerUpRightDefault;
            case UPLEFT:
                return Assets.playerUpLeftDefault;
            case DOWNRIGHT:
                return Assets.playerDownRightDefault;
            case DOWNLEFT:
                return Assets.playerDownLeftDefault;
            case LEFT:
                return Assets.playerLeftDefault;
            case RIGHT:
                return Assets.playerRightDefault;
            case UP:
                return Assets.playerUpDefault;
            case DOWN:
                return Assets.playerDownDefault;
            default:
                return Assets.playerDownDefault;
        }

    }

    @Override
    public void die() {
        System.out.println("You lose");
    }

    public void increaseCannabisCollected() {
        cannabisCollected++;
        sfxCannabisCollected.play();
    }

    public void resetCannabisCollected() { cannabisCollected = 0; }

    // GETTERS & SETTERS

    public int getCannabisCollected() {
        return cannabisCollected;
    }

    public IHoldable getHoldableObject() {
        return holdableObject;
    }

    public void setHoldableObject(IHoldable holdableObject) {
        this.holdableObject = holdableObject;
    }

    public boolean getHolding() {
        return holding;
    }

    public void setHolding(boolean holding) {
        this.holding = holding;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

} // **** end Player class ****