package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.main.Game;

import java.awt.*;

public class GameState extends State {

    private Player player;

    public GameState(Game game) {
        super(game);
        player = new Player(game, 100, 100);
    } // **** end GameState() constructor ****

    @Override
    public void tick() {
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
    }

} // **** end GameState class ****