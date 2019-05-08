package edu.pooh.tiles;

import edu.pooh.gfx.Assets;
import edu.pooh.main.IInvokable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SignPostTile extends SolidGenericTile
        implements IInvokable {

    private String message;
    private int x, y;

    public SignPostTile(BufferedImage texture, int x, int y, String message) {
        super(texture);

        this.x = x;
        this.y = y;
        this.message = message;
    } // **** end SignPostTile(BufferedImage, int, int, String) constructor ****

    @Override
    public void render(Graphics g, int x, int y) {
        super.render(g, x, y);

        g.drawImage(Assets.signPostTransparent, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
    }

    @Override
    public void execute() {
        // TODO: implement rendering/displaying the message into the game via dialog box.

        System.out.println("SignPostTile message: " + message);
    }

} //  **** end SignPostTile class ****