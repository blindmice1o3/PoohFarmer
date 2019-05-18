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

public class Dog extends Creature
        implements IHoldable {

    private Map<String, Animation> anim;

    private Random random;

    private boolean pickedUp;

    public Dog(Handler handler, float x, float y) {
        super(handler, (x + (Tile.TILE_WIDTH/4)), (y + (Tile.TILE_HEIGHT/4)),
                (Tile.TILE_WIDTH / 2), (Tile.TILE_HEIGHT / 2));

        initDogAnimations();

        setSpeed(5);

        random = new Random();
        pickedUp = false;
    } // **** end Dog(Handler, float, float) constructor ****

    private void initDogAnimations() {
        anim = new HashMap<String, Animation>();

        anim.put("animDogUp", new Animation(400, Assets.dogUp));
        anim.put("animDogDown", new Animation(400, Assets.dogDown));
        anim.put("animDogLeft", new Animation(400, Assets.dogLeft));
        anim.put("animDogRight", new Animation(400, Assets.dogRight));
    }

    @Override
    public void tick() {
        if (!pickedUp) {
            for (Animation tempAnim : anim.values()) {
                tempAnim.tick();
            }

            randomlyMove();
            move();
        }
    }

    private void randomlyMove() {
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
            return anim.get("animDogLeft").getCurrentFrame();
        } else if (xMove > 0) {                         // Moving right.
            return anim.get("animDogRight").getCurrentFrame();
        } else if (yMove < 0) {                         // Moving up.
            return anim.get("animDogUp").getCurrentFrame();
        } else if (yMove > 0) {                         // Moving down.
            return anim.get("animDogDown").getCurrentFrame();
        } else {
            return Assets.dogDown[0];
        }
    }

    @Override
    public void hurt(int amt) { return; }

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
                System.out.println("Dog.drop(Tile) switch construct's default option.");
                break;
        }

        if (!handler.getWorld().getEntityManager().getEntities().contains(this)) {
            handler.getWorld().getEntityManager().addEntity(this);
        }

        pickedUp = false;
    }

} // **** end Dog class ****