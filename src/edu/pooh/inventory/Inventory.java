package edu.pooh.inventory;

import edu.pooh.items.Item;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {

    private Handler handler;
    private boolean active = false;
    private ArrayList<Item> inventoryItems;

    public Inventory(Handler handler) {
        this.handler = handler;
        inventoryItems = new ArrayList<Item>();
    } // **** end Inventory(Handler) constructor ****

    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
            active = !active;
        }

        if (!active) {
            return;
        }

        System.out.println("INVENTORY:");
        for (Item i : inventoryItems) {
            System.out.println(i.getName() + "   " + i.getCount());
        }
    }

    public void render(Graphics g) {
        if (!active) {
            return;
        }


    }

    // INVENTORY METHODS

    public void addItem(Item item) {
        // If we already have it in the inventory, just increase the count variable.
        for (Item i : inventoryItems) {
            if (i.getId() == item.getId()) {
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        inventoryItems.add(item);
    }

    // GETTERS & SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end Inventory class ****