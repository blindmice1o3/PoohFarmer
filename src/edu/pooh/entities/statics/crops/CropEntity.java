package edu.pooh.entities.statics.crops;

import edu.pooh.entities.statics.produce_yields.HarvestEntity;
import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * TODO: Move responsibility of tracking waterable from this singular concrete subclass of StaticEntity
 * to the abstract class named Tile. Set up an observer design-pattern between Tile class and StaticEntity
 * class. Inside the tile's setStaticEntity(StaticEntity) method, have the tile call its
 * registerObserver() method to add the newly composed StaticEntity object to the tile's list of Observer.
 * Add an instance variable to Tile class named boolean watered. Whenever the state of the tile's watered
 * variable is set to true, call tile's (Subject) notifyObservers() method, which traverse its collection
 * of Observer objects to call their update() method.
 */
public class CropEntity extends StaticEntity {

    public enum CropType {
        CANNABIS_WILD, TURNIP, POTATO, TOMATO, CORN;
    }

    private CropType cropType;

    private int daysWatered;
    private boolean harvestable;
    private BufferedImage currentImage;

    public CropEntity(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        daysWatered = 0;
        harvestable = false;
        currentImage = Assets.dirtSeededDry;

        // NO COLLISION WHILE dirtSeededDry. START COLLISION AT daysWatered == 3.
        setBoundsWidth(0);
        setBoundsHeight(0);
    } // **** end CropEntity(Handler, float, float) constructor ****

    @Override
    public void tick() {
        if (harvestable) {
            die();
        }
    }

