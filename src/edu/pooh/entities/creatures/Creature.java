package edu.pooh.entities.creatures;

import edu.pooh.entities.Entity;

public abstract class Creature extends Entity {

    protected int health;

    public Creature(float x, float y) {
        super(x, y);
        health = 10;
    } // **** end Creature(float, float) constructor ****

} // **** end Creature abstract class ****