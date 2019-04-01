package edu.pooh.entities.creatures;

import edu.pooh.entities.Entity;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.inventory.Inventory;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    // ANIMATIONS
    private Animation animDown;
    private Animation animUp;
    private Animation animLeft;
    private Animation animRight;
    // ATTACK TIMER
    private long lastAttackTimer;
    private long attackCooldown = 800;  // 800 milliseconds
    private long attackTimer = attackCooldown;

    // INVENTORY
    private Inventory inventory;

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

        // INVENTORY
        inventory = new Inventory(handler);
    } // **** end Player(Handler, float, float) constructor ****

    @Override
    public void tick() {
        // ANIMATIONS
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();
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

        Rectangle cb = getCollisionBounds(0, 0);    // player's collision box (center square)
        Rectangle ar = new Rectangle();     // attack-rectangle
        int arSize = 20;                    // 20 pixels
        ar.width = arSize;
        ar.height = arSize;

        // Setting the coordinate of the attack rectangle
        // (attacking in one direction at a time [a bunch of if-else statements])
        if (handler.getKeyManager().aUp) {
            ar.x = cb.x + (cb.width / 2) - (arSize / 2);   // center x coordinate of our player's collision box
            ar.y = cb.y - arSize;
        } else if (handler.getKeyManager().aDown) {
            ar.x = cb.x + (cb.width / 2) - (arSize / 2);   // center x coordinate of our player's collision box
            ar.y = cb.y + cb.height;
        } else if (handler.getKeyManager().aLeft) {
            ar.x = cb.x - arSize;
            ar.y = cb.y + (cb.height / 2) - (arSize / 2);  // center y coordinate of collision box
        } else if (handler.getKeyManager().aRight) {
            ar.x = cb.x + cb.width;
            ar.y = cb.y + (cb.height / 2) - (arSize / 2);  // center y coordinate of collision box
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
                e.hurt(1);      // Successful attack collision, invoke hurt() method.
                return;
            }
        }
    }

    private void getInput() {
        // Important to reset xMove and yMove to 0 at start of getInput().
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up) { yMove = -speed; }
        if (handler.getKeyManager().down) { yMove = speed; }
        if (handler.getKeyManager().left) { xMove = -speed; }
        if (handler.getKeyManager().right) { xMove = speed; }

        //System.out.println("player's x,y: " + x + ", " + y);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
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
        if (xMove < 0) {                                // Moving left.
            return animLeft.getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return animRight.getCurrentFrame();
        } else if (yMove < 0) {                         // Moving up.
            return animUp.getCurrentFrame();
        } else if (yMove > 0) {                         // Moving down.
            return animDown.getCurrentFrame();
        } else {                                        // else/default: Standing still.
            return Assets.playerDefault;
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