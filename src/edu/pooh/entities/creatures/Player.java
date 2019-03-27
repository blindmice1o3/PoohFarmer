package edu.pooh.entities.creatures;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;

import java.awt.*;

public class Player extends Creature {

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        bounds.x = 16;
        bounds.y = 32;
        bounds.width = 32;
        bounds.height = 32;
    } // **** end Player(Handler, float, float) constructor ****

    @Override
    public void tick() {
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
        g.drawImage(Assets.playerDefault, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
        g.setColor(Color.RED);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),
                (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
    }

} // **** end Player class ****