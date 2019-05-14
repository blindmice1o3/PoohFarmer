package edu.pooh.items.tier0;

import edu.pooh.entities.statics.crops.CropEntity;
import edu.pooh.entities.statics.produce_yields.HarvestEntity;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

public class Shovel extends Item {

    private static Shovel uniqueInstance = new Shovel();

    private Shovel() {
        super(Assets.shovel, "Shovel", ID.SHOVEL);
    } // **** end Shovel() singleton-pattern constructor ****

    public static synchronized Shovel getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {
        Tile t = handler.getWorld().getEntityManager().getPlayer().getTileCurrentlyFacing();

        if (t != null) {
            System.out.print("Shovel.execute(), targeted-tile's id: " + t.getId());

            if ((t instanceof DirtNormalTile)) {
                DirtNormalTile temp = (DirtNormalTile) t;

                // Shovel calls CropEntity's and HarvestEntity's die() method.
                if (temp.getStaticEntity() != null) {
                    if ( /* 1 */
                            ((temp.getStaticEntity() instanceof CropEntity) &&
                            (((CropEntity)temp.getStaticEntity()).getCropType() != CropEntity.CropType.GRASS))
                                    ||
                         /* 2 */
                            (temp.getStaticEntity() instanceof HarvestEntity) ) {
                        temp.getStaticEntity().die();
                        temp.setDirtState(DirtNormalTile.DirtState.TILLED);
                        temp.setTexture(Assets.dirtTilledDry);
                    }
                    return;
                }

                // Does not matter if DirtNormalTile is NORMAL or SEEDED.
                ///////////////////////////////////////////////////
                temp.setDirtState(DirtNormalTile.DirtState.TILLED);
                temp.setTexture(Assets.dirtTilledDry);
                ///////////////////////////////////////////////////
            }
        }
    }

} // **** end Shovel class ****