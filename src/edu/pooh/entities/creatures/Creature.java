package edu.pooh.entities.creatures;

import edu.pooh.entities.Entity;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

public abstract class Creature extends Entity {

    // CONSTANTS

    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 64;
    public static final int DEFAULT_CREATURE_HEIGHT = 64;

    protected int health;
    protected float speed;
    protected float xMove;
    protected float yMove;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    } // **** end Creature(Handler, float, float, int, int) constructor ****

    public void move() {
        moveX();
        moveY();
    }

    public void moveX() {
        if (xMove > 0) {                                                                    //Moving right.
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;

            //upper-right corner && lower-right corner
            if (!collisionWithTile(tx, (int)(y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
                x += xMove;
            } else {    //there was collision with a tile while moving right (we are inside a tile).
                x = (tx * Tile.TILE_WIDTH) - bounds.x - bounds.width - 1; //the -1 is to enable up/down movement.
            }
        } else if (xMove < 0) {                                                             //Moving left.
            int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;

            //upper-left corner && lower-left corner
            if (!collisionWithTile(tx, (int)(y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
                x += xMove;
            } else {    //there was collision with a tile while moving left (we are inside a tile).
                x = (tx * Tile.TILE_WIDTH) + Tile.TILE_WIDTH - bounds.x;
            }
        }
    }

    public void moveY() {
        if (yMove < 0) {                                                                    //Moving up.
            int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;

            //upper-left corner && upper-right corner
            if (!collisionWithTile((int)(x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
                y += yMove;
            } else { //there was collision with a tile while moving up (we are inside a tile).
                y = (ty * Tile.TILE_HEIGHT) + Tile.TILE_HEIGHT - bounds.y;
            }
        } else if (yMove > 0) {                                                             //Moving down.
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;

            //upper-left corner && upper-right corner
            if (!collisionWithTile((int)(x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
                y += yMove;
            } else { //there was collision with a tile while moving down (we are inside a tile).
                y = (ty * Tile.TILE_HEIGHT) - bounds.y - bounds.height - 1;
            }
        }
    }

    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    // GETTERS & SETTERS

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

} // **** end Creature abstract class ****