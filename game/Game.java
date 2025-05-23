package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JFrame;

import game.entity.Entity;
import game.entity.entities.BlackholeEntity;
import game.entity.entities.CurrentCashEntity;
import game.entity.entities.OpenShopEntity;
import game.entity.entities.ShopEntity;
import game.listeners.ClickHandler;
import game.listeners.ExitHandler;
import game.listeners.ResizeHandler;
import game.upgrade.UpgradeManager;

public class Game extends JFrame implements Runnable {

    public static Game INSTANCE;
    public static volatile int WIDTH = 1200;
    public static volatile int HEIGHT = 900;
    public final static String NAME = "Blackhole Clicker";
    public final static Color BACKGROUND_COLOR = Color.BLACK;

    public volatile boolean running; // volatile to let other threads use this variable. When this thread stops, the generator thread will also stop.
    private Thread gameThread;
    private Thread generatorThread;
    private long lastFrameTime; // save time it took for the last frame to generate for deltaTime calculations
    private double cash; // current cash in game
    private ArrayList<Entity> entities = new ArrayList<>(); // list that contains all the entities needed to be rendered.
    private ArrayList<Entity> guiEntities = new ArrayList<>(); // these entities are rendered on top of the other list.
    private ConcurrentLinkedQueue<Entity> entityQueue = new ConcurrentLinkedQueue<>();

    public Game() {
        super(NAME);

        if (INSTANCE != null) {
            throw new RuntimeException("Game already exists.");
        }
        INSTANCE = this;
        
        setSize(WIDTH, HEIGHT);
        // setResizable(false);
        setBackground(BACKGROUND_COLOR);
        setVisible(true);

        UpgradeManager.load(); // load all of the upgrades before the game is started.

        try {
            ResourceManager.load(); // load all of the resources before the game is started.
        } catch (Exception e) {
            throw new RuntimeException("Could not load resource files", e);
        }

        setIconImage(ResourceManager.RESOURCE_MAP.get("blackhole.png").getScaledInstance(256, 256, Image.SCALE_SMOOTH)); // set icon of game

        running = true;

        // add starting entities
        guiEntities.add(new CurrentCashEntity());
        guiEntities.add(new ShopEntity());
        guiEntities.add(new OpenShopEntity());
        entities.add(new BlackholeEntity());

        // add event listeners
        addMouseListener(new ClickHandler());
        addWindowListener(new ExitHandler());
        addComponentListener(new ResizeHandler());

        createBufferStrategy(2); // creates a buffer that is rendered on the screen
        gameThread = new Thread(this);
        gameThread.start(); // start the game
        generatorThread = new Thread(new Generator());
        generatorThread.start(); // start the generators

        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setAlwaysOnTop(false); //weird trick to focus frame
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy(); // get the buffer that can be drawn on

        if (bs == null || bs.contentsLost()) {
            createBufferStrategy(2);
            bs = getBufferStrategy();
            if (bs == null) {
                System.err.println("Unable to create new buffer strategy.");
                return;
            }
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics(); // get the graphics object that will let me draw

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT); // clear the screen for new frame
        g.setFont(ResourceManager.MONOCRAFT_FONT); // set the font

        for (int i = 0; i < entities.size(); i++) { // loop through each entity
            Entity entity = entities.get(i);
            if (!entity.isAlive()) continue; // only render entities that are alive
            g.setColor(Color.WHITE);
            entity.render(g); // render entity
        }

        for (Entity entity : guiEntities) { // loop through guiEntities
            // gui entities can't be killed so there is no reason to check here
            g.setColor(Color.WHITE);
            entity.render(g); // render entity
        }
        
        g.dispose();
        bs.show(); // put buffer on the screen
    }

    @Override
    public void run() {

        lastFrameTime = System.nanoTime(); // set time to calculate deltaTime between frames
        while (running) {
            long currentTime = System.nanoTime();
            long diffTime = currentTime - lastFrameTime; // calculate time difference in nanoseconds
            double deltaTime = diffTime / 1_000_000_000.0; // convert nanoseconds to seconds. Math.pow(10, 9);
            lastFrameTime = currentTime; // update for next frame

            for (int i = 0; i < entities.size(); i++) { // loop through all entities
                entities.get(i).update(deltaTime); // update the entity with the deltaTime
            }

            for (Entity entity : guiEntities) { // loop through all guiEntities
                entity.update(deltaTime); // update the entity with deltaTime
            }

            render(); // render the frame

            // clean up dead entities
            removeDeadEntities();

            // add queued entities
            addQueuedEntities();

            try {
                Thread.sleep(1); // let the cpu have a break. peak fps = 1000
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void addQueuedEntities() {
        while (entityQueue.peek() != null) {
            entities.add(entityQueue.poll());
        }
    }

    public void stop() {
        if (!running) return;
        try {
            running = false;
            gameThread.interrupt();
            gameThread.join(); // stop game
            generatorThread.interrupt();
            generatorThread.join(); // stop generator
            System.exit(0); // exit
        } catch (InterruptedException e) {
            throw new RuntimeException("Unable to stop the game thread", e);
        }
    }

    private void removeDeadEntities() {

        ArrayList<Entity> noDeadEntities = new ArrayList<>(); // make a list

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.isAlive()) noDeadEntities.add(entity); // add entities that are alive
        }

        entities = noDeadEntities; // replace list. This is faster than shifting the list over when removing elements. O(N) vs O(N^2)
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entityQueue.add(entity);
    }

    public ArrayList<Entity> getGuiEntities() {
        return guiEntities;
    }

    public double getCash() {
        return cash;
    }

    public void addCash(double cash) {
        this.cash += cash;
    }
}