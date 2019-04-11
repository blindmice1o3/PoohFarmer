package edu.pooh.items;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.gfx.Assets;
import edu.pooh.tiles.Tile;

public class WateringCan extends Item {

    private int countWater = 0;

    public WateringCan(String name, int id, int x, int y) {
        super(Assets.wateringCan, name, id);
        setPosition(x, y);
    } // **** end WateringCan(String, int, int, int) constructor ****

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
        int playerX = (int)handler.getWorld().getEntityManager().getPlayer().getX();
        int playerY = (int)handler.getWorld().getEntityManager().getPlayer().getY();
        Creature.DirectionFacing playerCurrentDirection =
                handler.getWorld().getEntityManager().getPlayer().getCurrentDirection();

        Tile t = null;
        switch (playerCurrentDirection) {
            case DOWN:
                t = handler.getWorld().getTile((playerX / Tile.TILE_WIDTH), (playerY / Tile.TILE_HEIGHT) + 1);
                break;
            case UP:
                t = handler.getWorld().getTile((playerX / Tile.TILE_WIDTH), (playerY / Tile.TILE_HEIGHT) - 1);
                break;
            case LEFT:
                t = handler.getWorld().getTile((playerX / Tile.TILE_WIDTH) - 1, (playerY / Tile.TILE_HEIGHT));
                break;
            case RIGHT:
                t = handler.getWorld().getTile((playerX / Tile.TILE_WIDTH) + 1, (playerY / Tile.TILE_HEIGHT));
                break;
            default:
                break;
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