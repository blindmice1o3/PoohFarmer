package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.main.Game;
import edu.pooh.tiles.Tile;

import javax.swing.*;
import java.awt.*;

public class GameState extends JPanel
                    implements State {

    private Game game;
    private Player player;

    public GameState(Game game) {
        this.game = game;
        player = new Player(game,64, 0);

        setDoubleBuffered(true);
        setFocusable(false);
    } // **** end GameState(Game) constructor ****

    @Override
    public void tick() {
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);

        Tile.tiles[0].render(g, 0, 0);
        //player.render(g);
    }

} // **** end GameState class ****