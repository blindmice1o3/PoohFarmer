package edu.pooh.main;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.GameCamera;
import edu.pooh.input.KeyManager;
import edu.pooh.input.MouseManager;
import edu.pooh.sfx.SoundManager;
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
    private IState gameState, homeState, chickenCoopState, cowBarnState, toolShedState,
            crossroadState, mountainState, menuState, travelingFenceState;

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
        SoundManager.init();


        ////////////////////////////////////////////////////////
        gameState = new GameState(handler);
        homeState = new HomeState(handler);
        chickenCoopState = new ChickenCoopState(handler);
        cowBarnState = new CowBarnState(handler);
        toolShedState = new ToolShedState(handler);
        crossroadState = new CrossroadState(handler);
        mountainState = new MountainState(handler);
        menuState = new MenuState(handler);
        travelingFenceState = new TravelingFenceState(handler);
        StateManager.setCurrentState( getGameState() );
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

        if (StateManager.getCurrentState() != null) {
            StateManager.getCurrentState().tick();
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

        if (StateManager.getCurrentState() != null) {
            StateManager.getCurrentState().render(g);
        }

        ////////////////////////////////    //End drawing!
        bs.show();
        g.dispose();
    }

    // GETTERS & SETTERS

    public IState getGameState() {
        return gameState;
    }

    public void setGameState(IState gameState) {
        this.gameState = gameState;
    }

    public IState getHomeState() {
        return homeState;
    }

    public void setHomeState(IState homeState) {
        this.homeState = homeState;
    }

    public IState getChickenCoopState() { return chickenCoopState; }

    public void setChickenCoopState(IState chickenCoopState) { this.chickenCoopState = chickenCoopState; }

    public IState getCowBarnState() { return cowBarnState; }

    public void setCowBarnState(IState cowBarnState) { this.cowBarnState = cowBarnState; }

    public IState getToolShedState() { return toolShedState; }

    public void setToolShedState(IState toolShedState) { this.toolShedState = toolShedState; }

    public IState getCrossroadState() { return crossroadState; }

    public void setCrossroadState(IState crossroadState) { this.crossroadState = crossroadState; }

    public IState getMountainState() { return mountainState; }

    public void setMountainState(IState mountainState) { this.mountainState = mountainState; }

    public IState getMenuState() {
        return menuState;
    }

    public void setMenuState(IState menuState) {
        this.menuState = menuState;
    }

    public IState getTravelingFenceState() {
        return travelingFenceState;
    }

    public void setTravelingFenceState(IState travelingFenceState) {
        this.travelingFenceState = travelingFenceState;
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