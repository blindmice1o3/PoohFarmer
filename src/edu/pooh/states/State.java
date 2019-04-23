package edu.pooh.states;

import java.awt.*;

public interface State {

    public abstract void enter(Object[] args);

    public abstract void exit();

    public abstract void tick();

    public abstract void render(Graphics g);

} // **** end State abstract class ****