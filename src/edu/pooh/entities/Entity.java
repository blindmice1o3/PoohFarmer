package edu.pooh.entities;

import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.main.Handler;

import javax.sql.rowset.serial.SerialArray;
import java.awt.*;
import java.io.Serializable;

public abstract class Entity
        implements Serializable {

    public static final int DEFAULT_HEALTH = 10;

    protected transient Handler handler;
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected int health;
    protected boolean active = true;
    protected Rectangle bounds;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        health = DEFAULT_HEALTH;

        bounds = new Rectangle(0, 0, width, height);
    } // **** end Entity(Handler, float, float, int, int) constructor ****

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void die();

    public void hurt(int amt) {
        health -= amt;

        if (health <= 0) {
            setActive(false);
            die();
        }
    }

    public boolean checkEntityCollision(float xOffset, float yOffset) {
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if ( (e.equals(this)) ||
                    (e.equals(handler.getWorld().getEntityManager().getPlayer().getHoldableObject())) ||
                    ((e instanceof CropEntity) && (((CropEntity)e).getCropType() == CropEntity.CropType.GRASS)) ) {
                continue;
            }

            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int)(x + bounds.x + xOffset),
                (int)(y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // GETTERS & SETTERS

    public int getBoundsX() { return bounds.x; }

    public void setBoundsX(int x) { bounds.x = x; }

    public int getBoundsY() { return bounds.y; }

    public void setBoundsY(int y) { bounds.y = y; }

    public int getBoundsWidth() { return bounds.width; }

    public void setBoundsWidth(int width) {
        bounds.width = width;
    }

    public int getBoundsHeight() { return bounds.height; }

    public void setBoundsHeight(int height) {
        bounds.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end Entity class ****