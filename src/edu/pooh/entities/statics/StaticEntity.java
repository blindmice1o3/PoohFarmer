package edu.pooh.entities.statics;

import edu.pooh.entities.Entity;
import edu.pooh.main.Handler;

/**
 * Like abstract class Creature, StaticEntity is also an abstract class.
 * Unlike Creature, StaticEntity is for NON-MOVING game objects (trees, bodies of water, bushes/plants, etc).
 */
public abstract class StaticEntity extends Entity {

    public StaticEntity(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
    } // **** end StaticEntity(Handler, float, float, int, int) constructor ****

} // **** end StaticEntity abstract class ****