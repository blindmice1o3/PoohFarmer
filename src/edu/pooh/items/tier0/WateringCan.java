package edu.pooh.items.tier0;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

public class WateringCan extends Item {

    private static WateringCan uniqueInstance = new WateringCan();

    private int countWater = 0;

    private WateringCan() {
        super(Assets.wateringCan, "Watering Can", ID.WATERING_CAN);
    } // **** end WateringCan() singleton-pattern constructor ****

    public static WateringCan getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    public int getCountWater() {
        return countWater;
    }
    public void setCountWater(int count) {
        countWater = count;
    }
    public void increaseCountWater(int count) {
        countWater += count;
    }

    @Override
    public void execute() {
        int playerCenterX = (int)(handler.getWorld().getEntityManager().getPlayer().getX()
                + (handler.getWorld().getEntityManager().getPlayer().getWidth() / 2));
        int playerCenterY = (int)(handler.getWorld().getEntityManager().getPlayer().getY()
                + (handler.getWorld().getEntityManager().getPlayer().getHeight() / 2));
        Creature.DirectionFacing playerCurrentDirection =
                handler.getWorld().getEntityManager().getPlayer().getCurrentDirection();

        Tile t = null;
        switch (playerCurrentDirection) {
            case DOWN:
                t = handler.getWorld().getTile( (playerCenterX / Tile.TILE_WIDTH),
                        ((playerCenterY + Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT) );
                break;
            case UP:
                t = handler.getWorld().getTile( (playerCenterX / Tile.TILE_WIDTH),
                        ((playerCenterY - Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT) );
                break;
            case LEFT:
                t = handler.getWorld().getTile( ((playerCenterX - Tile.TILE_WIDTH) / Tile.TILE_WIDTH),
                        (playerCenterY / Tile.TILE_HEIGHT) );
                break;
            case RIGHT:
                t = handler.getWorld().getTile( ((playerCenterX + Tile.TILE_WIDTH) / Tile.TILE_WIDTH),
                        (playerCenterY / Tile.TILE_HEIGHT) );
                break;
            default:
                break;
        }

        if (t != null) {
            System.out.print("targeted-tile's id: " + t.getId());
        }

        if (t != null) {
            if (t.getId() >= 236 && t.getId() <= 248) {
                this.increaseCountWater(25);
            } else if (this.getCountWater() > 0) {
                t.setTexture(Assets.dirtSeed);
                this.setCountWater( (this.getCountWater() - 1) );
            }
        }

        System.out.println("Executed wateringCan.");
    }

} // **** end WateringCan class ****