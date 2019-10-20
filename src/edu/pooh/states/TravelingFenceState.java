package edu.pooh.states;

import edu.pooh.entities.creatures.player.Player;
import edu.pooh.gfx.Assets;
import edu.pooh.inventory.Inventory;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.items.Item;
import edu.pooh.items.live_stocks.*;
import edu.pooh.items.crops.tier0.*;
import edu.pooh.items.crops.tier1.GoldShovel;
import edu.pooh.items.crops.tier1.GoldSprinkler;
import edu.pooh.main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class TravelingFenceState
        implements IState, Serializable {

    private transient Handler handler;

    private Object[] args;
    private Player player;

    ////////////////////////////
    private Inventory inventory;
    ///////////////////////////

    public TravelingFenceState(Handler handler) {
        this.handler = handler;

        initInventory();
    } // **** end TravelingFenceState(Handler) constructor ****


    @Override
    public void enter(Object[] args) {
        handler.getTimeManager().setClockRunningFalse();

        this.args = args;

        if ((args[0] != null) && (args[0] instanceof Player)) {
            player = (Player)args[0];
        }
    }

    @Override
    public void exit() {
        args[0] = player;
    }

    @Override
    public void tick() {
        // VK_ESCAPE will set state to GameState.
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            inventory.setActive(false);
            handler.getStateManager().popIState();

            //positions the player to where they entered from.
            IState currentState = handler.getStateManager().getCurrentState();
            GameState gameState = (GameState)handler.getStateManager().getIState(StateManager.GameState.GAME);
            currentState.enter(gameState.getArgs());
        }

        inventory.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.shoppingScreen, 10, 10,
                handler.getWidth()-20, handler.getHeight()-20, null);

        inventory.render(g);
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void initInventory() {
        inventory = new Inventory(handler) {
            @Override
            public void addItem(Item item) {
                System.out.println("TravelingFenceState's special-Inventory's addItem(Item) method.");
                getInventoryItems().add(item);
            }
            @Override
            public void tick() {
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
                    setActive( !isActive() );
                }
                if (!isActive()) {
                    return;
                }

                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                    decrementSelectedItem();
                }
                else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                    incrementSelectedItem();
                }


                //EXECUTE buying
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    buyItemAtCurrentIndex();

                    System.out.println("A-Button (VK_COMMA) just pressed.");
                }
                //NO FUNCTIONALITY!!!!!!
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    System.out.println("B-Button (VK_PERIOD) just pressed.");
                }
            }

            public boolean checkCanShopperAfford(int expense) {
                return (handler.getResourceManager().getCurrencyUnitCount() >= expense);
            }

            public void buyItemAtCurrentIndex() {
                if (getInventoryItems().size() == 0) {
                    System.out.println("NOTHING TO BUY!");
                    return;
                }

                if ( checkCanShopperAfford( checkPrice(getItem(getIndex())) ) ) {
                    handler.getResourceManager().decreaseCurrencyUnitCount( checkPrice(getItem(getIndex())) );
                    player.getInventory().addItem(getItem(getIndex()));
                    removeItem(getIndex());

                    // If index has been moved outside of bound, set it as one of the edge cases (min or max).
                    if (getIndex() < 0) {
                        setIndex( (getInventoryItems().size() - 1) );
                    } else if (getIndex() >= getInventoryItems().size()) {
                        setIndex(0);
                    }
                } else {
                    System.out.println("CAN'T AFFORD, probably not enough currencyUnitCount in ResourceManager class.");
                }
            }

        };

        inventory.addItem(CowMilker.getUniqueInstance(handler));
        inventory.addItem(CowBrush.getUniqueInstance(handler));
        inventory.addItem(Axe.getUniqueInstance(handler));
        inventory.addItem(Hammer.getUniqueInstance(handler));
        inventory.addItem(Scythe.getUniqueInstance(handler));
        inventory.addItem(GoldShovel.getUniqueInstance(handler));
        inventory.addItem(GoldSprinkler.getUniqueInstance(handler));
        inventory.addItem(new SeedsWild(handler));
        if (inventory.getItem(7) instanceof SeedsWild) {
            ((SeedsWild)inventory.getItem(7)).setSeedType(SeedsWild.SeedType.TOMATO);
            ((SeedsWild)inventory.getItem(7)).setName("Tomato seeds");
        }
        inventory.addItem(new SeedsWild(handler));
        if (inventory.getItem(8) instanceof SeedsWild) {
            ((SeedsWild)inventory.getItem(8)).setSeedType(SeedsWild.SeedType.GRASS);
            ((SeedsWild)inventory.getItem(8)).setName("Grass seeds");
        }
        inventory.addItem(ChickenSpontaneousGenerator.getUniqueInstance(handler));
        inventory.addItem(CowSpontaneousGenerator.getUniqueInstance(handler));
        inventory.addItem(CowArtificialInseminator.getUniqueInstance(handler));
    }

    private int checkPrice(Item item) {
        if ((item instanceof SeedsWild) && (((SeedsWild)item).getSeedType() == SeedsWild.SeedType.TURNIP)) {
            return 200;
        } else if ((item instanceof SeedsWild) && (((SeedsWild)item).getSeedType() == SeedsWild.SeedType.POTATO)) {
            return 200;
        } else if ((item instanceof SeedsWild) && (((SeedsWild)item).getSeedType() == SeedsWild.SeedType.TOMATO)) {
            return 300;
        } else if ((item instanceof SeedsWild) && (((SeedsWild)item).getSeedType() == SeedsWild.SeedType.CORN)) {
            return 300;
        } else if ((item instanceof SeedsWild) && (((SeedsWild)item).getSeedType() == SeedsWild.SeedType.CANNABIS_WILD)) {
            return 350;
        } else if ((item instanceof SeedsWild) && (((SeedsWild)item).getSeedType() == SeedsWild.SeedType.GRASS)) {
            return 500;
        } else if (item instanceof Scythe) {
            return 200;
        } else if (item instanceof Hammer) {
            return 200;
        } else if (item instanceof Axe) {
            return 200;
        } else if (item instanceof GoldShovel) {
            return 500;
        } else if (item instanceof GoldSprinkler) {
            return 1000;
        } else if (item instanceof CowBrush) {
            return 150;
        } else if (item instanceof CowMilker) {
            return 350;
        } else if (item instanceof ChickenSpontaneousGenerator) {
            return 1000;
        } else if (item instanceof CowSpontaneousGenerator) {
            return 5000;
        } else if (item instanceof CowArtificialInseminator) {
            return 4000;
        } else {
            return 300000000;
        }
    }

    // GETTERS AND SETTERS

    public Inventory getInventory() { return inventory; }

} // **** end TravelingFenceState class ****