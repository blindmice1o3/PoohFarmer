package edu.pooh.entities;

import java.awt.*;

public abstract class Entity {

    protected float x;
    protected float y;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    } // **** end Entity(float, float) constructor ****

    public abstract void tick();

    public abstract void render(Graphics g);

} // **** end Entity class ****