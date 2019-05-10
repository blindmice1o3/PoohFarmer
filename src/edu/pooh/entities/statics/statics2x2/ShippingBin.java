package edu.pooh.entities.statics.statics2x2;

import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.ISellable;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;

public class ShippingBin extends StaticEntity {

    private ArrayList<ISellable> inventory;

    public ShippingBin(Handler handler, float x, float y) {
        super(handler, x, y,  (2*Tile.TILE_WIDTH), (2*Tile.TILE_HEIGHT));

        inventory = new ArrayList<ISellable>();
    } // **** end ShippingBin(Handler, float, float) constructor ****

    public void addISellable(ISellable sellable) {
        inventory.add(sellable);

        for (ISellable tempSellable : inventory) {
            System.out.println("Inside the shipping bin is: " + tempSellable.toString() +
                    " (" + tempSellable.getPrice() + ")");
        }

        System.out.println("ISellable added to shipping bin.");
    }

    public void emptyShippingBin() {
        inventory.clear();
    }

    public int calculateTotal() {
        int total = 0;

        for (ISellable sellable : inventory) {
            total += sellable.getPrice();
        }

        System.out.println("Total price from shipping bin: " + total);
        return total;
    }

    @Override
    public void tick() {
        return;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.shippingBin2x2[0][0], (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        g.drawImage(Assets.shippingBin2x2[0][1], (int)(x - handler.getGameCamera().getxOffset() + Tile.TILE_WIDTH),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        g.drawImage(Assets.shippingBin2x2[1][0], (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset() + Tile.TILE_HEIGHT), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        g.drawImage(Assets.shippingBin2x2[1][1], (int)(x - handler.getGameCamera().getxOffset() + Tile.TILE_WIDTH),
                (int)(y - handler.getGameCamera().getyOffset() + Tile.TILE_HEIGHT), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
    }

    @Override
    public void hurt(int dmg) {
        return;
    }

    @Override
    public void die() {
        return;
    }

    // GETTERS & SETTERS

    public ArrayList<ISellable> getInventory() {
        return inventory;
    }

} // **** end ShippingBin class ****