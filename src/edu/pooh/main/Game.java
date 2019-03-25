package edu.pooh.main;

import edu.pooh.gfx.Assets;
import edu.pooh.input.KeyManager;
import edu.pooh.states.GameState;
import edu.pooh.states.MenuState;
import edu.pooh.states.State;
import edu.pooh.states.StateManager;

import javax.swing.*;
import java.awt.*;

public class Game {

    // CONSTANTS
    public static final int WIDTH_OF_FRAME = 800;
    public static final int HEIGHT_OF_FRAME = 600;

    // DISPLAY
    private JFrame frame;
    private JPanel panel;

    // THREAD
    Thread gameThread;
    // GAME LOOP'S conditional statement (while loop)
    private volatile boolean running = false;

    // STATES
    private State gameState;
    private State menuState;

    // INPUT
    private KeyManager keyManager;

    public Game() {
        Assets.init();

        keyManager = new KeyManager();

        gameState = new GameState(this);
        menuState = new MenuState(this);
        StateManager.setCurrentState(gameState);
    } // **** end edu.pooh.main.Game() constructor

    public void gameInit() {
        frame = new JFrame("Pooh Farmer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(keyManager); // @@@@

        panel = new GameState(this);
        //panel.addKeyListener(keyManager); // @@@@
        frame.setContentPane(panel);

        frame.setVisible(true);
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // @@@@@
    }

    public synchronized void gameStart() {
        if (running) {
            return;
        }

        running = true;

        gameThread = new Thread() {
            @Override
            public void run() {
                gameInit(); // Is just called once.

                gameLoop(); // @@@@ GAME LOOP @@@@

                gameStop();
            }
        };

        gameThread.start();
    }

    public synchronized void gameStop() {
        if (!running) {
            return;
        }

        running = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int renderCounter = 0; // @@@@
    private void gameLoop() {
        /*
            initializing bunch of variables to achieve CONSISTENT fps,
            no matter if running game on fast/slow computer
         */
        // frames (/ticks/updates) per second.
        int fps = 60;   // tick() and render() will be called 60 times every single second.
        // 1 second / 60 fps ==> The maximum time we have to run tick() and render() to achieve 60 fps.
        double timeAllottedPerTick = 1000000000 / fps;  // 1 billion nanoseconds == 1 second.
        double delta = 0;

        long currentTime;
        long previousTime = System.nanoTime();  // The current time of our computer, but in nanoseconds.
        long elapsedTime;

        // We need a visual fps counter to show to the console screen.
        long tickTimer = 0;
        int tickCounter = 0;

        /*
           **** start of GAME-LOOP ****
         */
        while (running) {
            currentTime = System.nanoTime();

            elapsedTime = currentTime - previousTime;
            tickTimer += elapsedTime;
            delta += (elapsedTime / timeAllottedPerTick);

            previousTime = currentTime;

            // Changed if () to while ().
            // "series of fixed time steps" -gameprogrammingpatterns.com/game-loop.html
            while (delta >= 1) {
                //surround with if(!gamePaused) later on.
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                tick();
                tickCounter++;

                render();
                renderCounter++;

                panel.repaint();
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                delta--;
            }


            // Check if our timer is greater than or equal to 1 second.
            // Visual representation to check how many times we're calling tick() and render() each second.
            if (tickTimer >= 1000000000) {
                System.out.println("Tick calls: " + tickCounter + ".  |  Render calls: " + renderCounter + ".");
                tickCounter = 0;  // Reset tickCounter back to 0.
                renderCounter = 0;
                tickTimer = 0;  // Reset tickTimer back to 0.
            }

            //Thread.sleep(timeLeft) later on.

        } // *** end of GAME-LOOP ***
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    private void tick() {
        keyManager.tick();

        if (StateManager.getCurrentState() != null) {
            StateManager.getCurrentState().tick();
        }
    }

    private void render() {
        if(StateManager.getCurrentState() != null) {
            StateManager.getCurrentState().render();
        }
    }

    // GETTERS & SETTERS

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public static void main(String[] args) {
        Game game = new Game();

        /*
        // Use the event dispatch thread to build the UI for thread-safety.
        SwingUtilities.invokeLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           game.createFrame();
                                       }
                                   });
        */

        game.gameStart();
    }
} // **** end edu.pooh.main.Game class ****