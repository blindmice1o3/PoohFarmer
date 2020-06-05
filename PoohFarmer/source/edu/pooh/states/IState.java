package edu.pooh.states;

import edu.pooh.main.Handler;

import java.awt.*;

public interface IState {

    void enter(Object[] args);

    void exit();

    void tick();

    void render(Graphics g);

    void setHandler(Handler handler);

} // **** end IState abstract class ****