package edu.pooh.states;

import edu.pooh.entities.creatures.Player;
import edu.pooh.gfx.Assets;
import edu.pooh.inventory.Inventory;
import edu.pooh.items.Item;
import edu.pooh.items.tier0.SeedsWild;
import edu.pooh.items.tier1.GoldShovel;
import edu.pooh.items.tier1.GoldSprinkler;
import edu.pooh.main.Handler;
import edu.pooh.time.TimeManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TravelingFenceState implements IState {

    private Handler handler;

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
        TimeManager.setClockRunningFalse();

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
            StateManager.change(handler.getGame().getGameState(), args);
        }

        inventory.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.shoppingScreen, 10, 10,
                handler.getWidth()-20, handler.getHeight()-20, null);

        inventory.render(g);
    }

    private void initInventory() {
        inventory = new Inventory(handler) {
            @Override
            public void addItem(Item item) {
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
                    setIndex( (getIndex() - 1) );
                }
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                    setIndex( (getIndex() + 1) );
                }

                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                    buyItemAtCurrentIndex();

                    System.out.println("A-Button (VK_COMMA) just pressed.");
                }
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                    System.out.println("B-Button (VK_PERIOD) just pressed.");
                }

                if (getIndex() < 0) {
                    setIndex( (getInventoryItems().size() - 1) );
                }
                else if (getIndex() >= getInventoryItems().size()) {
                    setIndex(0);
                }
            }

            public boolean checkCanShopperAfford(int expense) {
                return (player.getCurrencyUnit() >= expense);
            }

            public void buyItemAtCurrentIndex() {
                if (getInventoryItems().size() == 0) {
                    System.out.println("NOTHING TO BUY!");
                    return;
                }

                if ( checkCanShopperAfford( checkPrice(getItem(getIndex())) ) ) {
                    player.decreaseCurrencyUnit( checkPrice(getItem(getIndex())) );
                    player.getInventory().addItem(getItem(getIndex()));
                    removeItem(getIndex());
                }
            }

        };

        inventory.addItem(GoldShovel.getUniqueInstance(handler));
        inventory.addItem(GoldSprinkler.getUniqueInstance(handler));
        inventory.addItem(new SeedsWild(handler));
        if(inventory.getItem(2) instanceof SeedsWild) {
            ((SeedsWild)inventory.getItem(2)).setSeedType(SeedsWild.SeedType.CANNABIS_WILD);
            ((SeedsWild)inventory.getItem(2)).setName("pure-breeding Cannabis indica seeds");
        }
        inventory.addItem(new SeedsWild(handler));
        if(inventory.getItem(3) instanceof SeedsWild) {
            ((SeedsWild)inventory.getItem(3)).setSeedType(SeedsWild.SeedType.TURNIP);
            ((SeedsWild)inventory.getItem(3)).setName("Turnip seeds");
        }
        inventory.addItem(new SeedsWild(handler));
        if(inventory.getItem(4) instanceof SeedsWild) {
            ((SeedsWild)inventory.getItem(4)).setSeedType(SeedsWild.SeedType.POTATO);
            ((SeedsWild)inventory.getItem(4)).setName("Potato seeds");
        }
        inventory.addItem(new SeedsWild(handler));
        if(inventory.getItem(5) instanceof SeedsWild) {
            ((SeedsWild)inventory.getItem(5)).setSeedType(SeedsWild.SeedType.TOMATO);
            ((SeedsWild)inventory.getItem(5)).setName("Tomato seeds");
        }
        inventory.addItem(new SeedsWild(handler));
        if(inventory.getItem(6) instanceof SeedsWild) {
            ((SeedsWild)inventory.getItem(6)).setSeedType(SeedsWild.SeedType.CORN);
            ((SeedsWild)inventory.getItem(6)).setName("Corn seeds");
        }
        inventory.addItem(new SeedsWild(handler));
        if(inventory.getItem(7) instanceof SeedsWild) {
            ((SeedsWild)inventory.getItem(7)).setSeedType(SeedsWild.SeedType.CANNABIS_WILD);
            ((SeedsWild)inventory.getItem(7)).setName("pure-breeding Cannabis indica seeds");
        }
        inventory.addItem(new SeedsWild(handler));
        if(inventory.getItem(8) instanceof SeedsWild) {
            ((SeedsWild)inventory.getItem(8)).setSeedType(SeedsWild.SeedType.CANNABIS_WILD);
            ((SeedsWild)inventory.getItem(8)).setName("pure-breeding Cannabis sativa seeds");
        }
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
            return 300;
        } else if (item instanceof GoldShovel) {
            return 500;
        } else if (item instanceof GoldSprinkler) {
            return 1000;
        } else {
            return 3000;
        }
    }

} // **** end TravelingFenceState class ****