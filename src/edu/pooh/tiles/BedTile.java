package edu.pooh.tiles;

import edu.pooh.main.Handler;
import edu.pooh.main.IInvokable;
import edu.pooh.time.TimeManager;

import java.awt.image.BufferedImage;

public class BedTile extends SolidGenericTile
        implements IInvokable {

    private transient Handler handler;

    public BedTile(Handler handler, BufferedImage texture) {
        super(texture);

        this.handler = handler;
    } // **** end BedTile(Handler, BufferedImage) constructor ****

    @Override
    public void execute() {
        handler.getTimeManager().executeSleep();

        System.out.println("BedTile.execute() called.");
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end BedTile class ****