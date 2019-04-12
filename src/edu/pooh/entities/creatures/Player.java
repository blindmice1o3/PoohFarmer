package edu.pooh.entities.creatures;

import edu.pooh.entities.Entity;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.Inventory;
import edu.pooh.items.Item;
import edu.pooh.items.tier0.WateringCan;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Creature {

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

    //

    // MELEE ATTACK
    private Rectangle cb;
    private Rectangle ar; // attack-rectangle
    private int arSize = 20;
    private boolean attacking = false;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        bounds.x = 15;
        bounds.y = 20;
        bounds.width = 34;
        bounds.height = 44;

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

        // MELEE ATTACK
        ar = new Rectangle();
        ar.width = arSize;
        ar.height = arSize;
    } // **** end Player(Handler, float, float) constructor ****

    @Override
    public void tick() {
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



        // ATTACK
        checkAttacks();
        // INVENTORY
        inventory.tick();
    }

    private void checkAttacks() {
        // Use attack timer to check eligibility for new attack.
        attackTimer += System.currentTimeMillis() - lastAttackTimer;    // time elapsed
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown) {
            return;
        }

        attacking = false;

        if (inventory.isActive()) {
            return;
        }

        cb = getCollisionBounds(0, 0);    // player's collision box (center square)


        // Setting the coordinate of the attack rectangle
        // (attacking in one direction at a time [a bunch of if-else statements])
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

        attackTimer = 0;

        // The above attack rectangle coordinates were set to some values
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this)) {   // Find the player.
                continue;   // Skip the rest of the code and move on to the next Entity in the entities ArrayList.
            }

            // We have an Entity object that isn't the player, check if it intersects with the attack rectangle.
            if (e.getCollisionBounds(0, 0).intersects(ar)) {
                e.hurt(2);      // Successful attack collision, invoke hurt() method.
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

        // A/B BUTTONS
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            inventory.incrementSelectedItem();
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            inventory.getItem(inventory.getSelectedItem()).execute();
        }
    }

    @Override
    public void render(Graphics g) {
        // MOVEMENT
        g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);

        // MELEE ATTACK
        if (attacking) {
            g.setColor(Color.RED);
            g.fillRect((int)(ar.x - handler.getGameCamera().getxOffset()),
                    (int)(ar.y - handler.getGameCamera().getyOffset()), ar.width, ar.height);
        }

        // HUD (Head-Up-Display)
        g.setColor(Color.BLUE);
        g.drawRect((Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) - 2), 25 - 2,
                (Item.ITEM_WIDTH + 3), (Item.ITEM_HEIGHT + 3));
        g.drawImage( inventory.getItem(inventory.getSelectedItem()).getTexture(),
                (Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH)), 25,
                Item.ITEM_WIDTH, Item.ITEM_HEIGHT, null);
        if (inventory.getItem(inventory.getSelectedItem()).getId() == Item.ID.WATERING_CAN) {
            WateringCan temp = (WateringCan)inventory.getItem(inventory.getSelectedItem());
            Text.drawString(g, Integer.toString(temp.getCountWater()),
                    (Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) + (Item.ITEM_WIDTH / 2)),
                    25 + Item.ITEM_HEIGHT + 15, true, Color.BLUE, Assets.font28);
        } else {
            Text.drawString(g, Integer.toString(inventory.getItem(inventory.getSelectedItem()).getCount()),
                    (Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) + (Item.ITEM_WIDTH / 2)),
                    25 + Item.ITEM_HEIGHT + 15, true, Color.BLUE, Assets.font28);
        }

        // COLLISION BOX
        //g.setColor(Color.RED);
        //g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
        //        (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
        //        bounds.width, bounds.height);
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

    // GETTERS & SETTERS

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

} // **** end Player class ****