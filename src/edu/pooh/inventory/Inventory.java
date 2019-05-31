package edu.pooh.inventory;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.items.Item;
import edu.pooh.items.crops.tier0.SeedsWild;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {

    private Handler handler;
    private boolean active = false;
    private ArrayList<Item> inventoryItems;

    private int invX = 64, invY = 48,
            invWidth = 668, invHeight = 500,
            invListCenterX = invX + 200,
            invListCenterY = invY + invHeight / 2,
            invListSpacing = 100;

    private int invImageX = 575, invImageY = 90,
                invImageWidth = 64, invImageHeight = 64;

    private int invCountX = 607, invCountY = 180;

    private int index = 0;

    public Inventory(Handler handler) {
        this.handler = handler;
        inventoryItems = new ArrayList<Item>();
    } // **** end Inventory(Handler) constructor ****

    public void incrementSelectedItem() {
        index++;

        if (index >= inventoryItems.size()) {
            index = 0;
        }

        System.out.println("Inventory.incrementSelectedItem() called... (aka index incremented)");
    }

    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_I)) {
            active = !active;
        }
        if (!active) {
            return;
        }

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            index--;
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            index++;
        }

        if (index < 0) {
            index = inventoryItems.size() - 1;
        }
        else if (index >= inventoryItems.size()) {
            index = 0;
        }
    }

    public void render(Graphics g) {
        if (!active) {
            return;
        }

        g.drawImage(Assets.inventoryBackground, invX, invY, invWidth, invHeight, null);

        int len = inventoryItems.size();
        if (len == 0) {
            return;
        }

        for (int i = -2; i < 3; i++) {
            if (index + i < 0 || index + i >= len) {
                continue;
            }

            if (i == 0) {
                Text.drawString(g, "> " + inventoryItems.get(index + i).getName() + " <", invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28);
            } else {
                Text.drawString(g, inventoryItems.get(index + i).getName(), invListCenterX,
                        invListCenterY + i * invListSpacing, true, Color.RED, Assets.font28);
            }
        }

        Item item = inventoryItems.get(index);
        g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
        Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY, true, Color.YELLOW, Assets.font28);
    }

    // INVENTORY METHODS

    public void addItem(Item item) {
        // If we already have it in the inventory, just increase the count variable.
        for (Item i : inventoryItems) {
            if ( (i.getId() == item.getId()) && (item.getId() == Item.ID.SEEDSWILD) ) {
                if ( ((SeedsWild)i).getSeedType() == ((SeedsWild)item).getSeedType() ) {
                    i.setCount(i.getCount() + item.getCount());
                    return;
                }
            } else if (i.getId() == item.getId()) {
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        inventoryItems.add(item);
    }

    // GETTERS & SETTERS

    public ArrayList<Item> getInventoryItems() { return inventoryItems; }

    public Item getItem(int index) {
        return inventoryItems.get(index);
    }

    public void removeItem(int index) {
        inventoryItems.remove(index);
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) { this.index = index; }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) { this.active = active; }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end Inventory class ****