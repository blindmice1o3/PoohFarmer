package edu.pooh.main;

import edu.pooh.gfx.Assets;

import javax.swing.*;
import java.awt.*;

public class Game {

    private JFrame frame;
    private JPanel panel;
    private volatile boolean running = false;

    private Graphics g;

    public Game() {
        guiInit();

        Assets.init();

        g = panel.getGraphics();
    } // **** end edu.pooh.main.Game() constructor

    public void guiInit() {
        frame = new JFrame("Pooh Farmer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new GamePanel(this);
        frame.setContentPane(panel);

        frame.setVisible(true);
    }

    public void gameInit() {

    }

    public void gameStart() {
        Thread gameThread = new Thread() {
            @Override
            public void run() {
                gameLoop();
            }
        };
        running = true;
        gameThread.start();
    }


    int renderCounter = 0;
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
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                delta--;
            }

            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            render(g);
            renderCounter++;
            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

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

    private void tick() {

    }

    private void render(Graphics g) {
        panel.repaint();
        g.drawString("renderCounter: " + renderCounter, 50, 100);
        g.drawImage(Assets.plantFlowering2, 400, 400, null);
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
                                           game.guiInit();
                                       }
                                   });
        */

        game.gameStart();
    }
} // **** end edu.pooh.main.Game class ****