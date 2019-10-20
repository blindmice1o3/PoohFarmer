package edu.pooh.serialize_deserialize;

import edu.pooh.entities.Entity;
import edu.pooh.entities.creatures.live_stocks.Chicken;
import edu.pooh.entities.creatures.player.Player;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.states.*;
import edu.pooh.tiles.FodderDisplayerTile;
import edu.pooh.tiles.FodderExecutorTile;
import edu.pooh.tiles.Tile;
import edu.pooh.time.TimeManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

                StateManager stateManager = handler.getStateManager();
                TimeManager timeManager = handler.getTimeManager();
                ResourceManager resourceManager = handler.getResourceManager();

                objectOutputStream.writeObject(stateManager);
                objectOutputStream.writeObject(timeManager);
                objectOutputStream.writeObject(resourceManager);


                /*
                ////////////////////////////////////////////////////////////////////
                //should be GameStageState
                GameStageState gameStageState = (GameStageState) handler.getGame().getStateManager().getState(StateManager.State.GAME_STAGE);
                GameStage currentGameStage = gameStageState.getCurrentGameStage();

                if (currentGameStage.getIdentifier() == GameStage.Identifier.EVO) {
                    FishStateManager fishStateManager = ((Fish) currentGameStage.getPlayer()).getFishStateManager();
                    Fish.DirectionFacing directionFacing = ((Fish) currentGameStage.getPlayer()).getDirectionFacing();
                    float x = ((Fish) currentGameStage.getPlayer()).getX();
                    float y = ((Fish) currentGameStage.getPlayer()).getY();
                    ArrayList<Entity> entities = currentGameStage.getEntityManager().getEntities();
                    ArrayList<Item> items = currentGameStage.getItemManager().getItems();
                    ArrayList<ComponentHUD> timedNumericIndicators = currentGameStage.getHeadUpDisplay().getTimedNumericIndicators();
                    ArrayList<Quest> quests = gameStageState.getQuestManager().getQuests();


                    objectOutputStream.writeObject(fishStateManager);
                    objectOutputStream.writeObject(directionFacing);
                    objectOutputStream.writeFloat(x);
                    objectOutputStream.writeFloat(y);
                    objectOutputStream.writeObject(entities);
                    objectOutputStream.writeObject(items);
                    objectOutputStream.writeObject(timedNumericIndicators);
                    objectOutputStream.writeObject(quests);
                }
                ////////////////////////////////////////////////////////////////////
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


            StateManager stateManager = (StateManager)objectInputStream.readObject();
            TimeManager timeManager = (TimeManager)objectInputStream.readObject();
            ResourceManager resourceManager = (ResourceManager)objectInputStream.readObject();

            handler.getGame().setStateManager(stateManager);
            handler.getGame().setTimeManager(timeManager);
            handler.getGame().setResourceManager(resourceManager);

            for (IState concreteIState : handler.getGame().getStateManager().getStates().values()) {
                concreteIState.setHandler(handler);

                if (concreteIState instanceof ChickenCoopState) {
                    ((ChickenCoopState)concreteIState).getWorld().setHandler(handler);
                    ((ChickenCoopState)concreteIState).getWorld().getEntityManager().setHandler(handler);
                    for (Entity entity : ((ChickenCoopState)concreteIState).getWorld().getEntityManager().getEntities()) {
                        entity.setHandler(handler);
                    }
                    for (Tile[] tiles : ((ChickenCoopState)concreteIState).getWorld().getTilesViaRGB()) {
                        for (Tile tile : tiles) {
                            if (tile instanceof FodderDisplayerTile) {
                                ((FodderDisplayerTile)tile).setHandler(handler);
                            }
                        }
                    }
                } else if (concreteIState instanceof CowBarnState) {
                    ((CowBarnState)concreteIState).getWorld().setHandler(handler);
                    ((CowBarnState)concreteIState).getWorld().getEntityManager().setHandler(handler);
                    for (Entity entity : ((CowBarnState)concreteIState).getWorld().getEntityManager().getEntities()) {
                        entity.setHandler(handler);
                    }
                    for (Tile[] tiles : ((CowBarnState)concreteIState).getWorld().getTilesViaRGB()) {
                        for (Tile tile : tiles) {
                            if (tile instanceof FodderDisplayerTile) {
                                ((FodderDisplayerTile)tile).setHandler(handler);
                            } else if (tile instanceof FodderExecutorTile) {
                                ((FodderExecutorTile)tile).setHandler(handler);
                            }
                        }
                    }
                } else if (concreteIState instanceof CrossroadState) {
                    ((CrossroadState)concreteIState).getWorld().setHandler(handler);
                    ((CrossroadState)concreteIState).getWorld().getEntityManager().setHandler(handler);
                    for (Entity entity : ((CrossroadState)concreteIState).getWorld().getEntityManager().getEntities()) {
                        entity.setHandler(handler);
                    }
                } else if (concreteIState instanceof GameState) {
                    ((GameState)concreteIState).getWorld().setHandler(handler);
                    ((GameState)concreteIState).getWorld().getEntityManager().setHandler(handler);
                    for (Entity entity : ((GameState)concreteIState).getWorld().getEntityManager().getEntities()) {
                        entity.setHandler(handler);

                        if (entity instanceof Player) {
                            ((Player)entity).getInventory().setHandler(handler);
                            ((Player)entity).getMeleeAttackModule().setHandler(handler);
                        }
                    }
                } else if (concreteIState instanceof HomeState) {
                    ((HomeState)concreteIState).getWorld().setHandler(handler);
                    ((HomeState)concreteIState).getWorld().getEntityManager().setHandler(handler);
                    for (Entity entity : ((HomeState)concreteIState).getWorld().getEntityManager().getEntities()) {
                        entity.setHandler(handler);
                    }
                } else if (concreteIState instanceof MountainState) {
                    ((MountainState)concreteIState).getWorld().setHandler(handler);
                    ((MountainState)concreteIState).getWorld().getEntityManager().setHandler(handler);
                    for (Entity entity : ((MountainState)concreteIState).getWorld().getEntityManager().getEntities()) {
                        entity.setHandler(handler);
                    }
                } else if (concreteIState instanceof TheWestState) {
                    ((TheWestState)concreteIState).getWorld().setHandler(handler);
                    ((TheWestState)concreteIState).getWorld().getEntityManager().setHandler(handler);
                    for (Entity entity : ((TheWestState)concreteIState).getWorld().getEntityManager().getEntities()) {
                        entity.setHandler(handler);
                    }
                } else if (concreteIState instanceof ToolShedState) {
                    ((ToolShedState)concreteIState).getWorld().setHandler(handler);
                    ((ToolShedState)concreteIState).getWorld().getEntityManager().setHandler(handler);
                    for (Entity entity : ((ToolShedState)concreteIState).getWorld().getEntityManager().getEntities()) {
                        entity.setHandler(handler);
                    }
                }
            }
            /*
            ////////////////////////////////////////////////////////////////////
            //should be GameStageState
            GameStageState gameStageState = (GameStageState) handler.getGame().getStateManager().getState(StateManager.State.GAME_STAGE);
            GameStage currentGameStage = gameStageState.getCurrentGameStage();

            if (currentGameStage.getIdentifier() == GameStage.Identifier.EVO) {
                FishStateManager fishStateManager = (FishStateManager) objectInputStream.readObject();
                Fish.DirectionFacing directionFacing = (Fish.DirectionFacing) objectInputStream.readObject();
                float x = objectInputStream.readFloat();
                float y = objectInputStream.readFloat();
                ArrayList<Entity> entities = (ArrayList<Entity>) objectInputStream.readObject();
                ArrayList<Item> items = (ArrayList<Item>) objectInputStream.readObject();
                ArrayList<ComponentHUD> timedNumericIndicators = (ArrayList<ComponentHUD>) objectInputStream.readObject();
                ArrayList<Quest> quests = (ArrayList<Quest>) objectInputStream.readObject();

                fishStateManager.setHandler(handler);

                ((Fish)currentGameStage.getPlayer()).setFishStateManager(fishStateManager);
                ((Fish)currentGameStage.getPlayer()).tick(0);
                ((Fish)currentGameStage.getPlayer()).setDirectionFacing(directionFacing);
                ((Fish)currentGameStage.getPlayer()).setX(x);
                ((Fish)currentGameStage.getPlayer()).setY(y);
                currentGameStage.getEntityManager().setEntities(entities);
                currentGameStage.getItemManager().setItems(items);
                currentGameStage.getHeadUpDisplay().setTimedNumericIndicators(timedNumericIndicators);
                gameStageState.getQuestManager().setQuests(quests);

                for (Entity e : currentGameStage.getEntityManager().getEntities()) {
                    e.initAnimations();

                    e.setHandler(handler);

                    if (e instanceof Fish) {
                        currentGameStage.getEntityManager().setPlayer((Fish)e);
                    }
                }

                for (Item i : currentGameStage.getItemManager().getItems()) {
                    if (i.getName().equals("Meat")) {
                        i.setTexture(Assets.meat);
                    }

                    i.setHandler(handler);
                }

                for (ComponentHUD componentHUD : currentGameStage.getHeadUpDisplay().getTimedNumericIndicators()) {
                    componentHUD.setHandler(handler);
                }

                for (Quest quest : gameStageState.getQuestManager().getQuests()) {
                    quest.setHandler(handler);
                }
            }
            ////////////////////////////////////////////////////////////////////
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