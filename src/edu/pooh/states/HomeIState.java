package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HomeIState implements IState {

    private Handler handler;
    private World world;
    private Player player;


    private Rectangle mapTransferPointDoorOut;

    public HomeIState(Handler handler) {
        this.handler = handler;
        world = new World(handler, "res/worlds/");
    } // **** end HomeIState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        player = (Player)args[0];

    }

    @Override
    public void exit() {

    }

    @Override
    public void tick() {
        if (StateManager.getCurrentIState() != handler.getGame().getHomeIState()) {
            return;
        }

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            StateManager.setCurrentIState(handler.getGame().getGameIState());
            player.setPosition(7* Tile.TILE_WIDTH, 18*Tile.TILE_HEIGHT);

        }
        player.tick();
        //checkMapTransferPoints();
    }

    public void checkMapTransferPoints() {
        /*
        Rectangle tempTransferPointBounds = new Rectangle(7* Tile.TILE_WIDTH, 17*Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        if (tempTransferPointBounds.intersects(entityManager.getPlayer().getCollisionBounds(0, 0))) {
            StateManager.setCurrentIState(handler.getGame().homeState);
        }
        */
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentIState() != handler.getGame().getHomeIState()) {
            return;
        }

        // Render background image.
        g.drawImage(Assets.homeStateBackground, 0, 0, Game.WIDTH_OF_FRAME, Game.HEIGHT_OF_FRAME, null);

        // Render player image.
        player.render(g);
    }

} // **** end HomeIState class ****