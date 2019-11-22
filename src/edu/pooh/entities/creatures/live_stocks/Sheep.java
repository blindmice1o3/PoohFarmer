package edu.pooh.entities.creatures.live_stocks;

import edu.pooh.entities.creatures.Creature;
import edu.pooh.gfx.Animation;
import edu.pooh.gfx.Assets;
import edu.pooh.main.Handler;
import edu.pooh.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Sheep extends Creature {

    private transient Map<String, Animation> anim;

    private Random random;

    public Sheep(Handler handler, float x, float y) {
        super(handler, x, y, ((3 * Tile.TILE_WIDTH) / 4), ((3 * Tile.TILE_HEIGHT) / 4));

        initAnimations();

        random = new Random();
    } // **** end Sheep(Handler, float, float) constructor ****

    @Override
    public void initAnimations() {
        anim = new HashMap<String, Animation>();

        anim.put("animSheepUp", new Animation(400, Assets.sheepUp));
        anim.put("animSheepDown", new Animation(400, Assets.sheepDown));
        anim.put("animSheepLeft", new Animation(400, Assets.sheepLeft));
        anim.put("animSheepRight", new Animation(400, Assets.sheepRight));
    }

    @Override
    public void tick() {
        for (Animation tempAnim : anim.values()) {
            tempAnim.tick();
        }

        randomlyMove();
        move();
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
        if (xMove < 0) {                            // Moving left.
            return anim.get("animSheepLeft").getCurrentFrame();
        } else if (xMove > 0) {                     // Moving right.
            return anim.get("animSheepRight").getCurrentFrame();
        } else if (yMove < 0) {                     // Moving up.
            return anim.get("animSheepUp").getCurrentFrame();
        } else if (yMove > 0) {                     // Moving down.
            return anim.get("animSheepDown").getCurrentFrame();
        } else {                                    // Non-moving.
            return Assets.sheepDown[0];
        }
    }

    @Override
    public void die() {
        active = false;
    }

} // **** end Sheep class ****