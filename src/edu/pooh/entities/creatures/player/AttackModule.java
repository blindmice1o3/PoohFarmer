package edu.pooh.entities.creatures.player;

import edu.pooh.entities.Entity;
import edu.pooh.main.Handler;

import java.awt.*;

public class AttackModule {

    private Handler handler;
    private Player player;

    // ATTACK TIMER
    private long lastAttackTimer;
    private long attackCooldown = 800;  // 800 milliseconds
    private long attackTimer = attackCooldown;

    // MELEE ATTACK
    private Rectangle cb; // player's collision/bounding box
    private Rectangle ar; // attack-rectangle
    private int arSize = 20;
    private boolean attacking = false;

    public AttackModule(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        // MELEE ATTACK
        ar = new Rectangle();
        ar.width = arSize;
        ar.height = arSize;
    } // **** end AttackModule(Handler, Player) constructor ****

    public void tick() {
        // ATTACK
        if (!player.isHolding()) {         //if holding from GameState, moved into MountainState... cannot put down... cannot attack.
            checkAttacks();
        }
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
        if (player.getInventory().isActive()) {
            return;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // player's collision box (center square in color-penciled drawing/notes).
        cb = player.getCollisionBounds(0, 0);
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

    public void render(Graphics g) {
        // MELEE ATTACK
        if (attacking) {
            g.setColor(Color.RED);
            g.fillRect((int)(ar.x - handler.getGameCamera().getxOffset()),
                    (int)(ar.y - handler.getGameCamera().getyOffset()), ar.width, ar.height);
        }
    }

} // **** end AttackModule class ****