package edu.pooh.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean up, down, left, right;
    public boolean aUp, aDown, aLeft, aRight;

    public KeyManager() {
        keys = new boolean[256];
    }

    public void tick() {
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];

        aUp = keys[KeyEvent.VK_UP];
        aDown = keys[KeyEvent.VK_DOWN];
        aLeft = keys[KeyEvent.VK_LEFT];
        aRight = keys[KeyEvent.VK_RIGHT];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) {
            return;
        }
        keys[e.getKeyCode()] = true;
        System.out.println("pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) {
            return;
        }
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

} // **** end KeyManager class ****