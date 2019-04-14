package edu.pooh.items.tier0;

import edu.pooh.entities.Entity;
import edu.pooh.entities.statics.CannabisWild;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.tiles.DirtNormalTile;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.util.Iterator;

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
            if ((count > 0) && (t instanceof DirtNormalTile) &&
                    (((DirtNormalTile) t).getDirtState() == DirtNormalTile.DirtState.TILLED &&
                    ((DirtNormalTile) t).getStaticEntity() == null)) {
                DirtNormalTile temp = (DirtNormalTile) t;
                temp.setTexture(Assets.dirtNormal);
                temp.setDirtState(DirtNormalTile.DirtState.NORMAL);

                ///////////////////////////////////////////////////////////////////////////////////////////

                temp.setStaticEntity(new CannabisWild(handler, temp.getX() * Tile.TILE_WIDTH, temp.getY() * Tile.TILE_HEIGHT));

                ///////////////////////////////////////////////////////////////////////////////////////////
                handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(temp.getStaticEntity());
                handler.getWorld().getEntityManager().setToBeAdded(true);

                count--;
            }
        }

        System.out.println("Executed SeedsWild.");
    }

} // **** end SeedsWild class ****