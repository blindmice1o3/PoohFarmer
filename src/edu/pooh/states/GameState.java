package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.main.Game;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import javax.swing.*;
import java.awt.*;

public class GameState implements State {

    private Game game;
    private Player player;
    private World world;

    public GameState(Game game) {
        this.game = game;
        player = new Player(game,64, 0);
        world = new World(game, "res/worlds/chapter1.txt");
        //setDoubleBuffered(true);      // JPanel
        //setFocusable(false);          // JPanel
    } // **** end GameState(Game) constructor ****

    @Override
    public void tick() {
        world.tick();
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
    }

    /* // JPanel
    @Override
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);

        Tile.tiles[0].render(g, 0, 0);
        //player.render(g);
    }
    */

} // **** end GameState class ****