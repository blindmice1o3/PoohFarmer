package edu.pooh.main;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.GameCamera;
import edu.pooh.input.KeyManager;
import edu.pooh.input.MouseManager;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.serialize_deserialize.SaverAndLoader;
import edu.pooh.sfx.SoundManager;
import edu.pooh.states.*;
import edu.pooh.time.TimeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serializable;

public class Game extends Canvas {
    // TODO: Serialiable (save/load feature).

    // CONSTANTS
    public static final int WIDTH_OF_FRAME = 800;
    public static final int HEIGHT_OF_FRAME = 600;

    // DISPLAY
    private JFrame frame;

    // THREAD
    private Thread gameThread;
    private volatile boolean running = false; // GAME LOOP'S conditional statement (while loop)



    // HANDLER
    private Handler handler;

    // CAMERA
    private GameCamera gameCamera;

    // INPUT
    private KeyManager keyManager;
    private MouseManager mouseManager;

    // GAME STATES
    private StateManager stateManager;

    // TIME
    private TimeManager timeManager;

    // RESOURCE (currency, fodder, wood, chicken, cow)
    private ResourceManager resourceManager;

    // SAVER AND LOADER
    SaverAndLoader saverAndLoader;

    public Game() {
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        addKeyListener(keyManager);
        addMouseListener(mouseManager);
        addMouseMotionListener(mouseManager);

        frame = new JFrame("Pooh Farmer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));

        JPanel panel = (JPanel)frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        panel.setLayout(null);
        panel.setDoubleBuffered(false);

        setBounds(0, 0, WIDTH_OF_FRAME, HEIGHT_OF_FRAME);
        panel.add(this);

        ///////////////////////
        setIgnoreRepaint(true);
        ///////////////////////

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        requestFocus();
    } // **** end edu.pooh.main.Game() constructor ****

    public void gameInit() {
        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0, 0);

        Assets.init();
        SoundManager.init();

        saverAndLoader = new SaverAndLoader(handler);
        timeManager = new TimeManager(handler);
        resourceManager = new ResourceManager(handler);
        stateManager = new StateManager(handler);
    }

    public synchronized void gameStart() {
        if (running) {
            return;
        }

        running = true;

        gameThread = new Thread() {
            @Override
            public void run() {
                gameInit();

                gameLoop(); // @@@@ GAME LOOP @@@@

                //gameStop();
                //Player class's tick() method is now the one calling Game class's gameStop() method.
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
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                //TimeManager's tick().
                timeManager.incrementElapsedRealSeconds();
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
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

        if (stateManager.getCurrentState() != null) {
            stateManager.getCurrentState().tick();
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            ///////
            return;
            ///////
        }

        Graphics2D g2d = null;
        try {
            g2d = (Graphics2D)bs.getDrawGraphics();
            // ************ Draw here! ************

            //g2d.clearRect(0, 0, this.getWidth(), this.getHeight());   //Clear screen

            if (stateManager.getCurrentState() != null) {
                stateManager.getCurrentState().render(g2d);                 //Render currentState
            }

            // ************ End drawing! ************
        } finally {
            g2d.dispose();
        }
        bs.show();
    }

    // GETTERS & SETTERS

    public SaverAndLoader getSaverAndLoader() { return  saverAndLoader; }

    public TimeManager getTimeManager() { return timeManager; }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setTimeManager(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public ResourceManager getResourceManager() { return resourceManager; }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
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