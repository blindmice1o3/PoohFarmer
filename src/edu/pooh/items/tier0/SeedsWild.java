package edu.pooh.items.tier0;

import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

public class SeedsWild extends Item {

    public SeedsWild(Handler handler) {
        super(Assets.dirtSeed, "Wild Seeds", ID.SEEDSWILD);
        setHandler(handler);
        count = 5;
    } // **** end SeedsWild(Handler) constructor ****

    @Override
    public void execute() {
        Tile t = handler.getWorld().getEntityManager().getPlayer().getTileCurrentlyFacing();

        if (t != null) {
            System.out.print("targeted-tile's id: " + t.getId());

            // If there's a seed left and the tile is a dirtNormal.
            if (count > 0 && (t.getId() == 0)) {
                // TODO: set tile's boolean flag (seedable) to false,
                // create a new Entity with a dirtSeed texture.

                count--;
            }
        }

        System.out.println("Executed SeedsWild.");
    }

} // **** end SeedsWild class ****