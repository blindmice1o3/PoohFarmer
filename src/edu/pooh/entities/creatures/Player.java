package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    // ANIMATIONS
    private Animation animDown;
    private Animation animUp;
    private Animation animLeft;
    private Animation animRight;

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
        g.setColor(Color.RED);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
                (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
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

} // **** end Player class ****