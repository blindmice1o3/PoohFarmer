package edu.pooh.entities.creatures;

import edu.pooh.entities.Entity;
import edu.pooh.entities.statics.harvests.HarvestEntity;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.Inventory;
import edu.pooh.items.Item;
import edu.pooh.items.tier0.WateringCan;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.main.TimeManager;
import edu.pooh.sfx.SoundManager;
import edu.pooh.states.StateManager;
import edu.pooh.tiles.BedTile;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    // CANNABIS COUNTER
    private int cannabisCollected;
    public static final AudioClip sfxCannabisCollected = SoundManager.sounds[0];
    public static final AudioClip sfxBButtonPressed = SoundManager.sounds[1];

    // ANIMATIONS
    private Animation animDown;
    private Animation animUp;
    private Animation animLeft;
    private Animation animRight;
    private Animation animUpRight;
    private Animation animUpLeft;
    private Animation animDownRight;
    private Animation animDownLeft;

    // ATTACK TIMER
    private long lastAttackTimer;
    private long attackCooldown = 800;  // 800 milliseconds
    private long attackTimer = attackCooldown;

    // INVENTORY
    private Inventory inventory;

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


    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        bounds.x = 15;
        bounds.y = 20;
        bounds.width = 34;
        bounds.height = 44;

        // CANNABIS COUNTER
        cannabisCollected = 0;

        // ANIMATIONS
        animDown = new Animation(60, Assets.playerDown);
        animUp = new Animation(60, Assets.playerUp);
        animLeft = new Animation(60, Assets.playerLeft);
        animRight = new Animation(60, Assets.playerRight);
        animUpRight = new Animation(60, Assets.playerUpRight);
        animUpLeft = new Animation(60, Assets.playerUpLeft);
        animDownRight = new Animation(60, Assets.playerDownRight);
        animDownLeft = new Animation(60, Assets.playerDownLeft);

        // INVENTORY
        inventory = new Inventory(handler);

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

    @Override
    public void tick() {
        // CANNABIS COUNTER ( !!!!! checks for WINNER STATE !!!!! )
        if (cannabisCollected == 3) {
            StateManager.change( handler.getGame().getMenuState(), new Object[5] );
            sfxCannabisCollected.play();
            //handler.getGame().gameStop();
        }

        // ANIMATIONS
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();
        animUpRight.tick();
        animUpLeft.tick();
        animDownRight.tick();
        animDownLeft.tick();


        // MOVEMENT
        getInput(); // Sets the xMove and yMove variables.
        move();     // Changes the x and y coordinates of the player based on xMove and yMove variables.
        handler.getGameCamera().centerOnEntity(this);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //if (StateManager.getCurrentState() != handler.getGame().getGameState() &&
        //        StateManager.getCurrentState() != handler.getGame().getMountainState()) {
        //    return;
        //}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    private void getInput() {
        // Important to reset xMove and yMove to 0 at start of getInput().
        xMove = 0;
        yMove = 0;

        // INVENTORY CHECK
        if (inventory.isActive()) {
            return;
        }

        // KEY INPUT to SET MOVEMENT
        if (handler.getKeyManager().up) { yMove = -speed; }
        if (handler.getKeyManager().down) { yMove = speed; }
        if (handler.getKeyManager().left) { xMove = -speed; }
        if (handler.getKeyManager().right) { xMove = speed; }

        ///////////////// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ /////////////////

        // !!!!! GAMESTATE SPECIFIC KEY ASSIGNMENTS !!!!!
        if (StateManager.getCurrentState() == handler.getGame().getGameState() ||
                StateManager.getCurrentState() == handler.getGame().getChickenCoopState() ||
                StateManager.getCurrentState() == handler.getGame().getHomeState()) {

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
                    // TODO: Implement TravelingFenceState.
                    /////////////////////////////////////////////////////////////////////
                    StateManager.setCurrentState(handler.getGame().getTravelingFenceState());
                    /////////////////////////////////////////////////////////////////////
                    return;
                }

                // HOLDING CHECK
                if (holding) {  // Already holding, can only drop the holdableObject.
                    if (checkDropableTile()) {

                        //if (holdableObject instanceof HarvestEntity) {
                        //  if (getTileCurrentlyFacing() instanceof DirtNormalTile) {
                        //      ((HarvestEntity)holdableObject).setTexture(((HarvestEntity) holdableObject).determineFragmentedTexture());
                        //  }
                        //}
                        /////////////////////////////////////////////////
                        holdableObject.dropped(getTileCurrentlyFacing());

                        // TODO: Dropped HarvestEntity Object should render an image of itself broken and then setActive(false).
                        //if (getTileCurrentlyFacing() instanceof DirtNormalTile) {
                        //((DirtNormalTile)getTileCurrentlyFacing()).checkRemoveFragmentedStaticEntity();
                        //} else {

                        //}

                        setHoldableObject(null);
                        holding = false;
                        /////////////////////////////////////////////////

                    }
                } else {        // Not holding IHoldable.
                    if (checkForHoldable()) {   // Check if IHoldable in front, pick up if true.
                        if (!holding) {
                            //////////////////////////////////////
                            setHoldableObject(pickUpHoldable());
                            holdableObject.pickedUp();
                            holding = true;
                            //////////////////////////////////////
                        }
                    } else if (getTileCurrentlyFacing() instanceof BedTile) {
                        //System.out.println("waahoo");
                        ((BedTile)getTileCurrentlyFacing()).execute();
                    } else { // Not holding IHoldable, no IHoldable in front, not bed tile in front, use selected item.

                        // |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|
                        inventory.getItem(inventory.getSelectedItem()).execute();
                        // |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|

                    }
                }
            }
        }
    }

    private boolean checkForTravelingFence() {
        int playerCenterX = (int)(x + (width / 2));
        int playerCenterY = (int)(y + (height / 2));
        TravelingFence tempTravelingFence = null;

        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e instanceof TravelingFence) {
                tempTravelingFence = (TravelingFence)e;
            }
        }

        if (tempTravelingFence != null) {
            switch (currentDirection) {
                case DOWN:
                    if (tempTravelingFence.getCollisionBounds(0, 0).contains(
                            playerCenterX, playerCenterY + Tile.TILE_HEIGHT)) {
                        return true;
                    }
                case UP:
                    if (tempTravelingFence.getCollisionBounds(0, 0).contains(
                            playerCenterX, playerCenterY - Tile.TILE_HEIGHT)) {
                        return true;
                    }
                case LEFT:
                    if (tempTravelingFence.getCollisionBounds(0, 0).contains(
                            playerCenterX - Tile.TILE_WIDTH, playerCenterY)) {
                        return true;
                    }
                case RIGHT:
                    if (tempTravelingFence.getCollisionBounds(0, 0).contains(
                            playerCenterX + Tile.TILE_WIDTH, playerCenterY)) {
                        return true;
                    }
                default:
                    return false;
            }
        }

        return false;
    }

    private boolean checkDropableTile() {
        // If DirtNormalTile or chest.
        if (getTileCurrentlyFacing() instanceof DirtNormalTile) {
            return (((DirtNormalTile)getTileCurrentlyFacing()).getStaticEntity() == null);
        } else if (getTileCurrentlyFacing().getId() >= 232 && getTileCurrentlyFacing().getId() < 236) {
            return (holdableObject instanceof HarvestEntity);
        }
        // @@@@@@@@@@@@@
        else if (holdableObject instanceof Creature && !getTileCurrentlyFacing().isSolid()) {
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
        g.setColor(Color.RED);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
                (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);

        // MELEE ATTACK
        if (attacking) {
            g.setColor(Color.RED);
            g.fillRect((int)(ar.x - handler.getGameCamera().getxOffset()),
                    (int)(ar.y - handler.getGameCamera().getyOffset()), ar.width, ar.height);
        }

        // @@@@@@@@@@@@ LEAVE Player class's render(Graphics) EARLY IF NOT GAMESTATE @@@@@@@@@@@@@@
        if (StateManager.getCurrentState() != handler.getGame().getGameState()) {
            return;
        }
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        // HUD (Head-Up-Display)
        g.setColor(Color.BLUE);
        g.drawRect((Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) - 2), 25 - 2,
                (Item.ITEM_WIDTH + 3), (Item.ITEM_HEIGHT + 3));
        g.drawImage( inventory.getItem(inventory.getSelectedItem()).getTexture(),
                (Game.  WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH)), 25,
                Item.ITEM_WIDTH, Item.ITEM_HEIGHT, null);
        if (inventory.getItem(inventory.getSelectedItem()).getId() == Item.ID.WATERING_CAN) {
            WateringCan temp = (WateringCan)inventory.getItem(inventory.getSelectedItem());
            Text.drawString(g, Integer.toString(temp.getCountWater()),
                    (Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) + (Item.ITEM_WIDTH / 2)),
                    25 + Item.ITEM_HEIGHT + 15, true, Color.BLUE, Assets.font28);
        } else {
            Text.drawString(g, Integer.toString(inventory.getItem(inventory.getSelectedItem()).getCount()),
                    (Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) + (Item.ITEM_WIDTH / 2)),
                    25 + Item.ITEM_HEIGHT + 15, true, Color.YELLOW, Assets.font28);
        }
        g.setColor(Color.BLUE);
        g.drawRect((25 - 2), (25 - 2), (Item.ITEM_WIDTH + 3), (Item.ITEM_HEIGHT + 3));
        Text.drawString(g, Integer.toString(getCannabisCollected()),
                (25 + (Item.ITEM_WIDTH / 2)), (25 + (Item.ITEM_HEIGHT / 2)), true, Color.YELLOW, Assets.font28);
        Text.drawString(g, Integer.toString(TimeManager.elapsedRealSeconds), ((handler.getWidth() / 2) - 25), 30,
                true, Color.YELLOW, Assets.font28);
    }

    public void postRender(Graphics g) {
        inventory.render(g);
    }

    private BufferedImage getCurrentAnimationFrame() {
        // Horizontal (x axis) over vertical (y axis).

        // ANIMATION MOVEMENTS
        if (yMove < 0 && xMove > 0) {                   // Moving up-right.
            return animUpRight.getCurrentFrame();
        } else if (yMove < 0 && xMove < 0) {            // Moving up-left.
            return animUpLeft.getCurrentFrame();
        } else if (yMove > 0 && xMove > 0) {            // Moving down-right.
            return animDownRight.getCurrentFrame();
        } else if (yMove > 0 && xMove < 0) {            // Moving down-left.
            return animDownLeft.getCurrentFrame();
        } else if (xMove < 0) {                         // Moving left.
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