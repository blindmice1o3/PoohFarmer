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
import edu.pooh.worlds.World;

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

                TimeManager timeManager = handler.getTimeManager();
                ResourceManager resourceManager = handler.getResourceManager();

                objectOutputStream.writeObject(timeManager);
                objectOutputStream.writeObject(resourceManager);


                for (IState concreteIState : handler.getGame().getStateManager().getStates().values()) {
                    if (concreteIState instanceof ChickenCoopState) {

                        objectOutputStream.writeObject( ((ChickenCoopState)concreteIState).getWorld() );

                    } else if (concreteIState instanceof CowBarnState) {

                        objectOutputStream.writeObject( ((CowBarnState)concreteIState).getWorld() );

                    } else if (concreteIState instanceof CrossroadState) {

                        objectOutputStream.writeObject( ((CrossroadState)concreteIState).getWorld() );

                    } else if (concreteIState instanceof GameState) {

                        objectOutputStream.writeObject( ((GameState)concreteIState).getWorld() );

                    } else if (concreteIState instanceof HomeState) {

                        objectOutputStream.writeObject( ((HomeState)concreteIState).getWorld() );

                    } else if (concreteIState instanceof MountainState) {

                        objectOutputStream.writeObject( ((MountainState)concreteIState).getWorld() );

                    } else if (concreteIState instanceof TheWestState) {

                        objectOutputStream.writeObject( ((TheWestState)concreteIState).getWorld() );

                    } else if (concreteIState instanceof ToolShedState) {

                        objectOutputStream.writeObject( ((ToolShedState)concreteIState).getWorld() );

                    }
                }
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

            TimeManager timeManager = (TimeManager)objectInputStream.readObject();
            timeManager.setHandler(handler);
            ResourceManager resourceManager = (ResourceManager)objectInputStream.readObject();
            resourceManager.setHandler(handler);

            handler.getGame().setTimeManager(timeManager);
            handler.getGame().setResourceManager(resourceManager);


            for (int i = 0; i < 8; i++) {
                World world = (World)objectInputStream.readObject();

                world.setHandler(handler);

                switch (world.getWorldType()) {
                    case CHICKEN_COOP:
                        ChickenCoopState chickenCoopState = (ChickenCoopState)handler.getGame().getStateManager().getIState(StateManager.GameState.CHICKEN_COOP);
                        chickenCoopState.setHandler(handler);
                        chickenCoopState.setWorld(world);

                        chickenCoopState.getWorld().getEntityManager().setHandler(handler);
                        for (Entity entity : chickenCoopState.getWorld().getEntityManager().getEntities()) {
                            entity.setHandler(handler);
                        }
                        for (Tile[] tiles : chickenCoopState.getWorld().getTilesViaRGB()) {
                            for (Tile tile : tiles) {
                                if (tile instanceof FodderDisplayerTile) {
                                    ((FodderDisplayerTile)tile).setHandler(handler);
                                }
                            }
                        }
                        break;
                    case COW_BARN:
                        CowBarnState cowBarnState = (CowBarnState)handler.getGame().getStateManager().getIState(StateManager.GameState.COW_BARN);
                        cowBarnState.setHandler(handler);
                        cowBarnState.setWorld(world);

                        cowBarnState.getWorld().getEntityManager().setHandler(handler);
                        for (Entity entity : cowBarnState.getWorld().getEntityManager().getEntities()) {
                            entity.setHandler(handler);
                        }
                        for (Tile[] tiles : cowBarnState.getWorld().getTilesViaRGB()) {
                            for (Tile tile : tiles) {
                                if (tile instanceof FodderDisplayerTile) {
                                    ((FodderDisplayerTile)tile).setHandler(handler);
                                } else if (tile instanceof FodderExecutorTile) {
                                    ((FodderExecutorTile)tile).setHandler(handler);
                                }
                            }
                        }
                        break;
                    case CROSSROAD:
                        CrossroadState crossroadState = (CrossroadState)handler.getGame().getStateManager().getIState(StateManager.GameState.CROSSROAD);
                        crossroadState.setHandler(handler);
                        crossroadState.setWorld(world);

                        crossroadState.getWorld().getEntityManager().setHandler(handler);
                        for (Entity entity : crossroadState.getWorld().getEntityManager().getEntities()) {
                            entity.setHandler(handler);
                        }
                        break;
                    case GAME:
                        GameState gameState = (GameState)handler.getGame().getStateManager().getIState(StateManager.GameState.GAME);
                        gameState.setHandler(handler);
                        gameState.setWorld(world);

                        gameState.getWorld().getEntityManager().setHandler(handler);
                        for (Entity entity : gameState.getWorld().getEntityManager().getEntities()) {
                            entity.setHandler(handler);

                            if (entity instanceof Player) {
                                ((Player)entity).getInventory().setHandler(handler);
                                ((Player)entity).getMeleeAttackModule().setHandler(handler);
                            }
                        }
                        break;
                    case HOME:
                        HomeState homeState = (HomeState)handler.getGame().getStateManager().getIState(StateManager.GameState.HOME);
                        homeState.setHandler(handler);
                        homeState.setWorld(world);

                        homeState.getWorld().getEntityManager().setHandler(handler);
                        for (Entity entity : homeState.getWorld().getEntityManager().getEntities()) {
                            entity.setHandler(handler);
                        }
                        break;
                    case MOUNTAIN:
                        MountainState mountainState = (MountainState)handler.getGame().getStateManager().getIState(StateManager.GameState.MOUNTAIN);
                        mountainState.setHandler(handler);
                        mountainState.setWorld(world);

                        mountainState.getWorld().getEntityManager().setHandler(handler);
                        for (Entity entity : mountainState.getWorld().getEntityManager().getEntities()) {
                            entity.setHandler(handler);
                        }
                        break;
                    case THE_WEST:
                        TheWestState theWestState = (TheWestState)handler.getGame().getStateManager().getIState(StateManager.GameState.THE_WEST);
                        theWestState.setHandler(handler);
                        theWestState.setWorld(world);

                        theWestState.getWorld().getEntityManager().setHandler(handler);
                        for (Entity entity : theWestState.getWorld().getEntityManager().getEntities()) {
                            entity.setHandler(handler);
                        }
                        break;
                    case TOOL_SHED:
                        ToolShedState toolShedState = (ToolShedState)handler.getGame().getStateManager().getIState(StateManager.GameState.TOOL_SHED);
                        toolShedState.setHandler(handler);
                        toolShedState.setWorld(world);

                        toolShedState.getWorld().getEntityManager().setHandler(handler);
                        for (Entity entity : toolShedState.getWorld().getEntityManager().getEntities()) {
                            entity.setHandler(handler);
                        }
                        break;
                    default:
                        System.out.println("SaverAndLoader.load(), switch construct's default.");
                        break;
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