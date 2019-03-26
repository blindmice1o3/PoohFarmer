package edu.pooh.entities.creatures;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Game;

import java.awt.*;

public class Player extends Creature {

    private Game game;

    public Player(Game game, float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;
    } // **** end Player(Game, float, float) constructor ****

    @Override
    public void tick() {
        getInput(); // Sets the xMove and yMove variables.
        move();     // Changes the x and y coordinates of the player based on xMove and yMove variables.
    }

    private void getInput() {
        // Important to reset xMove and yMove to 0 at start of getInput().
        xMove = 0;
        yMove = 0;

        if (game.getKeyManager().up) { yMove = -speed; }
        if (game.getKeyManager().down) { yMove = speed; }
        if (game.getKeyManager().left) { xMove = -speed; }
        if (game.getKeyManager().right) { xMove = speed; }

        //System.out.println("player's x,y: " + x + ", " + y);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.signPost, (int)x, (int)y, width, height, null);
    }

} // **** end Player class ****