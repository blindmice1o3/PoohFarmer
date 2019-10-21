package edu.pooh.main;

import edu.pooh.gfx.GameCamera;
import edu.pooh.input.KeyManager;
import edu.pooh.input.MouseManager;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.states.StateManager;
import edu.pooh.time.TimeManager;
import edu.pooh.worlds.World;

import java.io.Serializable;

public class Handler
        implements Serializable {

    private transient Game game;
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

    public MouseManager getMouseManager() { return game.getMouseManager(); }

    public StateManager getStateManager() { return game.getStateManager(); }

    public TimeManager getTimeManager() { return game.getTimeManager(); }

    public ResourceManager getResourceManager() { return game.getResourceManager(); }

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