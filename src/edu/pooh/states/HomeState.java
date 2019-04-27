package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
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

    public HomeState(Handler handler) {
        this.handler = handler;

        world = new World(handler, World.WorldType.HOME);
    } // **** end HomeState(Handler) constructor ****

    @Override
    public void enter(Object[] args) {
        handler.setWorld(world);
        player = (Player)args[0];

        this.args = args;

        /////////////////////////////////////////////////////////////////////////////////////
        player.setPosition(world.getPlayerSpawnX() * Tile.TILE_WIDTH,
                world.getPlayerSpawnY() * Tile.TILE_HEIGHT);
        /////////////////////////////////////////////////////////////////////////////////////

        world.getEntityManager().addEntity(player);
        world.getEntityManager().setPlayer(player);
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
            StateManager.change(handler.getGame().getGameState(), args);
        }

        ///////////////
        player.tick();
        ///////////////

        if ( player.getCollisionBounds(0, 0).intersects(world.getTransferPointHomeToGame()) ) {
            StateManager.change(handler.getGame().getGameState(), args);
            ///////////
        }
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
        //g.drawImage(Assets.homeStateBackground, 0, 0, Game.WIDTH_OF_FRAME, Game.HEIGHT_OF_FRAME, null);
        world.render(g);

        // Render player image.
        //player.render(g);
    }

} // **** end HomeState class ****