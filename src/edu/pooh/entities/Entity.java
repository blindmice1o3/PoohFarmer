package edu.pooh.entities;

import edu.pooh.main.Handler;

import java.awt.*;

public abstract class Entity {

    protected Handler handler;
    protected float x;
    protected float y;
    protected int width;
    protected int height;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    } // **** end Entity(Handler, float, float, int, int) constructor ****

    public abstract void tick();

    public abstract void render(Graphics g);

    // GETTERS & SETTERS

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

} // **** end Entity class ****