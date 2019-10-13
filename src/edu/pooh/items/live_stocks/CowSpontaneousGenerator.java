package edu.pooh.items.live_stocks;

import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.creatures.live_stocks.Cow;
import edu.pooh.gfx.Assets;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.states.CowBarnState;
import edu.pooh.states.StateManager;
import edu.pooh.states.TravelingFenceState;
import edu.pooh.tiles.Tile;

public class CowSpontaneousGenerator extends Item {

    private static CowSpontaneousGenerator uniqueInstance =  new CowSpontaneousGenerator();

    private CowSpontaneousGenerator() {
        super(Assets.cowAdultDown[0], "Cow Spontaneous Generator", ID.COW_SPONTANEOUS_GENERATOR);
    } // **** end CowSpontaneousGenerator() singleton-pattern constructor ****

    public static synchronized CowSpontaneousGenerator getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {
        Player player = handler.getWorld().getEntityManager().getPlayer();

        if ((ResourceManager.getCowCounter() < 12) && (!player.getTileCurrentlyFacing().isSolid()) &&
                (player.getEntityCurrentlyFacing() == null)) {

            for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {

                    if (handler.getWorld().getTile(xx, yy) == player.getTileCurrentlyFacing()) {
                        ///////////////////////////////////////////////////////////////////////////////////////
                        //Could be cow population of 0 (player's first cow), which should be assigned stall index 0.
                        Cow cow = new Cow(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT), Cow.CowState.CALF,
                                ResourceManager.getCowCounter());
                        cow.setDaysInstantiated(14);
                        cow.setAffectionScore(0);
                        ((CowBarnState)handler.getStateManager().getIState(StateManager.GameState.COW_BARN)).assignCowToFodderDisplayerTile(cow);
                        handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(cow);
                        handler.getWorld().getEntityManager().setToBeAdded(true);
                        ///////////////////////////////////////////////////////////////////////////////////////

                        // Cow instantiated and added, return CowSpontaneousGenerator singleton-instance to shop.
                        ((TravelingFenceState)handler.getStateManager().getIState(StateManager.GameState.TRAVELING_FENCE)).getInventory().addItem( getUniqueInstance(handler) );
                        player.getInventory().decrementSelectedItem();
                        for (int x = 0; x < player.getInventory().getInventoryItems().size(); x++) {
                            if (player.getInventory().getItem(x) instanceof CowSpontaneousGenerator) {
                                player.getInventory().removeItem(x);
                                break;
                            }
                        }
                        System.out.println("CowSpontaneousGenerator used and returned to TravelingFenceState's inventory.");
                        return; //BUG: when tile's texture is DirtWalkway... it's static? not an instance of the class?
                    }
                }
            }
        }
    }

} // **** end CowSpontaneousGenerator class ****