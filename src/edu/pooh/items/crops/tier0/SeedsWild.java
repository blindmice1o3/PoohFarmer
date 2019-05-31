package edu.pooh.items.crops.tier0;

import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

public class SeedsWild extends Item {

    public enum SeedType {
        CANNABIS_WILD,
        TURNIP,
        POTATO,
        TOMATO,
        CORN,
        GRASS;
    }

    private SeedType seedType;

    public SeedsWild(Handler handler) {
        super(Assets.dirtSeededDry, "Wild Seeds", ID.SEEDSWILD);
        setHandler(handler);

        seedType = SeedType.CANNABIS_WILD;
        count = 5;
    } // **** end SeedsWild(Handler) constructor ****

    @Override
    public void execute() {
        int centerX = (int) (handler.getWorld().getEntityManager().getPlayer().getX() + (Tile.TILE_WIDTH / 2));
        int centerY = (int) (handler.getWorld().getEntityManager().getPlayer().getY() + (Tile.TILE_HEIGHT / 2));

        Tile[] tiles3x3 = handler.getWorld().getEntityManager().getPlayer().getTiles3x3FromCenter(centerX, centerY);

        for (Tile t : tiles3x3) {

            if (t != null) {
                // If there's a seed left and the tile is a dirtNormal that has DirtState.TILLED and is unoccupied.
                if ((count > 0) && (t instanceof DirtNormalTile) &&
                        //////////////////////////////////////////////////////////////////////
                        (((DirtNormalTile)t).getDirtState() == DirtNormalTile.DirtState.TILLED &&
                        //////////////////////////////////////////////////////////////////////
                                ((DirtNormalTile)t).getStaticEntity() == null)) {
                    DirtNormalTile temp = (DirtNormalTile) t;

                    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                    CropEntity.CropType tempCropType;
                    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                    switch (seedType) {
                        case CANNABIS_WILD:
                            tempCropType = CropEntity.CropType.CANNABIS_WILD;
                            break;
                        case TURNIP:
                            tempCropType = CropEntity.CropType.TURNIP;
                            break;
                        case POTATO:
                            tempCropType = CropEntity.CropType.POTATO;
                            break;
                        case TOMATO:
                            tempCropType = CropEntity.CropType.TOMATO;
                            break;
                        case CORN:
                            tempCropType = CropEntity.CropType.CORN;
                            break;
                        case GRASS:
                            tempCropType = CropEntity.CropType.GRASS;
                            break;
                        default:
                            tempCropType = CropEntity.CropType.CANNABIS_WILD;
                            break;
                    }

                    temp.setStaticEntity(new CropEntity(handler,
                            temp.getX() * Tile.TILE_WIDTH, temp.getY() * Tile.TILE_HEIGHT));
                    ((CropEntity)temp.getStaticEntity()).setCropType(tempCropType);

                    handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(temp.getStaticEntity());

                    ///////////////////////////////////////////////////
                    temp.setDirtState(DirtNormalTile.DirtState.SEEDED);
                    temp.setWatered(false);
                    temp.setTexture(Assets.dirtSeededDry);
                    ///////////////////////////////////////////////////
                }
            }
        }

        handler.getWorld().getEntityManager().setToBeAdded(true);
        count--;
        System.out.println("SeedsWild.execute(), SeedsWild's count decremented by 1");
        handler.getWorld().getEntityManager().getPlayer().decreaseStaminaCurrent(2);
        System.out.println("SeedsWild.execute(), player's stamina decrease by 2");
    }

    // GETTERS & SETTERS

    public void setSeedType(SeedType seedType) {
        this.seedType = seedType;
    }

    public SeedType getSeedType() {
        return seedType;
    }

} // **** end SeedsWild class ****