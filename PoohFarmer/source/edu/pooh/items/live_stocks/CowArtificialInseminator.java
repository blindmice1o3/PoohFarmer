package edu.pooh.items.live_stocks;

import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.creatures.live_stocks.Cow;
import edu.pooh.gfx.Assets;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.states.CowBarnState;
import edu.pooh.states.StateManager;
import edu.pooh.states.TravelingFenceState;

public class CowArtificialInseminator extends Item {

    private static CowArtificialInseminator uniqueInstance = new CowArtificialInseminator();

    private CowArtificialInseminator() {
        super(Assets.cowMiraclePotion, "Cow Artificial Inseminator", ID.COW_ARTIFICIAL_INSEMINATOR);
    } // **** end CowArtificialInseminator() singleton-pattern constructor ****

    public static synchronized CowArtificialInseminator getUniqueInstance(Handler handler) {
        uniqueInstance.setHandler(handler);
        return uniqueInstance;
    }

    @Override
    public void resetTexture() {
        texture = Assets.cowMiraclePotion;
    }

    @Override
    public void execute() {
        Player tempPlayer = handler.getWorld().getEntityManager().getPlayer();
        CowBarnState tempCowBarnState = (CowBarnState)handler.getStateManager().getIState(StateManager.GameState.COW_BARN);

        //If originalStallIndexOfPregnant is 12, have NOT assigned PREGNANT cow.
        if (tempCowBarnState.getOriginalStallIndexOfPregnant() == 12) {
            if (tempPlayer.getEntityCurrentlyFacing() instanceof Cow) {
                Cow tempCow = (Cow) tempPlayer.getEntityCurrentlyFacing();

                if ((tempCow.getCowState() == Cow.CowState.ADULT_1) || (tempCow.getCowState() == Cow.CowState.ADULT_2) ||
                        (tempCow.getCowState() == Cow.CowState.ADULT_3)) {

                    // SAVING stall index number of the tempCow. Prevents multiple cows from becoming PREGNANT.
                    //see first if-clause of this execute() method.
                    tempCowBarnState.setOriginalStallIndexOfPregnant( tempCow.getFodderDisplayerTileArrayIndex() );

                    // dissociate tempCow from original stall.
                    tempCowBarnState.unassignCowFromFodderDisplayerTile(tempCow);
                    // associating cow to cow incubator stall.
                    tempCow.setFodderDisplayerTileArrayIndex(12);
                    tempCowBarnState.assignCowToFodderDisplayerTile(tempCow);


                    ////////////////////////////////////////////////////
                    tempCow.setCowState(Cow.CowState.PREGNANT);
                    //This SAVES a stall for the BABY.
                    tempCowBarnState.setStallIndexOfUnbornBaby(handler.getResourceManager().getCowCounter());
                    handler.getResourceManager().increaseCowCounter(1);
                    tempCow.increaseAffectionScore(10);
                    ////////////////////////////////////////////////////


                    // Cow impregnanted, return CowArtificialInseminator singleton-instance to shop.
                    ((TravelingFenceState)handler.getStateManager().getIState(StateManager.GameState.TRAVELING_FENCE)).getInventory().addItem( getUniqueInstance(handler) );
                    tempPlayer.getInventory().decrementSelectedItem();
                    for (int x = 0; x < tempPlayer.getInventory().getInventoryItems().size(); x++) {
                        if (tempPlayer.getInventory().getItem(x) instanceof CowArtificialInseminator) {
                            tempPlayer.getInventory().removeItem(x);
                            break;
                        }
                    }
                    System.out.println("CowArtificialInseminator used and returned to TravelingFenceState's inventory.");
                }
            }
        }
    }

} // **** end CowArtificialInseminator class ****