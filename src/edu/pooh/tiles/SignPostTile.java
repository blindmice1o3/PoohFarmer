package edu.pooh.tiles;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SignPostTile extends SolidGenericTile
        implements IInvokable {

    public enum SignPostType { SHIPPING_BIN, RESOURCE_FODDER, RESOURCE_WOOD, HORSE_STABLE, MOUNTAIN_TODO,
        CHICKEN_COOP_INCUBATOR, COW_BARN_INCUBATOR; }

    private Handler handler;
    private int x, y;
    private boolean executing;

    private SignPostType signPostType;

    public SignPostTile(Handler handler, BufferedImage texture, int x, int y, SignPostType signPostType) {
        super(texture);

        this.handler = handler;
        this.x = x;
        this.y = y;
        executing = false;

        this.signPostType = signPostType;
    } // **** end SignPostTile(Handler, BufferedImage, int, int, String) constructor ****

    @Override
    public void render(Graphics g, int x, int y) {
        super.render(g, x, y);

        g.drawImage(Assets.signPostTransparent, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);

        if (executing) {
            g.drawImage(Assets.dateDisplayerBackground, 64, 48, 668, 500, null);

            Text.drawString(g, determineSignPostMessage(), 100, 100, false,
                    Color.BLUE, Assets.font28);
        }
    }

    private String determineSignPostMessage() {
        switch (signPostType) {
            case SHIPPING_BIN:
                return "ISellable objects in the ShippingBin will be traded for currencyUnit at 5pm everyday.";
            case RESOURCE_FODDER:
                return "ResourceManager's fodderCount: " + handler.getResourceManager().getFodderCount();
            case RESOURCE_WOOD:
                return "ResourceManager's woodCount: " + handler.getResourceManager().getWoodCount();
            case HORSE_STABLE:
                return "HorseStable tile is nothing more than a textured collision box with its isSolid() set to true.";
            case CHICKEN_COOP_INCUBATOR:
                return "ChickenIncubator tile: 3 days for Egg to hatch into Chicken whose ChickenState is CHICK.";
            case COW_BARN_INCUBATOR:
                return "CowIncubator tile is in progress.";
            default:
                return "Could not determine SignPostTile's message.";
        }
    }

    @Override
    public void execute() {
        // TODO: implement rendering/displaying the signPostName into the game via dialog box.
        executing = true;

        System.out.println("SignPostTile says: Silo contains | " + handler.getResourceManager().getFodderCount() + " | fodder(s).");
    }

    public void setExecuting(boolean executing) {
        this.executing = executing;
    }

} //  **** end SignPostTile class ****