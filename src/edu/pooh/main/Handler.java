package edu.pooh.main;

import edu.pooh.gfx.GameCamera;
import edu.pooh.input.KeyManager;
import edu.pooh.worlds.World;

public class Handler {

    private Game game;
    private World world;

    public Handler(Game game) {
        this.game = game;
    } // **** end Handler(Game) constructor ****

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public int getWidth() {
        return Game.WIDTH_OF_FRAME;
    }

    public int getHeight() {
        return Game.HEIGHT_OF_FRAME;
    }

    // GETTERS & SETTERS

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

} // **** end Handler class ****