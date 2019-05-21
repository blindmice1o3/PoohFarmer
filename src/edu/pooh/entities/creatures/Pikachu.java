package edu.pooh.entities.creatures;

import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.main.IHoldable;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Pikachu extends Creature
        implements IHoldable {

    private Map<String, Animation> animations;
    private Animation[] animationsArray;

    private Random random;
    private int randomInt;

    private boolean pickedUp;



    //@@@@@@@@@@@@@@@@@@@@@@@@
    private boolean following;
    //@@@@@@@@@@@@@@@@@@@@@@@@



    public Pikachu(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        //initialize animations HashMap.
        initAnimations();
        //initialize animationsArray array.
        animationsArray = new Animation[animations.values().size()];
        animations.values().toArray(animationsArray);

        setSpeed(5);

        random = new Random();
        pickedUp = false;



        //@@@@@@@@@@@@@@@@@@@@@@@@
        following = false;
        //@@@@@@@@@@@@@@@@@@@@@@@@



    } // **** end Pikachu(Handler, float, float) constructor ****

    public void initAnimations() {
        animations = new HashMap<String, Animation>();

        animations.put("pikachuWalkSW", new Animation(400, Assets.pikachuWalkSW));
        animations.put("pikachuWalkNW", new Animation(400, Assets.pikachuWalkNW));
        animations.put("pikachuWalkNE", new Animation(400, Assets.pikachuWalkNE));
        animations.put("pikachuWalkSE", new Animation(400, Assets.pikachuWalkSE));

        animations.put("pikachuRunSW", new Animation(400, Assets.pikachuRunSW));
        animations.put("pikachuRunNW", new Animation(400, Assets.pikachuRunNW));
        animations.put("pikachuRunNE", new Animation(400, Assets.pikachuRunNE));
        animations.put("pikachuRunSE", new Animation(400, Assets.pikachuRunSE));

        animations.put("pikachuAttackRegSW", new Animation(400, Assets.pikachuAttackRegSW));
        animations.put("pikachuAttackRegNW", new Animation(400, Assets.pikachuAttackRegNW));
        animations.put("pikachuAttackRegNE", new Animation(400, Assets.pikachuAttackRegNE));
        animations.put("pikachuAttackRegSE", new Animation(400, Assets.pikachuAttackRegSE));

        animations.put("pikachuAttackSpecialSW", new Animation(400, Assets.pikachuAttackSpecialSW));
        animations.put("pikachuAttackSpecialNW", new Animation(400, Assets.pikachuAttackSpecialNW));
        animations.put("pikachuAttackSpecialNE", new Animation(400, Assets.pikachuAttackSpecialNE));
        animations.put("pikachuAttackSpecialSE", new Animation(400, Assets.pikachuAttackSpecialSE));
    }

    @Override
    public void tick() {
        //@@@@@@@@@@@@@@@@@@@@@@@@
        if (following) {
            Player player = handler.getWorld().getEntityManager().getPlayer();
            switch (player.currentDirection) {
                case LEFT:
                    setX(player.getX()+Tile.TILE_WIDTH);
                    setY(player.getY());
                    return;
                case RIGHT:
                    setX(player.getX()-Tile.TILE_WIDTH);
                    setY(player.getY());
                    return;
                case UP:
                    setY(player.getY()+Tile.TILE_HEIGHT);
                    setX(player.getX());
                    return;
                case DOWN:
                    setY(player.getY()-Tile.TILE_HEIGHT);
                    setX(player.getX());
                    return;
                default:
                    System.out.println("Pikachu.tick() switch construct's (related to: following) default statement.");
                    return;
            }
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@





        if (!pickedUp) {
            for (Animation animation : animations.values()) {
                animation.tick();
            }

            randomlyMove();
            move();
        }
    }

    public void randomlyMove() {
        if (random.nextInt(100) == 1) {
            int moveDir = random.nextInt(5);

            switch (moveDir) {
                case 0:
                    xMove = -1;
                    break;
                case 1:
                    xMove = 1;
                    break;
                case 2:
                    yMove = -1;
                    break;
                case 3:
                    yMove = 1;
                    break;
                default:
                    xMove = 0;
                    yMove = 0;
                    randomInt = random.nextInt(animations.keySet().size());
                    break;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
        // ANIMATION MOVEMENTS
        if (xMove < 0) {                                // Moving left.
            return animations.get("pikachuWalkSW").getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return animations.get("pikachuWalkNW").getCurrentFrame();
        } else if (yMove < 0) {                         // Moving up.
            return animations.get("pikachuWalkNE").getCurrentFrame();
        } else if (yMove > 0) {                         // Moving down.
            return animations.get("pikachuWalkSE").getCurrentFrame();
        } else {
            return animationsArray[randomInt].getCurrentFrame();
        }
    }

    @Override
    public void hurt(int dmg) {
        return;
    }

    @Override
    public void die() {
        setActive(false);
    }

    // IHOLDABLE INTERFACE

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void pickedUp() {
        pickedUp = true;
    }

    @Override
    public void dropped(Tile t) {
        switch (handler.getWorld().getEntityManager().getPlayer().getCurrentDirection()) {
            case UP:
                x = handler.getWorld().getEntityManager().getPlayer().getX();
                y = handler.getWorld().getEntityManager().getPlayer().getY() - Tile.TILE_HEIGHT;
                break;
            case DOWN:
                x = handler.getWorld().getEntityManager().getPlayer().getX();
                y = handler.getWorld().getEntityManager().getPlayer().getY() + Tile.TILE_HEIGHT;
                break;
            case LEFT:
                x = handler.getWorld().getEntityManager().getPlayer().getX() - Tile.TILE_WIDTH;
                y = handler.getWorld().getEntityManager().getPlayer().getY();
                break;
            case RIGHT:
                x = handler.getWorld().getEntityManager().getPlayer().getX() + Tile.TILE_HEIGHT;
                y = handler.getWorld().getEntityManager().getPlayer().getY();
                break;
            default:
                System.out.println("Pikachu.drop(Tile) switch construct's default option.");
                break;
        }

        if (!handler.getWorld().getEntityManager().getEntities().contains(this)) {
            handler.getWorld().getEntityManager().addEntity(this);
        }

        pickedUp = false;
    }

    // GETTERS AND SETTERS

    public boolean isFollowing() { return following; }
    public void setFollowing(boolean following) { this.following = following; }

} // **** end Pikachu class ****