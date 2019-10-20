package edu.pooh.items;

import edu.pooh.main.Handler;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager
        implements Serializable {

    private Handler handler;
    private ArrayList<Item> items;

    public ItemManager(Handler handler) {
        this.handler = handler;
        items = new ArrayList<Item>();
    } // **** end ItemManager(Handler) constructor ****

    public void tick() {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item i = iterator.next();
            i.tick();
            if (i.isPickedUp()) {
                iterator.remove();
            }
        }
    }

    public void render(Graphics g) {
        for (Item i : items) {
            i.render(g);
        }
    }

    public void addItem(Item i) {
        items.add(i);
    }

    // GETTERS & SETTERS

    public Handler getHandler() {
        return handler;
    }

} // **** end ItemManager class ****