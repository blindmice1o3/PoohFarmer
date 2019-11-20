package edu.pooh.serialize_deserialize;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.Creature;
import edu.pooh.entities.creatures.player.Player;
import edu.pooh.entities.statics.StaticEntity;
import edu.pooh.entities.statics.statics1x1.DeadCow;
import edu.pooh.entities.statics.statics1x1.SpikeTrap;
import edu.pooh.gfx.Assets;
import edu.pooh.inventory.Inventory;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.items.Item;
import edu.pooh.main.Handler;
import edu.pooh.states.*;
import edu.pooh.tiles.*;
import edu.pooh.time.TimeManager;

import javax.imageio.ImageIO;
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

                objectOutputStream.writeObject(timeManager);
                objectOutputStream.writeObject(resourceManager);
                objectOutputStream.writeObject(entities);
                objectOutputStream.writeObject(items);


                ///////////////////////////////////////////////////////
                Tile[][] tiles = gameState.getWorld().getTilesViaRGB();
                for (int y = 0; y < tiles.length; y++) {
                    for (int x = 0; x < tiles[0].length; x++) {
                        if (tiles[y][x] instanceof DirtNormalTile) {
                            objectOutputStream.writeObject(((DirtNormalTile)tiles[y][x]).getDirtState());
                            objectOutputStream.writeObject(((DirtNormalTile)tiles[y][x]).isWatered());
                        } else {
                            System.out.println("SaverAndLoader.save(), skipping, NOT a DirtNormalTile.");
                        }
                    }
                }
                ///////////////////////////////////////////////////////


                /*
                objectOutputStream.writeObject(tiles);
                System.out.println("tiles written");
                ///////////////////////////////////////////
                objectOutputStream.writeInt(widthInTiles);
                System.out.println("widthInTiles written: " + widthInTiles);
                objectOutputStream.writeInt(heightInTiles);
                System.out.println("heightInTiles written: " + heightInTiles);
                ///////////////////////////////////////////
                int counter = 0;
                int totalNumOfTiles = widthInTiles * heightInTiles;
                for (int y = 0; y < heightInTiles; y++) {
                    for (int x = 0; x < widthInTiles; x++) {
                        ///////////////////////////////////////////////////////////////////////////////
                        ImageIO.write(tiles[x][y].getTexture(), "png", objectOutputStream);
                        counter++;
                        System.out.println(counter + " tile's texture written out of " + totalNumOfTiles);
                        ///////////////////////////////////////////////////////////////////////////////
                    }
                }
                */

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
            /////////////////////////////////////////////////////////////////////////
            Tile[][] tiles = gameState.getWorld().getTilesViaRGB();
            //clear current game's tile instances of their reference to StaticEntity.
            for (Tile[] tileArray : tiles) {
                for (Tile t : tileArray) {
                    if (t instanceof DirtNormalTile) {
                        ((DirtNormalTile)t).setStaticEntity(null);
                    } else if (t instanceof DirtNotFarmableTile) {
                        ((DirtNotFarmableTile)t).setStaticEntity(null);
                    }
                }
            }
            /////////////////////////////////////////////////////////////////////////
            for (Entity e : entities) {
                e.setHandler(handler);

                if (e instanceof Creature) {
                    ((Creature)e).initAnimations();
                } else if (e instanceof SpikeTrap) {
                    ((SpikeTrap)e).initAnimations();
                } else if (e instanceof DeadCow) {
                    ((DeadCow)e).initAnimations();
                }

                /////////////////////////////////////////////////////////////////////////
                //set the reloaded StaticEntity to the tile's reference.
                if (e instanceof StaticEntity) {
                    int xTileIndex = (int)(e.getX() / Tile.TILE_WIDTH);
                    int yTileIndex = (int)(e.getY() / Tile.TILE_HEIGHT);
                    Tile t = ((GameState)handler.getStateManager().getIState(StateManager.GameState.GAME)).getWorld().getTile(xTileIndex, yTileIndex);

                    if (t instanceof DirtNormalTile) {
                        ((DirtNormalTile)t).setStaticEntity((StaticEntity)e);
                    } else if (t instanceof DirtNotFarmableTile) {
                        ((DirtNotFarmableTile)t).setStaticEntity((StaticEntity)e);
                    }
                }
                /////////////////////////////////////////////////////////////////////////

                if (e instanceof Player) {
                    gameState.setPlayer((Player)e);
                    gameState.getWorld().getEntityManager().setPlayer((Player)e);

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


            ///////////////////////////////////////////////////////
            for (int y = 0; y < tiles.length; y++) {
                for (int x = 0; x < tiles[0].length; x++) {
                    if (tiles[y][x] instanceof DirtNormalTile) {
                        ((DirtNormalTile)tiles[y][x]).setDirtState((DirtNormalTile.DirtState)objectInputStream.readObject());
                        ((DirtNormalTile)tiles[y][x]).setWatered((boolean)objectInputStream.readObject());

                        switch (((DirtNormalTile)tiles[y][x]).getDirtState()) {
                            case NORMAL:
                                ((DirtNormalTile)tiles[y][x]).setTexture(Assets.dirtNormal);
                                break;
                            case TILLED:
                                if (((DirtNormalTile)tiles[y][x]).isWatered()) {
                                    ((DirtNormalTile)tiles[y][x]).setTexture(Assets.dirtTilledWatered);
                                } else {
                                    ((DirtNormalTile)tiles[y][x]).setTexture(Assets.dirtTilledDry);
                                }
                                break;
                            case SEEDED:
                                if (((DirtNormalTile)tiles[y][x]).isWatered()) {
                                    ((DirtNormalTile)tiles[y][x]).setTexture(Assets.dirtSeededWatered);
                                } else {
                                    ((DirtNormalTile)tiles[y][x]).setTexture(Assets.dirtSeededDry);
                                }
                                break;
                            default:
                                System.out.println("SaverAndLoader.load(), tiles... setting texture... switch construct's default.");
                                break;
                        }
                    } else {
                        System.out.println("SaverAndLoader.load(), skipping, NOT a DirtNormalTile.");
                    }
                }
            }
            ///////////////////////////////////////////////////////

            /*
            Tile[][] tiles = (Tile[][])objectInputStream.readObject();
            final int widthInTiles = objectInputStream.readInt();
            final int heightInTiles = objectInputStream.readInt();
            for (int y = 0; y < heightInTiles; y++) {
                for (int x = 0; x < widthInTiles; x++) {
                    //texture
                    tiles[x][y].setTexture( ImageIO.read(objectInputStream) );

                    //handler
                    if (tiles[x][y] instanceof BedTile) {
                        ((BedTile)tiles[x][y]).setHandler(handler);
                    } else if (tiles[x][y] instanceof FodderExecutorTile) {
                        ((FodderExecutorTile)tiles[x][y]).setHandler(handler);
                    } else if (tiles[x][y] instanceof FodderStashTile) {
                        ((FodderStashTile)tiles[x][y]).setHandler(handler);
                    } else if (tiles[x][y] instanceof HotSpringMountainTile) {
                        ((HotSpringMountainTile)tiles[x][y]).setHandler(handler);
                    } else if (tiles[x][y] instanceof SignPostTile) {
                        ((SignPostTile)tiles[x][y]).setHandler(handler);
                    } else if (tiles[x][y] instanceof WoodStashTile) {
                        ((WoodStashTile)tiles[x][y]).setHandler(handler);
                    }
                }
            }
            gameState.getWorld().setTilesViaRGB(tiles);
            */

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