package edu.pooh.serialize_deserialize;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Creature;
import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.statics.statics1x1.DeadCow;
import edu.pooh.entities.statics.statics1x1.SpikeTrap;
import edu.pooh.inventory.Inventory;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.states.*;
import edu.pooh.tiles.*;
import edu.pooh.time.TimeManager;

import java.io.*;
import java.util.ArrayList;

public class SaverAndLoader {

    private Handler handler;

    public SaverAndLoader(Handler handler) {
        this.handler = handler;
    } // **** end SaverAndLoader(Handler) constructor ***

    public void save(String path) {
        if (path.substring(path.length()-4).equals(".bin")) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                System.out.println("SaverAndLoader.save(String)...");

                TimeManager timeManager = handler.getTimeManager();
                ResourceManager resourceManager = handler.getResourceManager();
                GameState gameState = (GameState)handler.getStateManager().getIState(StateManager.GameState.GAME);
                ArrayList<Entity> entities = gameState.getWorld().getEntityManager().getEntities();
                ArrayList<Item> items = gameState.getWorld().getItemManager().getItems();
                Tile[][] tiles = gameState.getWorld().getTilesViaRGB();

                objectOutputStream.writeObject(timeManager);
                objectOutputStream.writeObject(resourceManager);
                objectOutputStream.writeObject(entities);
                objectOutputStream.writeObject(items);
                objectOutputStream.writeObject(tiles);

                objectOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("String argument does NOT end with \".bin\".");
        }
    }

    public void load() {
        try (FileInputStream fileInputStream = new FileInputStream("pooh_farmer.bin")) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            System.out.println("SaverAndLoader.load()...");

            TimeManager timeManager = (TimeManager)objectInputStream.readObject();
            timeManager.setHandler(handler);
            handler.getGame().setTimeManager(timeManager);

            ResourceManager resourceManager = (ResourceManager)objectInputStream.readObject();
            resourceManager.setHandler(handler);
            handler.getGame().setResourceManager(resourceManager);

            ArrayList<Entity> entities = (ArrayList<Entity>)objectInputStream.readObject();
            GameState gameState = (GameState)handler.getGame().getStateManager().getIState(StateManager.GameState.GAME);
            for (Entity e : entities) {
                e.setHandler(handler);

                if (e instanceof Creature) {
                    ((Creature)e).initAnimations();
                } else if (e instanceof SpikeTrap) {
                    ((SpikeTrap)e).initAnimations();
                } else if (e instanceof DeadCow) {
                    ((DeadCow)e).initAnimations();
                }

                if (e instanceof Player) {
                    gameState.getWorld().getEntityManager().setPlayer((Player)e);

                    //TODO: after loading, the player's inventory appears empty. using VK_COMMA throws exception.
                    Inventory inventory = ((Player)e).getInventory();
                    inventory.setHandler(handler);
                    for (Item i : inventory.getInventoryItems()) {
                        i.setHandler(handler);

                        i.resetTexture();
                    }

                    ((Player)e).getStaminaModule().setHandler(handler);

                    ((Player)e).getMeleeAttackModule().setHandler(handler);

                    ((Player)e).getHeadUpDisplayer().setHandler(handler);
                }
            }
            gameState.getWorld().getEntityManager().setEntities(entities);


            ArrayList<Item> items = (ArrayList<Item>)objectInputStream.readObject();
            for (Item i : items) {
                i.setHandler(handler);

                i.resetTexture();
            }
            gameState.getWorld().getItemManager().setItems(items);


            Tile[][] tiles = (Tile[][])objectInputStream.readObject();
            for (Tile[] tiles1DArray : tiles) {
                for (Tile tileElement : tiles1DArray) {
                    if (tileElement instanceof BedTile) {
                        ((BedTile)tileElement).setHandler(handler);
                    } else if (tileElement instanceof FodderExecutorTile) {
                        ((FodderExecutorTile)tileElement).setHandler(handler);
                    } else if (tileElement instanceof FodderStashTile) {
                        ((FodderStashTile)tileElement).setHandler(handler);
                    } else if (tileElement instanceof HotSpringMountainTile) {
                        ((HotSpringMountainTile)tileElement).setHandler(handler);
                    } else if (tileElement instanceof SignPostTile) {
                        ((SignPostTile)tileElement).setHandler(handler);
                    } else if (tileElement instanceof WoodStashTile) {
                        ((WoodStashTile)tileElement).setHandler(handler);
                    }
                }
            }
            gameState.getWorld().setTilesViaRGB(tiles);


            objectInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

} // **** end SaverAndLoader class ****