package edu.pooh.states;

import edu.pooh.gfx.Assets;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;

public class HomeState implements State {

    private Handler handler;

    public HomeState(Handler handler) {
        this.handler = handler;
    } // **** end HomeState(Handler) constructor ****

    @Override
    public void tick() {
        checkMapTransferPoints();
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
        g.drawImage(Assets.homeStateBackground, 0, 0, Game.WIDTH_OF_FRAME, Game.HEIGHT_OF_FRAME, null);
    }

} // **** end HomeState class ****