package edu.pooh.entities.creatures;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Game;

import java.awt.*;

public class Player extends Creature {

    private Game game;

    public Player(Game game, float x, float y) {
        super(x, y);
        this.game = game;
    } // **** end Player(Game, float, float) constructor ****

    @Override
    public void tick() {
        if (game.getKeyManager().up) { y -= 3; }
        if (game.getKeyManager().down) { y += 3; }
        if (game.getKeyManager().left) { x -= 3; }
        if (game.getKeyManager().right) { x += 3; }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.sign, (int)x, (int)y, null);
    }

} // **** end Player class ****