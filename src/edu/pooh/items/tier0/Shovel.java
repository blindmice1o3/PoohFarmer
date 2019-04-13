package edu.pooh.items.tier0;

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

    public static Shovel getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {
        Tile t = handler.getWorld().getEntityManager().getPlayer().getTileCurrentlyFacing();

        if (t != null) {
            System.out.print("targeted-tile's id: " + t.getId());

            // If tile is dirtNormalTile AND texture is dirtNormal...
            if ((t instanceof DirtNormalTile) && (t.getTexture() == Assets.dirtNormal)) {
                DirtNormalTile temp = (DirtNormalTile)t;
                temp.setSeedable(true);
                temp.setTexture(Assets.dirtTilled);
            }
        }

        System.out.println("Executed Shovel.");
    }

} // **** end Shovel class ****