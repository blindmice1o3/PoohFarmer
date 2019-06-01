package edu.pooh.items.live_stocks;

import edu.pooh.entities.creatures.Player;
import edu.pooh.entities.creatures.live_stocks.Chicken;
import edu.pooh.gfx.Assets;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.states.TravelingFenceState;
import edu.pooh.tiles.Tile;

public class ChickenSpontaneousGenerator extends Item {

    private static ChickenSpontaneousGenerator uniqueInstance = new ChickenSpontaneousGenerator();

    private ChickenSpontaneousGenerator() {
        super(Assets.chickenAdultDown[0], "Chicken Spontaneous Generator", ID.CHICKEN_SPONTANEOUS_GENERATOR);
    } // **** end ChickenSpontaneousGenerator() singleton-pattern constructor ****

    public static synchronized ChickenSpontaneousGenerator getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void execute() {
        Player player = handler.getWorld().getEntityManager().getPlayer();

        System.out.println("ChickenSpontaneousGenerator: BEFORE checking tile and entity in front of player.");

        if ( (!player.getTileCurrentlyFacing().isSolid()) && (player.getEntityCurrentlyFacing() == null) ) {

            System.out.println("ChickenSpontaneousGenerator: AFTER checking tile and entity in front of player.");

            for (int yy = 0; yy < handler.getWorld().getHeightInTiles(); yy++) {
                for (int xx = 0; xx < handler.getWorld().getWidthInTiles(); xx++) {

                    System.out.println("ChickenSpontaneousGenerator: nested-for loops BEFORE if found tile facing.");

                    if (handler.getWorld().getTile(xx, yy) == player.getTileCurrentlyFacing()) {

                        System.out.println("ChickenSpontaneousGenerator: nested-for loops AFTER if found tile facing.");

                        Chicken chicken = new Chicken(handler, (xx * Tile.TILE_WIDTH), (yy * Tile.TILE_HEIGHT),
                                Chicken.ChickenState.ADULT_EGG_LAYING);
                        chicken.setDaysInstantiated(7);

                        handler.getWorld().getEntityManager().getEntitiesToBeAdded().add(chicken);
                        handler.getWorld().getEntityManager().setToBeAdded(true);




                        ((TravelingFenceState)handler.getGame().getTravelingFenceState()).getInventory().addItem( getUniqueInstance(handler) );
                        player.getInventory().incrementSelectedItem();
                        for (int x = 0; x < player.getInventory().getInventoryItems().size(); x++) {
                            if (player.getInventory().getItem(x) instanceof ChickenSpontaneousGenerator) {
                                player.getInventory().removeItem(x);
                            }
                        }
                        System.out.println("Chicken Spontaneous Generator used and returned to TravelingFenceState's inventory.");
                        return; //BUG: when tile's texture is DirtWalkway... it's static? not an instance of the class?
                    }
                }
            }

        }
    }

} // **** end ChickenSpontaneousGenerator class ****