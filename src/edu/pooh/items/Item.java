package edu.pooh.items;

import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Item
        implements IInvokable, Serializable {

    public static final int ITEM_WIDTH = 32;
    public static final int ITEM_HEIGHT = 32;

    public enum ID {
        WATERING_CAN,
        SEEDSWILD,
        SHOVEL,
        SCYTHE,
        HAMMER,
        AXE,
        GOLD_SPRINKLER,
        GOLD_SHOVEL,
        GOLD_SCYTHE,
        GOLD_HAMMER,
        GOLD_AXE,
        COW_MILKER,
        COW_BRUSH,
        COW_ARTIFICIAL_INSEMINATOR,
        COW_SPONTANEOUS_GENERATOR,
        CHICKEN_SPONTANEOUS_GENERATOR;
    }

    // CLASS

    protected transient Handler handler;
    protected transient BufferedImage texture;
    protected String name;
    protected final ID id;

    protected Rectangle bounds;

    protected int x, y, count;
    protected boolean pickedUp = false;

    public Item(BufferedImage texture, String name, ID id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        count = 1;

        bounds = new Rectangle(x, y, ITEM_WIDTH, ITEM_HEIGHT);

    } // **** end Item(BufferedImage, String, int) constructor ****

    public abstract void execute();

    public void tick() {
        if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
            pickedUp = true;    // ItemManager uses this boolean flag to remove it from the World.
            handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
        }
    }

    public void render(Graphics g) {
        if (handler == null) {
            return;
        }

        render(g, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()) );    // x and y that the Item is currently storing
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, ITEM_WIDTH, ITEM_HEIGHT, null);
    }

    public void setPosition(int x, int y) {
        this.x = x + (Tile.TILE_WIDTH / 2) - (ITEM_WIDTH / 2);
        this.y = y + (Tile.TILE_HEIGHT / 2) - (ITEM_HEIGHT / 2);
        bounds.x = x + (Tile.TILE_WIDTH / 2) - (ITEM_WIDTH / 2);
        bounds.y = y + (Tile.TILE_HEIGHT / 2) - (ITEM_HEIGHT / 2);
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

    public ID getId() {
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

    public Rectangle getBounds() {
        return bounds;
    }

} // **** end Item class ****