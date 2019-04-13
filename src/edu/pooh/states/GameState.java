package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.statics.Bush;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;

public class GameState implements State {

    private Handler handler;
    //private Player player;
    private World world;
    //private Bush bush;

    public GameState(Handler handler) {
        this.handler = handler;
        world = new World(handler, "res/worlds/chapter1 - tiles (int).txt");
        handler.setWorld(world);    // IMPORTANT TO DO IN THIS ORDER, create world, then handler's setWorld().
        //player = new Player(handler, world.getSpawnX() * Tile.TILE_WIDTH, world.getSpawnY() * Tile.TILE_HEIGHT);
        //bush = new Bush(handler, 100, 200);
        //setDoubleBuffered(true);      // JPanel
        //setFocusable(false);          // JPanel
    } // **** end GameState(Handler) constructor ****

    @Override
    public void tick() {
        world.tick();
        //player.tick();
        //bush.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        //player.render(g);
        //bush.render(g);
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