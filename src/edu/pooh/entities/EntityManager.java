package edu.pooh.entities;

import edu.pooh.entities.creatures.Player;
import edu.pooh.main.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

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

    public EntityManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<Entity>();
        addEntity(player);
    } // **** end EntityManager(Handler, Player) constructor ****

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
        }
        entities.sort(renderSorter);    // Sort the collection of Entity objects based on y-coordinate values.
    }

    public void render(Graphics g) {
        // The order of which entity is rendered onto the screen determines which entity is drawn on top of which.
        // Sort the collection of Entity objects (ArrayList<Entity> entities) based on y-coordinate values.
        // Use a Comparator
        for (Entity e : entities) {
            e.render(g);
        }
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    // GETTERS & SETTERS

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