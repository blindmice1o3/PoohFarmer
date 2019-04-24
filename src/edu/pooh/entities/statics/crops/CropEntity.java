package edu.pooh.entities.statics.crops;

import edu.pooh.entities.statics.harvests.HarvestEntity;
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
        CANNABIS_WILD,
        TURNIP,
        POTATO,
        TOMATO,
        CORN;
    }

    private CropType cropType;
    private int daysWatered;

    private boolean waterable;
    private boolean harvestable;
    private BufferedImage currentImage;

    public CropEntity(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        setCurrentImage(Assets.dirtSeedsDry);
        setDaysWatered(0);
        setWaterable(true);
        setHarvestable(false);

        // NO COLLISION WHILE dirtSeedsDry. START COLLISION AT daysWatered == 3.
        setBoundsWidth(0);
        setBoundsHeight(0);
    } // **** end CropEntity(Handler, float, float) constructor ****

    @Override
    public void tick() {
        nextCropLifeCycle();

        if (harvestable) {
            die();
        }
    }

    private void nextCropLifeCycle() {
        switch (daysWatered) {
            case 0:
            case 1:
            case 2:
                if (waterable) {
                    setCurrentImage(Assets.dirtSeedsDry);
                    break;
                } else {
                    setCurrentImage(Assets.dirtSeedsWatered);
                    break;
                }
            case 3:
                //////////////////////////////////////
                // START ENTITY COLLISION DETECTION //
                //////////////////////////////////////
                setBoundsWidth(width);
                setBoundsHeight(height);

                if (waterable) {                                    // UNWATERED (DRY) TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                            setCurrentImage(Assets.plantSproutling);
                            break;
                        case TURNIP:
                            setCurrentImage(Assets.turnip1Dry);
                            break;
                        case POTATO:
                            setCurrentImage(Assets.potato1Dry);
                            break;
                        case TOMATO:
                            setCurrentImage(Assets.tomato1Dry);
                            break;
                        case CORN:
                            setCurrentImage(Assets.corn1Dry);
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                } else {                                            // WATERED TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                            setCurrentImage(Assets.plantSproutling);
                            break;
                        case TURNIP:
                            setCurrentImage(Assets.turnip1Watered);
                            break;
                        case POTATO:
                            setCurrentImage(Assets.potato1Watered);
                            break;
                        case TOMATO:
                            setCurrentImage(Assets.tomato1Watered);
                            break;
                        case CORN:
                            setCurrentImage(Assets.corn1Watered);
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                }
                break;
            case 4:
                if (waterable) {                                    // UNWATERED (DRY) TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                            setCurrentImage(Assets.plantJuvenille);
                            break;
                        case TURNIP:
                            setCurrentImage(Assets.turnip2Dry);
                            break;
                        case POTATO:
                            setCurrentImage(Assets.potato2Dry);
                            break;
                        case TOMATO:
                            setCurrentImage(Assets.tomato2Dry);
                            break;
                        case CORN:
                            setCurrentImage(Assets.corn2Dry);
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                } else {                                            // WATERED TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                            setCurrentImage(Assets.plantJuvenille);
                            break;
                        case TURNIP:
                            setCurrentImage(Assets.turnip2Watered);
                            break;
                        case POTATO:
                            setCurrentImage(Assets.potato2Watered);
                            break;
                        case TOMATO:
                            setCurrentImage(Assets.tomato2Watered);
                            break;
                        case CORN:
                            setCurrentImage(Assets.corn2Watered);
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                }
                break;
            case 5:
                if (waterable) {                                    // UNWATERED (DRY) TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                            setCurrentImage(Assets.plantAdult);
                            break;
                        case TURNIP:
                        case POTATO:
                            setHarvestable(true);
                            break;
                        case TOMATO:
                            setCurrentImage(Assets.tomato3Dry);
                            break;
                        case CORN:
                            setCurrentImage(Assets.corn3Dry);
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                } else {                                            // WATERED TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                            setCurrentImage(Assets.plantAdult);
                            break;
                        case TURNIP:
                        case POTATO:
                            break;
                        case TOMATO:
                            setCurrentImage(Assets.tomato3Watered);
                            break;
                        case CORN:
                            setCurrentImage(Assets.corn3Watered);
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                }
                break;
            case 6:
                if (waterable) {                                    // UNWATERED (DRY) TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                            setCurrentImage(Assets.plantFlowering2);
                            break;
                        case TURNIP:
                        case POTATO:
                            break;
                        case TOMATO:
                            setCurrentImage(Assets.tomato4Dry);
                            break;
                        case CORN:
                            setCurrentImage(Assets.corn4Dry);
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                } else {                                            // WATERED TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                            setCurrentImage(Assets.plantFlowering2);
                            break;
                        case TURNIP:
                        case POTATO:
                            break;
                        case TOMATO:
                            setCurrentImage(Assets.tomato4Watered);
                            break;
                        case CORN:
                            setCurrentImage(Assets.corn4Watered);
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                }
                break;
            case 7:
                if (waterable) {                                    // UNWATERED (DRY) TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                            setHarvestable(true);
                            break;
                        case TURNIP:
                        case POTATO:
                            break;
                        case TOMATO:
                        case CORN:
                            setHarvestable(true);
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                } else {                                            // WATERED TILE
                    switch (cropType) {
                        case CANNABIS_WILD:
                        case TURNIP:
                        case POTATO:
                        case TOMATO:
                        case CORN:
                            break;
                        default:
                            setCurrentImage(Assets.waterFX);
                            break;
                    }
                }
                break;
            default:
                setCurrentImage(Assets.waterFX);
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
        tempHarvestEntity.determineAndSetTexture();


        handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(tempHarvestEntity);
        handler.getWorld().getEntityManager().setToBeAdded(true);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        Tile[][] tempLevel = handler.getWorld().getTilesViaRGB();
        DirtNormalTile tempDirtNormalTile;
        for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
            for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                if (tempLevel[xx][yy] instanceof DirtNormalTile) {
                    tempDirtNormalTile = (DirtNormalTile)tempLevel[xx][yy];

                    ////////////////////////////////////////////////////////////////////////////////////////////
                    if (tempDirtNormalTile.getStaticEntity() == this) {
                        tempDirtNormalTile.setTexture(Assets.dirtNormal);
                        tempDirtNormalTile.setDirtState(DirtNormalTile.DirtState.NORMAL);
                        /////////////////
                        setActive(false);
                        /////////////////
                        tempDirtNormalTile.setStaticEntity(tempHarvestEntity);
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

    public boolean getWaterable() {
        return waterable;
    }

    public void setWaterable(boolean waterable) {
        this.waterable = waterable;
    }

    public int getDaysWatered() {
        return daysWatered;
    }

    public void setDaysWatered(int daysWatered) {
        this.daysWatered = daysWatered;
    }

} // **** end CropEntity class ****