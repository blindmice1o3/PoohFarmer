package edu.pooh.entities.statics.crops;

import edu.pooh.entities.statics.produce_yields.HarvestEntity;
import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CropEntity extends StaticEntity {

    public enum CropType {
        CANNABIS_WILD, TURNIP, POTATO, TOMATO, CORN, GRASS;
    }

    private CropType cropType;

    private int daysWatered;
    private boolean tangibleToScythe;
    private boolean harvestable;
    private BufferedImage currentImage;

    public CropEntity(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        daysWatered = 0;
        harvestable = false;
        tangibleToScythe = false;
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
        System.out.println("daysWatered == " + daysWatered);

        switch (cropType) {
            case GRASS:
                if (daysWatered == 1) {
                    setCurrentImage(Assets.grassSeeded);
                    setBoundsWidth(width);
                    setBoundsHeight(height);

                    break;
                } else if (daysWatered == 2) {
                    break;
                } else if (daysWatered == 3) {
                    break;
                } else if (daysWatered == 4) {
                    break;
                } else if (daysWatered == 5) {
                    setCurrentImage(Assets.grassYoung);
                    break;
                } else if (daysWatered == 6) {
                    break;
                } else if (daysWatered == 7) {
                    break;
                } else if (daysWatered == 8) {
                    break;
                } else if (daysWatered == 9) {
                    setCurrentImage(Assets.grassAdult);
                    // @@@@@
                    setTangibleToScythe(true);
                    // @@@@@

                    break;
                }

                break;
            case CANNABIS_WILD:
                if (daysWatered == 1) {
                    // START ENTITY COLLISION DETECTION
                    setBoundsWidth(width);
                    setBoundsHeight(height);
                    setTangibleToScythe(true);

                    setCurrentImage(Assets.tomato1Dry);
                    break;
                } else if (daysWatered == 2) {
                    setCurrentImage(Assets.tomato2Dry);
                    break;
                } else if (daysWatered == 3) {
                    setCurrentImage(Assets.tomato3Dry);
                    break;
                } else if (daysWatered == 4) {
                    setCurrentImage(Assets.plantFlowering2);
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
                    setTangibleToScythe(true);

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
                    setTangibleToScythe(true);

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
                    setTangibleToScythe(true);

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
                    setTangibleToScythe(true);

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
    public void die() {
        if (cropType == cropType.GRASS) {
            ResourceManager.increaseFodderCount(1);

            daysWatered = 0;
            currentImage = Assets.grassSeeded;
            harvestable = false;
            tangibleToScythe = false;
        }
        // @@@@@@@@@@@@@@@@@@@@@@ above: GRASS  |  below: CROPS @@@@@@@@@@@@@@@@@@@@@@
        else {
            HarvestEntity tempHarvestEntity = null;
            if (harvestable) {
                tempHarvestEntity = new HarvestEntity(handler, x, y);
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

                handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(tempHarvestEntity);
                handler.getWorld().getEntityManager().setToBeAdded(true);
            }

            Tile[][] tempLevel = handler.getWorld().getTilesViaRGB();
            DirtNormalTile tempDirtNormalTile;
            for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {
                for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                    if (tempLevel[xx][yy] instanceof DirtNormalTile) {
                        tempDirtNormalTile = (DirtNormalTile) tempLevel[xx][yy];
                        //////////////////////////////////////////////////////////////////
                        if (tempDirtNormalTile.getStaticEntity() == this) {
                            tempDirtNormalTile.setDirtState(DirtNormalTile.DirtState.NORMAL);
                            tempDirtNormalTile.setTexture(Assets.dirtNormal);
                            tempDirtNormalTile.setWatered(false);

                            /////////////////
                            setActive(false);
                            /////////////////

                            if (harvestable) {
                                tempDirtNormalTile.setStaticEntity(tempHarvestEntity);
                            } else {
                                tempDirtNormalTile.setStaticEntity(null);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentImage, (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
        Text.drawString(g, Integer.toString(daysWatered), (int)(x - handler.getGameCamera().getxOffset()),
                (int)(y - handler.getGameCamera().getyOffset()), false, Color.BLUE, Assets.font28);
    }

    @Override
    public void hurt(int amt) {
        return;
    }

    public void increaseDaysWatered() {
        daysWatered++;
    }

    public void swapCurrentImageDryToWet() {
        //CropType.CANNABIS_WILD
        if ( cropType.equals(CropType.CANNABIS_WILD) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededDry) ) {
                setCurrentImage(Assets.dirtSeededWatered);
            } else if ( getCurrentImage().equals(Assets.tomato1Dry) ) {
                setCurrentImage(Assets.tomato1Watered);
            } else if ( getCurrentImage().equals(Assets.tomato2Dry) ) {
                setCurrentImage(Assets.tomato2Watered);
            } else if ( getCurrentImage().equals(Assets.tomato3Dry) ) {
                setCurrentImage(Assets.tomato3Watered);
            } else if ( getCurrentImage().equals(Assets.plantFlowering2) ) {
                setCurrentImage(Assets.plantFlowering1);
            }
        }
        //CropType.TURNIP
        else if ( cropType.equals(CropEntity.CropType.TURNIP) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededDry) ) {
                setCurrentImage(Assets.dirtSeededWatered);
            } else if ( getCurrentImage().equals(Assets.turnip1Dry) ) {
                setCurrentImage(Assets.turnip1Watered);
            } else if ( getCurrentImage().equals(Assets.turnip2Dry) ) {
                setCurrentImage(Assets.turnip2Watered);
            }
        }
        //CropType.POTATO
        else if ( cropType.equals(CropEntity.CropType.POTATO) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededDry) ) {
                setCurrentImage(Assets.dirtSeededWatered);
            } else if ( getCurrentImage().equals(Assets.potato1Dry) ) {
                setCurrentImage(Assets.potato1Watered);
            } else if ( getCurrentImage().equals(Assets.potato2Dry) ) {
                setCurrentImage(Assets.potato2Watered);
            }
        }
        //CropType.TOMATO
        else if ( cropType.equals(CropEntity.CropType.TOMATO) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededDry) ) {
                setCurrentImage(Assets.dirtSeededWatered);
            } else if ( getCurrentImage().equals(Assets.tomato1Dry) ) {
                setCurrentImage(Assets.tomato1Watered);
            } else if ( getCurrentImage().equals(Assets.tomato2Dry) ) {
                setCurrentImage(Assets.tomato2Watered);
            } else if ( getCurrentImage().equals(Assets.tomato3Dry) ) {
                setCurrentImage(Assets.tomato3Watered);
            } else if ( getCurrentImage().equals(Assets.tomato4Dry) ) {
                setCurrentImage(Assets.tomato4Watered);
            }
        }
        //CropType.CORN
        else if ( cropType.equals(CropEntity.CropType.CORN) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededDry) ) {
                setCurrentImage(Assets.dirtSeededWatered);
            } else if ( getCurrentImage().equals(Assets.corn1Dry) ) {
                setCurrentImage(Assets.corn1Watered);
            } else if ( getCurrentImage().equals(Assets.corn2Dry) ) {
                setCurrentImage(Assets.corn2Watered);
            } else if ( getCurrentImage().equals(Assets.corn3Dry) ) {
                setCurrentImage(Assets.corn3Watered);
            } else if ( getCurrentImage().equals(Assets.corn4Dry) ) {
                setCurrentImage(Assets.corn4Watered);
            }
        }
    }

    public void swapCurrentImageWetToDry() {
        //CropType.CANNABIS_WILD
        if ( cropType.equals(CropType.CANNABIS_WILD) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededWatered) ) {
                setCurrentImage(Assets.dirtSeededDry);
            } else if ( getCurrentImage().equals(Assets.tomato1Watered) ) {
                setCurrentImage(Assets.tomato1Dry);
            } else if ( getCurrentImage().equals(Assets.tomato2Watered) ) {
                setCurrentImage(Assets.tomato2Dry);
            } else if ( getCurrentImage().equals(Assets.tomato3Watered) ) {
                setCurrentImage(Assets.tomato3Dry);
            } else if ( getCurrentImage().equals(Assets.plantFlowering1) ) {
                setCurrentImage(Assets.plantFlowering2);
            }
        }
        //CropType.TURNIP
        else if ( cropType.equals(CropEntity.CropType.TURNIP) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededWatered) ) {
                setCurrentImage(Assets.dirtSeededDry);
            } else if ( getCurrentImage().equals(Assets.turnip1Watered) ) {
                setCurrentImage(Assets.turnip1Dry);
            } else if ( getCurrentImage().equals(Assets.turnip2Watered) ) {
                setCurrentImage(Assets.turnip2Dry);
            }
        }
        //CropType.POTATO
        else if ( cropType.equals(CropEntity.CropType.POTATO) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededWatered) ) {
                setCurrentImage(Assets.dirtSeededDry);
            } else if ( getCurrentImage().equals(Assets.potato1Watered) ) {
                setCurrentImage(Assets.potato1Dry);
            } else if ( getCurrentImage().equals(Assets.potato2Watered) ) {
                setCurrentImage(Assets.potato2Dry);
            }
        }
        //CropType.TOMATO
        else if ( cropType.equals(CropEntity.CropType.TOMATO) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededWatered) ) {
                setCurrentImage(Assets.dirtSeededDry);
            } else if ( getCurrentImage().equals(Assets.tomato1Watered) ) {
                setCurrentImage(Assets.tomato1Dry);
            } else if ( getCurrentImage().equals(Assets.tomato2Watered) ) {
                setCurrentImage(Assets.tomato2Dry);
            } else if ( getCurrentImage().equals(Assets.tomato3Watered) ) {
                setCurrentImage(Assets.tomato3Dry);
            } else if ( getCurrentImage().equals(Assets.tomato4Watered) ) {
                setCurrentImage(Assets.tomato4Dry);
            }
        }
        //CropType.CORN
        else if ( cropType.equals(CropEntity.CropType.CORN) ) {
            if ( getCurrentImage().equals(Assets.dirtSeededWatered) ) {
                setCurrentImage(Assets.dirtSeededDry);
            } else if ( getCurrentImage().equals(Assets.corn1Watered) ) {
                setCurrentImage(Assets.corn1Dry);
            } else if ( getCurrentImage().equals(Assets.corn2Watered) ) {
                setCurrentImage(Assets.corn2Dry);
            } else if ( getCurrentImage().equals(Assets.corn3Watered) ) {
                setCurrentImage(Assets.corn3Dry);
            } else if ( getCurrentImage().equals(Assets.corn4Watered) ) {
                setCurrentImage(Assets.corn4Dry);
            }
        }
    }

    // GETTERS & SETTERS

    public CropType getCropType() { return cropType; }

    public void setCropType(CropType cropType) { this.cropType = cropType; }

    public boolean isHarvestable() {
        return harvestable;
    }

    public void setHarvestable(boolean harvestable) {
        this.harvestable = harvestable;
    }

    public boolean isTangibleToScythe() { return tangibleToScythe; }

    public void setTangibleToScythe(boolean tangibleToScythe) { this.tangibleToScythe = tangibleToScythe; }

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