package edu.pooh.entities.creatures.player;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Creature;
import edu.pooh.entities.creatures.TravelingFence;
import edu.pooh.entities.statics.produce_yields.Egg;
import edu.pooh.entities.statics.statics1x1.*;
import edu.pooh.entities.statics.statics2x2.Boulder;
import edu.pooh.entities.statics.statics2x2.ShippingBin;
import edu.pooh.entities.statics.statics2x2.TreeStump;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.Inventory;
import edu.pooh.items.Item;
import edu.pooh.items.crops.tier0.Axe;
import edu.pooh.items.crops.tier0.Hammer;
import edu.pooh.items.crops.tier0.WateringCan;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.main.ISellable;
import edu.pooh.sfx.SoundManager;
import edu.pooh.states.StateManager;
import edu.pooh.states.TextboxState;
import edu.pooh.tiles.*;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Creature {

    public static final AudioClip sfxCannabisCollected = SoundManager.sounds[0];
    public static final AudioClip sfxBButtonPressed = SoundManager.sounds[1];

    // CANNABIS COUNTER
    private int cannabisCollected;

    // ANIMATIONS
    private transient Map<String, Animation> animations;

    // MOVEMENT SPEED
    private int speedMax;

    // INVENTORY
    private Inventory inventory;

    //TODO: convert to State design pattern. 2 concrete subtype to choose from (HoldingState and NotHoldingState).
    // HOLDING (composed with IHoldable type)
    private IHoldable holdableObject;
    private Rectangle hr; // holding-rectangle
    private int hrSize = 30;
    private boolean holding = false;

    // STAMINA
    private StaminaModule staminaModule;

    // ATTACK
    private AttackModule meleeAttackModule;

    // HUD
    private HeadUpDisplayer headUpDisplayer;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        bounds.x = 15;
        bounds.y = 20;
        bounds.width = 34;
        bounds.height = 44;

        // CANNABIS COUNTER
        cannabisCollected = 0;

        // ANIMATIONS
        initAnimations();

        // MOVEMENT SPEED
        speedMax = 10;

        // INVENTORY
        inventory = new Inventory(handler);
        inventory.addItem(WateringCan.getUniqueInstance(handler));
        inventory.getItem(0).setPickedUp(true);

        // HOLDING
        holdableObject = null;
        hr = new Rectangle();
        hr.width = hrSize;
        hr.height = hrSize;

        // STAMINA
        staminaModule = new StaminaModule(handler);

        // ATTACK
        meleeAttackModule = new AttackModule(handler, this);

        // HUD
        headUpDisplayer = new HeadUpDisplayer(handler, this);
    } // **** end Player(Handler, float, float) constructor ****

    @Override
    public void initAnimations() {
        animations = new HashMap<String, Animation>();

        animations.put("animDown", new Animation(60, Assets.playerDown));
        animations.put("animUp", new Animation(60, Assets.playerUp));
        animations.put("animLeft", new Animation(60, Assets.playerLeft));
        animations.put("animRight", new Animation(60, Assets.playerRight));
        animations.put("animUpRight", new Animation(60, Assets.playerUpRight));
        animations.put("animUpLeft", new Animation(60, Assets.playerUpLeft));
        animations.put("animDownRight", new Animation(60, Assets.playerDownRight));
        animations.put("animDownLeft", new Animation(60, Assets.playerDownLeft));
        //TODO: animation for Pooh using Thor's Hammer!
    }

    //TODO: Have Game class be composed with a QuestManager and move this method to QuestManager class.
    private void checkWinningConditions() {
        if (cannabisCollected == 3000) {
            sfxBButtonPressed.play();

            System.out.println("game stopping");
            handler.getGame().gameStop();
        }
    }

    @Override
    public void tick() {
        // ANIMATIONS
        for (Animation anim : animations.values()) {
            anim.tick();
        }

        // MOVEMENT
        getInput(); // Sets the xMove and yMove variables.
        move();     // Changes the x and y coordinates of the player based on xMove and yMove variables.
        handler.getGameCamera().centerOnEntity(this);

        // HOLDING (at this point: holding is true)
        // but we do another (similar, possibly redundant) check... holdableObject should not be null.
        if (holdableObject != null) {
            holdableObject.setPosition(x + 10, y - 15);  // Moves image of holdableObject w/ player's.
        }

        // INVENTORY
        inventory.tick();

        // ATTACK
        meleeAttackModule.tick();

        // HUD
        headUpDisplayer.tick();
    }

    //TODO: move to Hammer class.
    private int hitBoulderCounter = 0;
    //TODO: move to Axe class.
    private int hitTreeStumpCounter = 0;
    private void getInput() {
        // Important to reset xMove and yMove to 0 at start of getInput().
        xMove = 0;
        yMove = 0;

        //TODO: calls to SaverAndLoader save() and load() methods.
        // Serialize game.
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_F2)) {
            //TODO: SAVE.
            handler.getGame().getSaverAndLoader().save("pooh_farmer.bin");
        }
        // Deserialize game.
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_F4)) {
            //TODO: LOAD.
            handler.getGame().getSaverAndLoader().load();
        }

        // PauseState
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
            /////////////////////////////////////////////////////////////////////
            handler.getStateManager().change(StateManager.GameState.PAUSE, null);
            /////////////////////////////////////////////////////////////////////
        }

        // TextboxState Mode.THE_SIMPSONS (toggle TextboxState's currentMode).
        if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_SHIFT))) {
            TextboxState textboxState = (TextboxState)handler.getStateManager().getIState(StateManager.GameState.TEXT_BOX);
            TextboxState.Mode currentMode = textboxState.getCurrentMode();

            if (currentMode == TextboxState.Mode.DEFAULT) {
                textboxState.setCurrentMode(TextboxState.Mode.THE_SIMPSONS);
            } else if (currentMode == TextboxState.Mode.THE_SIMPSONS) {
                textboxState.setCurrentMode(TextboxState.Mode.DEFAULT);
            }

            System.out.println("TextboxState's currentMode is now: " + textboxState.getCurrentMode());
        }

        // KeyEvent.VK_SLASH        //TEXTBOXSTATE enter
        if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_SLASH))) {
            /////////////////////////////////////////////////////////////////////
            //Object[] args = { "I love you mom" };
            Object[] args = new Object[2];

            String message = "Sublett fell silent. Rydell felt sorry for him; the Texan really didn't know any other " +
                    "way to start a conversation, and his folks back home in the trailer-camp would've seen " +
                    "all those films and more. \"Well,\" Rydell said, trying to pick up his end, \"I was " +
                    "watching this one old movie last night-\" Sublett perked up. \"Which one?\" \"Dunno,\" " +
                    "Rydell said. \"This guy's in L.A. and he's just met this girl. Then he picks up a pay " +
                    "phone, 'cause it's ringing. Late at night. It's some guy in a missile silo somewhere " +
                    "who knows they've just launched theirs at the Russians. He's trying to phone his dad, " +
                    "or his brother, or something. Says the world's gonna end in short order. Then the guy " +
                    "who answered the phone hears these soldiers come in and shoot the guy. The guy on the " +
                    "phone, I mean.\" Sublett closed his eyes, scanning his inner trivia-banks. \"Yeah? " +
                    "How's it end?\" \"Dunno,\" Rydell said. \"I went to sleep.\" Sublett opened his eyes. " +
                    "\"Who was in it?\" \"Got me.\" Sublett's blank silver eyes widened in disbelief. \"Jesus, " +
                    "Berry, you shouldn't oughta watch tv, not unless you're gonna pay attention.\" -William " +
                    "Gibson's Virtual Light";
            //////////////////
            args[0] = message;
            //////////////////

            int x = (int)(0);
            int y = (int)(handler.getHeight()/2);
            int width = (int)(handler.getWidth()/2);
            int height = (int)(handler.getHeight()/2);
            int[] locationAndSize = { x, y, width, height };
            //////////////////////////
            args[1] = locationAndSize;
            //////////////////////////

            //args[1] = null;
            //handler.getStateManager().change(StateManager.GameState.TEXT_BOX, null);
            handler.getStateManager().change(StateManager.GameState.TEXT_BOX, args);
            /////////////////////////////////////////////////////////////////////
        }
        // KeyEvent.VK_ESCAPE       //TEXTBOXSTATE escape
        if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) &&
                (handler.getStateManager().getCurrentState() instanceof TextboxState)) {
            /////////////////////////////////////////////////////////////////////
            handler.getStateManager().popIState();
            /////////////////////////////////////////////////////////////////////
        }
        //TODO: redo sign post tile.
        // KeyEvent.VK_ESCAPE       //SIGNPOSTTILE escape
        if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) &&
                (getTileCurrentlyFacing() instanceof SignPostTile)) {
            /////////////////////////////////////////////////////////////////////
            ((SignPostTile) getTileCurrentlyFacing()).setExecuting(false);
            /////////////////////////////////////////////////////////////////////
        }

        // INVENTORY CHECK
        if (inventory.isActive()) {
            return;
        }

        // RUNNING
        if (handler.getKeyManager().running) {  //KeyEvent.VK_CONTROL
            if ((speed *= 2) < speedMax) {
                speed *= 2;
            } else {
                speed = speedMax;
            }
        } else {
            speed = Creature.DEFAULT_SPEED;
        }

