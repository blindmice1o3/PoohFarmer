package edu.pooh.entities.creatures;

import edu.pooh.entities.Entity;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;

public abstract class Creature extends Entity {

    // CONSTANTS

    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 64;
    public static final int DEFAULT_CREATURE_HEIGHT = 64;

    public enum DirectionFacing {
        LEFT, RIGHT, UP, DOWN, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT;
    }

    protected DirectionFacing currentDirection;
    protected float speed;
    protected float xMove;
    protected float yMove;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        currentDirection = DirectionFacing.DOWN;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    } // **** end Creature(Handler, float, float, int, int) constructor ****

    public abstract void initAnimations();

    public Tile getTileCurrentlyStandingOn() {
        int creatureCenterX = (int)(x + (width / 2));
        int creatureCenterY = (int)(y + (height / 2));

        return handler.getWorld().getTile( (creatureCenterX / Tile.TILE_WIDTH), (creatureCenterY / Tile.TILE_HEIGHT) );
    }

    public Tile[] getTiles3x3FromCenter(int x, int y) {
        Tile[] tiles3x3 = new Tile[9];

        tiles3x3[0] = handler.getWorld().getTile( ((x / Tile.TILE_WIDTH) - 1),  ((y / Tile.TILE_HEIGHT) - 1) );
        tiles3x3[1] = handler.getWorld().getTile( (x / Tile.TILE_WIDTH),        ((y / Tile.TILE_HEIGHT) - 1) );
        tiles3x3[2] = handler.getWorld().getTile( ((x / Tile.TILE_WIDTH) + 1),  ((y / Tile.TILE_HEIGHT) - 1) );

        tiles3x3[3] = handler.getWorld().getTile( ((x / Tile.TILE_WIDTH) - 1),  (y / Tile.TILE_HEIGHT) );
        tiles3x3[4] = handler.getWorld().getTile( (x / Tile.TILE_WIDTH),        (y / Tile.TILE_HEIGHT) );
        tiles3x3[5] = handler.getWorld().getTile( ((x / Tile.TILE_WIDTH) + 1),  (y / Tile.TILE_HEIGHT) );

        tiles3x3[6] = handler.getWorld().getTile( ((x / Tile.TILE_WIDTH) - 1),  ((y / Tile.TILE_HEIGHT) + 1));
        tiles3x3[7] = handler.getWorld().getTile( (x / Tile.TILE_WIDTH),        ((y / Tile.TILE_HEIGHT) + 1));
        tiles3x3[8] = handler.getWorld().getTile( ((x / Tile.TILE_WIDTH) + 1),  ((y / Tile.TILE_HEIGHT) + 1));

        return tiles3x3;
    }

    public Entity getEntityCurrentlyFacing() {
        Entity tempEntityReturner = null;

        int creatureCenterX = (int)(x + (width / 2));
        int creatureCenterY = (int)(y + (height / 2));

        Rectangle entityCollisionBox = new Rectangle();
        switch (currentDirection) {
            case DOWN:
                entityCollisionBox.x = (creatureCenterX-(Tile.TILE_WIDTH/4));
                entityCollisionBox.y = (creatureCenterY+(Tile.TILE_HEIGHT/2)+((int)(0.3)*Tile.TILE_HEIGHT));
                entityCollisionBox.width = (Tile.TILE_WIDTH/2);
                entityCollisionBox.height = (Tile.TILE_HEIGHT/2);
                break;
            case UP:
                entityCollisionBox.x = (creatureCenterX-(Tile.TILE_WIDTH/4));
                entityCollisionBox.y = (creatureCenterY-((int)(1.4)*Tile.TILE_HEIGHT));
                entityCollisionBox.width = (Tile.TILE_WIDTH/2);
                entityCollisionBox.height = (Tile.TILE_HEIGHT/2);
                break;
            case LEFT:
                entityCollisionBox.x = (creatureCenterX-((int)(1.4)*Tile.TILE_WIDTH));
                entityCollisionBox.y = (creatureCenterY-(Tile.TILE_HEIGHT/4));
                entityCollisionBox.width = (Tile.TILE_WIDTH/2);
                entityCollisionBox.height = (Tile.TILE_HEIGHT/2);
                break;
            case RIGHT:
                entityCollisionBox.x = (creatureCenterX+(Tile.TILE_WIDTH/2)+((int)(0.3)*Tile.TILE_WIDTH));
                entityCollisionBox.y = (creatureCenterY-(Tile.TILE_HEIGHT/4));
                entityCollisionBox.width = (Tile.TILE_WIDTH/2);
                entityCollisionBox.height = (Tile.TILE_HEIGHT/2);
                break;
            default:
                break;
        }

        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }

            if (entityCollisionBox.intersects(e.getCollisionBounds(0, 0))) {
                tempEntityReturner = e;
            }
        }

        return tempEntityReturner;
    }

    public Tile getTileCurrentlyFacing() {
        int creatureCenterX = (int)(x + (width / 2));
        int creatureCenterY = (int)(y + (height / 2));

        switch (currentDirection) {
            case DOWN:
                return handler.getWorld().getTile( (creatureCenterX / Tile.TILE_WIDTH),
                        ((creatureCenterY + Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT) );
            case UP:
                return handler.getWorld().getTile( (creatureCenterX / Tile.TILE_WIDTH),
                        ((creatureCenterY - Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT) );
            case LEFT:
                return handler.getWorld().getTile( ((creatureCenterX - Tile.TILE_WIDTH) / Tile.TILE_WIDTH),
                        (creatureCenterY / Tile.TILE_HEIGHT) );
            case RIGHT:
                return handler.getWorld().getTile( ((creatureCenterX + Tile.TILE_WIDTH) / Tile.TILE_WIDTH),
                        (creatureCenterY / Tile.TILE_HEIGHT) );
            default:
                return null;
        }
    }

    public void move() {
        // Set currentDirection relative to most recent input (xMove and yMove being positive or negative or 0).
        if (yMove < 0 && xMove > 0) { currentDirection = DirectionFacing.UPRIGHT; }
        else if (yMove < 0 && xMove < 0) { currentDirection = DirectionFacing.UPLEFT; }
        else if (yMove > 0 && xMove > 0) { currentDirection = DirectionFacing.DOWNRIGHT; }
        else if (yMove > 0 && xMove < 0) { currentDirection = DirectionFacing.DOWNLEFT; }
        else if (xMove > 0) { currentDirection = DirectionFacing.RIGHT; }
        else if (xMove < 0) { currentDirection = DirectionFacing.LEFT; }
        else if (yMove > 0) { currentDirection = DirectionFacing.DOWN; }
        else if (yMove < 0) { currentDirection = DirectionFacing.UP;}

        if (!checkEntityCollision(xMove, 0f)) {
            moveX();
        }
        if (!checkEntityCollision(0f, yMove)) {
            moveY();
        }
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

    public DirectionFacing getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(DirectionFacing currentDirection) {
        this.currentDirection = currentDirection;
    }

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