package edu.pooh.main;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.GameCamera;
import edu.pooh.input.KeyManager;
import edu.pooh.input.MouseManager;
import edu.pooh.states.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game {

    // CONSTANTS
    public static final int WIDTH_OF_FRAME = 800;
    public static final int HEIGHT_OF_FRAME = 600;

    // DISPLAY
    private JFrame frame;
    private Canvas canvas;

    // GRAPHICS CONTEXT
    private BufferStrategy bs;
    private Graphics g;

    // THREAD
    private Thread gameThread;
    private volatile boolean running = false; // GAME LOOP'S conditional statement (while loop)


    // STATES
    private IState gameIState;
    private IState homeIState;
    private IState menuIState;
    private IState travelingFenceIState;

    // INPUT
    private KeyManager keyManager;
    private MouseManager mouseManager;

    // CAMERA
    private GameCamera gameCamera;

    // HANDLER
    private Handler handler;

    public Game() {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();

        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0, 0);

        Assets.init();

        ////////////////////////////////////////////////////////
        gameIState = new GameIState(handler);
        menuIState = new MenuIState(handler);
        travelingFenceIState = new TravelingFenceIState(handler);
        homeIState = new HomeIState(handler);
        StateManager.setCurrentIState( getGameIState() );
        ////////////////////////////////////////////////////////
    } // **** end edu.pooh.main.Game() constructor ****

    public void gameInit() {
        frame = new JFrame("Pooh Farmer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        canvas.setMaximumSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        canvas.setMinimumSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        canvas.setFocusable(false);

        frame.addKeyListener(keyManager);
        frame.addMouseListener(mouseManager);
        frame.addMouseMotionListener(mouseManager);
        canvas.addKeyListener(keyManager);
        canvas.addMouseListener(mouseManager);
        canvas.addMouseMotionListener(mouseManager);

        frame.add(canvas);
        frame.pack();

        frame.setVisible(true);
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
        int renderCounter = 0;

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

            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            render();
            renderCounter++;
            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            // Check if our timer is greater than or equal to 1 second.
            // Visual representation to check how many times we're calling tick() and render() each second.
            if (tickTimer >= 1000000000) {
                System.out.println("Tick: " + tickCounter + ". | Render: " + renderCounter + ".");
                tickCounter = 0;  // Reset tickCounter back to 0.
                renderCounter = 0;
                tickTimer = 0;  // Reset tickTimer back to 0.
            }

            //Thread.sleep(timeLeft) later on.

        } // *** end of GAME-LOOP ***
    }

    private void tick() {
        keyManager.tick();

        if (StateManager.getCurrentIState() != null) {
            StateManager.getCurrentIState().tick();
        }
    }

    private void render() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        ////////////////////////////////    //Clear Screen
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ////////////////////////////////    //Draw here!

        if (StateManager.getCurrentIState() != null) {
            StateManager.getCurrentIState().render(g);
        }

        ////////////////////////////////    //End drawing!
        bs.show();
        g.dispose();
    }

    // GETTERS & SETTERS

    public IState getGameIState() {
        return gameIState;
    }

    public void setGameIState(IState gameIState) {
        this.gameIState = gameIState;
    }

    public IState getMenuIState() {
        return menuIState;
    }

    public void setMenuIState(IState menuIState) {
        this.menuIState = menuIState;
    }

    public IState getTravelingFenceIState() {
        return travelingFenceIState;
    }

    public void setTravelingFenceIState(IState travelingFenceIState) {
        this.travelingFenceIState = travelingFenceIState;
    }

    public IState getHomeIState() {
        return homeIState;
    }

    public void setHomeIState(IState homeIState) {
        this.homeIState = homeIState;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() { return mouseManager; }

    public GameCamera getGameCamera() { return gameCamera; }

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