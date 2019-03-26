package edu.pooh.gfx;

import edu.pooh.entities.Entity;
import edu.pooh.main.Game;

public class GameCamera {

    private Game game;
    private float xOffset;
    private float yOffset;

    public GameCamera(Game game, float xOffset, float yOffset) {
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    } // **** end GameCamera(Game, float, float) constructor ****

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - (Game.WIDTH_OF_FRAME / 2) + (e.getWidth() / 2);
        yOffset = e.getY() - (Game.HEIGHT_OF_FRAME / 2) + (e.getHeight() / 2);
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
    }

    // GETTERS & SETTERS

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

} // **** end GameCamera class ****