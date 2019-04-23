package edu.pooh.states;

import java.awt.*;

public interface IState {

    public abstract void enter(Object[] args);

    public abstract void exit();

    public abstract void tick();

    public abstract void render(Graphics g);

} // **** end IState abstract class ****