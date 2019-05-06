package edu.pooh.tiles;

import edu.pooh.main.IInvokable;
import edu.pooh.time.TimeManager;

import java.awt.image.BufferedImage;

public class BedTile extends SolidGenericTile
        implements IInvokable {

    public BedTile(BufferedImage texture) {
        super(texture);
    } // **** end BedTile(BufferedImage) constructor ****

    @Override
    public void execute() {
        TimeManager.setNewDayTrue();

        System.out.println("BedTile execute() method called");
    }

} // **** end BedTile class ****