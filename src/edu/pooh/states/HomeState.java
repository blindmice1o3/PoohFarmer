package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;
import edu.pooh.worlds.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HomeState implements IState {

    private Handler handler;
    private World world;

    private Object[] args;
    private Player player;


    private Rectangle mapTransferPointDoorOut;

    public HomeState(Handler handler) {
        this.handler = handler;
        args = new Object[5];

        world = new World(handler, "res/worlds/Game Boy GBC - Harvest Moon GBC - Home Background (rgb).png");
    } // **** end HomeState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        if ((args[0] != null) && (args[0] instanceof Player)) {
            player = (Player)args[0];
        }

    }

    @Override
    public void exit() {
        args = new Object[5];
        args[0] = getPlayer();
    }

    public Player getPlayer() {
        return player;
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