package edu.pooh.items;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item implements Invokable {

    // HANDLER (like the Tile[] array from the Tile class).

    public static Item[] items = new Item[256];
    public static Item scytheItem = new Item(Assets.scythe, "scythe", 1);
    public static Item shovelItem = new Item(Assets.shovel, "shovel", 2);
    public static Item hammerItem = new Item(Assets.hammer, "hammer", 3);
    public static Item axeItem = new Item(Assets.axe, "axe", 4);
    public static Item goldSprinklerItem = new Item(Assets.goldSprinkler, "goldSprinkler", 5);
    public static Item goldScytheItem = new Item(Assets.goldScythe, "goldScythe", 6);
    public static Item goldShovelItem = new Item(Assets.goldShovel, "goldShovel", 7);
    public static Item goldAxeItem = new Item(Assets.goldAxe, "goldAxe", 8);
    public static Item goldHammerItem = new Item(Assets.goldHammer, "goldHammer", 9);
    
    // CLASS

    public static final int ITEM_WIDTH = 32;
    public static final int ITEM_HEIGHT = 32;

    protected Handler handler;
    protected BufferedImage texture;
    protected String name;
    protected final int id;

    protected Rectangle bounds;

    protected int x, y, count;
    protected boolean pickedUp = false;

    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        count = 1;

        bounds = new Rectangle(x, y, ITEM_WIDTH, ITEM_HEIGHT);

        items[id] = this;
    } // **** end Item(BufferedImage, String, int) constructor ****

    public void execute() {

    }

    public void tick() {
        if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
            pickedUp = true;
            handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
        }
    }

    public void render(Graphics g) {
        if (handler == null) {
            return;
        }

        render(g, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()));    // x and y that the Item is currently storing
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, ITEM_WIDTH, ITEM_HEIGHT, null);
    }

    /**
     * only for testing purposes, will not be used in the game.
     */
    public Item createNew(int count) {
        Item i = new Item(texture, name, id);
        i.setPickedUp(true);
        i.setCount(count);
        return i;
    }

    public Item createNew(int x, int y) {
        Item i = new Item(texture, name, id);
        i.setPosition(x, y);
        return i;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        bounds.x = x;
        bounds.y = y;
    }

    // GETTERS & SETTERS

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

} // **** end Item class ****