package edu.pooh.entities;

import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.statics.statics1x1.Fodder;
import edu.pooh.main.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class EntityManager {

    private Handler handler;
    private Player player;

    private ArrayList<Entity> entities;

    private Comparator<Entity> renderSorter = new Comparator<Entity>(){     // Comparator to pass into ArrayList's
        @Override                                                           // sort(Comparator) method.
        public int compare(Entity a, Entity b) {
            // top of entity plus height of entity is the y-coordinate value of the bottom of the entity.
            if (a.getY() + a.getHeight() < b.getY() + b.getHeight()) {
                return -1;
            }
            return 1;
        }
    };

    private ArrayList<Entity> entitiesToBeAdded;
    private boolean toBeAdded;

    public EntityManager(Handler handler) {
        this.handler = handler;
        entities = new ArrayList<Entity>();

        toBeAdded = false;
        entitiesToBeAdded = new ArrayList<Entity>();
    } // **** end EntityManager(Handler) constructor ****

    /**
     * World class's entityManager WILL HAVE TO CALL locatePlayer() AFTER loading map/level from file.
     */
    public void locatePlayer() {
        for (Entity e : entities) {
            if (e instanceof Player) {
                setPlayer( (Player)e );
            }
        }
    }

    public void tick() {
        Iterator<Entity> iterator = entities.iterator();

        while (iterator.hasNext()) {
            Entity e = iterator.next();
            e.tick();
            if (!e.isActive()) {
                iterator.remove();
            }
        }
        if (toBeAdded) {
            entities.addAll(entitiesToBeAdded);
            toBeAdded = false;
            entitiesToBeAdded.clear();
        }

        entities.sort(renderSorter);    // Sort the collection of Entity objects based on y-coordinate values.
    }

    public void render(Graphics g) {
        // The order of which entity is rendered onto the screen determines which entity is drawn on top of which.
        // Sort the collection of Entity objects (ArrayList<Entity> entities) based on y-coordinate values.
        // Use a Comparator
        for (Entity e : entities) {
            if ( !(e instanceof Fodder) ) {
                e.render(g);
            }
        }
        player.postRender(g);
    }

    public void addEntity(Entity e) {
        if (!entities.contains(e)) {
            entities.add(e);
        }
    }

    // GETTERS & SETTERS

    public ArrayList<Entity> getEntitiesToBeAdded() { return entitiesToBeAdded; }

    public void setEntitiesToBeAdded(ArrayList<Entity> entitiesToBeAdded) { this.entitiesToBeAdded = entitiesToBeAdded; }

    public boolean getToBeAdded() {
        return toBeAdded;
    }

    public void setToBeAdded(boolean toBeAdded) {
        this.toBeAdded = toBeAdded;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

} // **** end EntityManager class ****