    public void incrementLifeCycleByDaysWatered() {
        System.out.println("CropEntity.incrementLifeCycleByDaysWatered()");

        switch (cropType) {
            case CANNABIS_WILD:
                if (daysWatered == 1) {
                    // START ENTITY COLLISION DETECTION
                    setBoundsWidth(width);
                    setBoundsHeight(height);

                    setCurrentImage(Assets.plantSproutling);
                    break;
                } else if (daysWatered == 2) {
                    setCurrentImage(Assets.plantJuvenille);
                    break;
                } else if (daysWatered == 3) {
                    setCurrentImage(Assets.plantAdult);
                    break;
                } else if (daysWatered == 4) {
                    setCurrentImage(Assets.plantFlowering1);
                    break;
                } else if (daysWatered == 5) {
                    setHarvestable(true);
                    break;
                }

                break;
            case TURNIP:
                if (daysWatered == 1) {
                    // START ENTITY COLLISION DETECTION
                    setBoundsWidth(width);
                    setBoundsHeight(height);

                    setCurrentImage(Assets.turnip1Dry);
                    break;
                } else if (daysWatered == 2) {
                    setCurrentImage(Assets.turnip2Dry);
                    break;
                } else if (daysWatered == 3) {
                    setHarvestable(true);
                    break;
                }

                break;
            case POTATO:
                if (daysWatered == 1) {
                    // START ENTITY COLLISION DETECTION
                    setBoundsWidth(width);
                    setBoundsHeight(height);

                    setCurrentImage(Assets.potato1Dry);
                    break;
                } else if (daysWatered == 2) {
                    break;
                } else if (daysWatered == 3) {
                    setCurrentImage(Assets.potato2Dry);
                    break;
                } else if (daysWatered == 4) {
                    break;
                } else if (daysWatered == 5) {
                    setHarvestable(true);
                    break;
                }

                break;
            case TOMATO:
                if (daysWatered == 1) {
                    // START ENTITY COLLISION DETECTION
                    setBoundsWidth(width);
                    setBoundsHeight(height);

                    setCurrentImage(Assets.tomato1Dry);
                    break;
                } else if (daysWatered == 2) {
                    break;
                } else if (daysWatered == 3) {
                    setCurrentImage(Assets.tomato2Dry);
                    break;
                } else if (daysWatered == 4) {
                    break;
                } else if (daysWatered == 5) {
                    setCurrentImage(Assets.tomato3Dry);
                    break;
                } else if (daysWatered == 6) {
                    break;
                } else if (daysWatered == 7) {
                    setCurrentImage(Assets.tomato4Dry);
                    break;
                } else if (daysWatered == 8) {
                    break;
                } else if (daysWatered == 9) {
                    setHarvestable(true);
                    break;
                }

                break;
            case CORN:
                if (daysWatered == 1) {
                    // START ENTITY COLLISION DETECTION
                    setBoundsWidth(width);
                    setBoundsHeight(height);

                    setCurrentImage(Assets.corn1Dry);
                    break;
                } else if (daysWatered == 2) {
                    break;
                } else if (daysWatered == 3) {
                    setCurrentImage(Assets.corn2Dry);
                    break;
                } else if (daysWatered == 4) {
                    break;
                } else if (daysWatered == 5) {
                    break;
                } else if (daysWatered == 6) {
                    setCurrentImage(Assets.corn3Dry);
                    break;
                } else if (daysWatered == 7) {
                    break;
                } else if (daysWatered == 8) {
                    break;
                } else if (daysWatered == 9) {
                    setCurrentImage(Assets.corn4Dry);
                    break;
                } else if (daysWatered == 10) {
                    break;
                } else if (daysWatered == 11) {
                    break;
                } else if (daysWatered == 12) {
                    setHarvestable(true);
                    break;
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentImage, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        Text.drawString(g, "dayWatered: " + daysWatered, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), false, Color.YELLOW, Assets.font28);
    }

    @Override
    public void die() {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        HarvestEntity tempHarvestEntity = new HarvestEntity(handler, x, y);
        switch (cropType) {
            case CANNABIS_WILD:
                tempHarvestEntity.setHarvestType(HarvestEntity.HarvestType.CANNABIS_WILD);
                break;
            case TURNIP:
                tempHarvestEntity.setHarvestType(HarvestEntity.HarvestType.TURNIP);
                break;
            case POTATO:
                tempHarvestEntity.setHarvestType(HarvestEntity.HarvestType.POTATO);
                break;
            case TOMATO:
                tempHarvestEntity.setHarvestType(HarvestEntity.HarvestType.TOMATO);
                break;
            case CORN:
                tempHarvestEntity.setHarvestType(HarvestEntity.HarvestType.CORN);
                break;
            default:
                tempHarvestEntity.setHarvestType(HarvestEntity.HarvestType.CANNABIS_WILD);
                break;
        }
        tempHarvestEntity.setTextureAndPrice();

        if (harvestable) {
            handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(tempHarvestEntity);
            handler.getWorld().getEntityManager().setToBeAdded(true);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        Tile[][] tempLevel = handler.getWorld().getTilesViaRGB();
        DirtNormalTile tempDirtNormalTile;
        for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
            for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                if (tempLevel[xx][yy] instanceof DirtNormalTile) {
                    tempDirtNormalTile = (DirtNormalTile) tempLevel[xx][yy];

                    ////////////////////////////////////////////////////////////////////////////////////////////
                    if (tempDirtNormalTile.getStaticEntity() == this) {

                        //////////////////////////////////////////////////////////////////
                        tempDirtNormalTile.setDirtState(DirtNormalTile.DirtState.NORMAL);
                        tempDirtNormalTile.setTexture(Assets.dirtNormal);
                        tempDirtNormalTile.setWatered(false);
                        //////////////////////////////////////////////////////////////////

                        /////////////////
                        setActive(false);
                        /////////////////

                        if (harvestable) {
                            tempDirtNormalTile.setStaticEntity(tempHarvestEntity);
                        } else {
                            tempDirtNormalTile.setStaticEntity(null);
                        }
                    }
                    ////////////////////////////////////////////////////////////////////////////////////////////
                }
            }
        }
    }

    @Override
    public void hurt(int amt) {
        return;
    }

    public void increaseDaysWatered() {
        daysWatered++;
    }

    // GETTERS & SETTERS

    public CropType getCropType() { return cropType; }

    public void setCropType(CropType cropType) { this.cropType = cropType; }

    public boolean getHarvestable() {
        return harvestable;
    }

    public void setHarvestable(boolean harvestable) {
        this.harvestable = harvestable;
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(BufferedImage texture) {
        currentImage = texture;
    }

    public int getDaysWatered() {
        return daysWatered;
    }

    public void setDaysWatered(int daysWatered) {
        this.daysWatered = daysWatered;
    }

} // **** end CropEntity class ****