//////////////////////////////////////////////
        // KEY INPUT to SET MOVEMENT
        if (handler.getKeyManager().up) {
            yMove = -speed;
            hitBoulderCounter = 0;
            hitTreeStumpCounter = 0;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
            hitBoulderCounter = 0;
            hitTreeStumpCounter = 0;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
            hitBoulderCounter = 0;
            hitTreeStumpCounter = 0;
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
            hitBoulderCounter = 0;
            hitTreeStumpCounter = 0;
        }
///////////////////////////////////////////////

        //////////////////// @@@@@@@@@@@@@@ STOPPING LEGACY DIAGONAL MOVEMENT BUG @@@@@@@@@@@@@@ ////////////////////
        if ((currentDirection == DirectionFacing.UPLEFT) || (currentDirection == DirectionFacing.UPRIGHT) ||
                (currentDirection == DirectionFacing.DOWNLEFT) || (currentDirection == DirectionFacing.DOWNRIGHT)) {
            return;
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ /////////////////
        // B BUTTON
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            inventory.incrementSelectedItem();
            sfxBButtonPressed.play();
        }

        // A BUTTON
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {

            //DEADCOW explosion
            if (getEntityCurrentlyFacing() instanceof DeadCow) {
                ((DeadCow)getEntityCurrentlyFacing()).setClicked(true);
            }

            //TODO: move to Hammer.execute().
            // CHECK BOULDER AND HAMMER (6 consecutive hits without moving)
            if (inventory.getItem(inventory.getIndex()) instanceof Hammer) {
                if (getEntityCurrentlyFacing() instanceof Boulder) {
                    hitBoulderCounter++;
                    staminaModule.decreaseStaminaCurrent(2);
                    System.out.println("player's stamina decrease by 2");

                    if (hitBoulderCounter == 6) {
                        //TODO: possible bug where Hammer.execute()'s call to Player.decreaseStaminaCurrent(2) is redundant.
                        ((Hammer)inventory.getItem(inventory.getIndex())).execute();
                        hitBoulderCounter = 0;
                    }

                    return;
                } else if ((getEntityCurrentlyFacing() instanceof Rock) || (getEntityCurrentlyFacing() instanceof RockMountain)) {
                    ((Hammer)inventory.getItem(inventory.getIndex())).execute();
                    return;
                }
            }

            //TODO: move to Axe.execute().
            // CHECK TREESTUMP AND AXE (6 consecutive hits without moving)
            if (inventory.getItem(inventory.getIndex()) instanceof Axe) {
                if (getEntityCurrentlyFacing() instanceof TreeStump) {
                    hitTreeStumpCounter++;
                    staminaModule.decreaseStaminaCurrent(2);
                    System.out.println("player's stamina decrease by 2");

                    if (hitTreeStumpCounter == 6) {
                        //TODO: possible bug where Axe.execute()'s call to Player.decreaseStaminaCurrent(2) is redundant.
                        ((Axe)inventory.getItem(inventory.getIndex())).execute();
                        hitTreeStumpCounter = 0;
                    }

                    return;
                } else if (getEntityCurrentlyFacing() instanceof Wood) {
                    ((Axe)inventory.getItem(inventory.getIndex())).execute();
                    return;
                }
            }

            // TRAVELINGFENCE CHECK
            if (handler.getStateManager().getCurrentState() ==
                    handler.getStateManager().getIState(StateManager.GameState.GAME) &&
                    checkForTravelingFence()) {
                System.out.println("FOUND: The Finn!");

                Object[] args = new Object[10];
                args[0] = this;
                args[1] = (int) x;
                args[2] = (int) y;
                /////////////////////////////////////////////////////////////////////
                handler.getStateManager().change( StateManager.GameState.TRAVELING_FENCE, args );
                /////////////////////////////////////////////////////////////////////
                return;
            }
            // SIGN-POST-TILE CHECK
            else if (getTileCurrentlyFacing() instanceof SignPostTile) {
                ((SignPostTile)getTileCurrentlyFacing()).execute();
            }
            // FODDER-STASH-TILE CHECK
            else if (getTileCurrentlyFacing() instanceof FodderStashTile) {
                ((FodderStashTile)getTileCurrentlyFacing()).execute();
            }
            // FODDER-EXECUTOR-TILE CHECK
            else if (getTileCurrentlyFacing() instanceof FodderExecutorTile) {
                ((FodderExecutorTile)getTileCurrentlyFacing()).execute();
            }
            // EGG-INCUBATOR-TILE CHECK
            else if ((getTileCurrentlyFacing() instanceof EggIncubatorTile) && (holdableObject instanceof Egg)) {
                ((EggIncubatorTile)getTileCurrentlyFacing()).execute();
                holdableObject.dropped(getTileCurrentlyFacing());
                setHoldableObject(null);
                holding = false;
            }
            // WOOD-STASH-TILE CHECK
            else if (getTileCurrentlyFacing() instanceof WoodStashTile) {
                ((WoodStashTile)getTileCurrentlyFacing()).execute();
            }
            // BED-TILE CHECK
            else if (getTileCurrentlyFacing() instanceof BedTile) {
                ((BedTile)getTileCurrentlyFacing()).execute();
            }
            // HOT-SPRING-MOUNTAIN-TILE CHECK
            else if (getTileCurrentlyFacing() instanceof HotSpringMountainTile) {
                ((HotSpringMountainTile)getTileCurrentlyFacing()).execute();
            }

            // HOLDING CHECK
            if (holding) {  // Already holding: can only drop the holdableObject.
                // if ShippingBin tile... store in ArrayList<HarvestEntity> until 5pm.
                if (getEntityCurrentlyFacing() != null && getEntityCurrentlyFacing() instanceof ShippingBin) {
                    if (holdableObject instanceof ISellable) {
                        /////////////////////////////////////////////////////
                        // TODO: HORSE SADDLE BAG - MOVEABLE SHIPPING BIN. //
                        /////////////////////////////////////////////////////
                        ((ISellable) holdableObject).dropIntoShippingBin((ShippingBin)getEntityCurrentlyFacing());
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
                if (checkForHoldable()) {   // Check if IHoldable in front: pick up if true.
                    if (!holding) {
                        //////////////////////////////////////
                        setHoldableObject(pickUpHoldable());
                        holdableObject.pickedUp();
                        holding = true;
                        //////////////////////////////////////
                    }
                } else {
                    // Not holding IHoldable, no IHoldable in front: use selected item.
                    // |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|
                    inventory.getItem(inventory.getIndex()).execute();
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

        // If FodderExecutorTile.
        if ((tempTileInFront instanceof FodderExecutorTile) && (holdableObject instanceof Fodder)) {
            return true;
        }
        // If DirtNormalTile.
        else if (tempTileInFront instanceof DirtNormalTile) {
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
        Rectangle cb = getCollisionBounds(0, 0);    // player's collision box (center square)

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

    public IHoldable pickUpHoldable() {
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
                        System.out.println("IHoldable object in front of player.");
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

        // ATTACK
        meleeAttackModule.render(g);

        // HUD
        headUpDisplayer.render(g);
    }

    /**
     * called inside EntityManager.render(Graphics).
     */
    public void postRender(Graphics g) {
        inventory.render(g);                // KeyEvent.VK_I

        if (holdableObject instanceof Fodder) {
            ((Fodder)holdableObject).render(g);
        }
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

    //TODO: the only way to access Game.gameStop() is never called.
    public void increaseCannabisCollected() {
        cannabisCollected++;
        sfxCannabisCollected.play();

        // CANNABIS COUNTER ( !!!!! checks for WINNER STATE !!!!! )
        checkWinningConditions();
    }

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

    public boolean isHolding() {
        return holding;
    }

    public void setHolding(boolean holding) {
        this.holding = holding;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public StaminaModule getStaminaModule() { return staminaModule; }

    public AttackModule getMeleeAttackModule() { return meleeAttackModule; }

    public HeadUpDisplayer getHeadUpDisplayer() { return headUpDisplayer; }

} // **** end Player class ****