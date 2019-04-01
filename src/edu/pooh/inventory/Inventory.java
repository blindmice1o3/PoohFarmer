package edu.pooh.inventory;

import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Inventory {

    private Handler handler;
    private boolean active = false;

    public Inventory(Handler handler) {
        this.handler = handler;
    } // **** end Inventory(Handler) constructor ****

    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
            active = !active;
        }

        if (!active) {
            return;
        }

        System.out.println("INVENTORY WORKING");
    }

    public void render(Graphics g) {
        if (!active) {
            return;
        }
    }

    // GETTERS & SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end Inventory class ****