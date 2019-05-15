package edu.pooh.tiles;

import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;

import java.awt.image.BufferedImage;

public class WoodStashTile extends SolidGenericTile
        implements IInvokable {

    public WoodStashTile(Handler handler, int x, int y, BufferedImage texture) {
        super(texture);
    } // **** end WoodStashTile() constructor ****

    @Override
    public void execute() {
        System.out.println("player clicked WoodStashTile!!!");
    }

} // **** end WoodStashTile class ****