package edu.pooh.tiles;

import edu.pooh.main.IInvokable;

import java.awt.image.BufferedImage;

public class EggIncubatorTile extends SolidGenericTile
        implements IInvokable {

    private int x, y;
    private boolean incubating;
    private int daysIncubating;

    public EggIncubatorTile(int x, int y, BufferedImage texture) {
        super(texture);

        this.x = x;
        this.y = y;
        incubating = false;
        daysIncubating = 0;
    } // **** end EggIncubatorTile(int, int BufferedImage) constructor ****

    public void incrementDaysIncubating() {
        daysIncubating++;
    }

    @Override
    public void execute() {
        if (!incubating) {
            incubating = true;
        }
    }

    public boolean isIncubating() { return incubating; }
    public void setIncubating(boolean incubating) { this.incubating = incubating; }

    public int getDaysIncubating() { return daysIncubating; }
    public void setDaysIncubating(int daysIncubating) { this.daysIncubating = daysIncubating; }

    public int getX() { return x; }
    public int getY() { return y; }

} // **** end EggIncubatorTile class ****