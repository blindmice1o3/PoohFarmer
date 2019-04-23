package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HomeState implements State {

    private Handler handler;
    private Player player;
    private Rectangle mapTransferPointDoorOut;

    public HomeState(Handler handler) {
        this.handler = handler;
        player = handler.getWorld().getEntityManager().getPlayer();

    } // **** end HomeState(Handler) constructor ****

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void tick() {
        if (StateManager.getCurrentState() != handler.getGame().getHomeState()) {
            return;
        }

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            StateManager.setCurrentState(handler.getGame().getGameState());
            player.setPosition(7* Tile.TILE_WIDTH, 18*Tile.TILE_HEIGHT);

        }
        player.tick();
        //checkMapTransferPoints();
    }

    public void checkMapTransferPoints() {
        /*
        Rectangle tempTransferPointBounds = new Rectangle(7* Tile.TILE_WIDTH, 17*Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
        if (tempTransferPointBounds.intersects(entityManager.getPlayer().getCollisionBounds(0, 0))) {
            StateManager.setCurrentState(handler.getGame().homeState);
        }
        */
    }

    @Override
    public void render(Graphics g) {
        if (StateManager.getCurrentState() != handler.getGame().getHomeState()) {
            return;
        }

        // Render background image.
        g.drawImage(Assets.homeStateBackground, 0, 0, Game.WIDTH_OF_FRAME, Game.HEIGHT_OF_FRAME, null);

        // Render player image.
        player.render(g);
    }

} // **** end HomeState class ****