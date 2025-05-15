package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import game.entities.BlackholeEntity;
import game.entities.CurrentCashEntity;
import game.entities.OpenShopEntity;
import game.entities.ShopEntity;
import game.listeners.ClickHandler;
import game.listeners.ExitHandler;
import game.listeners.ResizeListener;

public class Game extends JFrame implements Runnable {

    public static Game INSTANCE;
    public static volatile int WIDTH = 1200;
    public static volatile int HEIGHT = 900;
    public final static String NAME = "Blackhole Clicker";
    public final static Color BACKGROUND_COLOR = Color.BLACK;

    public volatile boolean running;
    private Thread gameThread;
    private Thread generatorThread;
    private long lastFrameTime;
    // private double fpsAccumulator;
    private double cash;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Entity> guiEntities = new ArrayList<>();

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

        UpgradeManager.load();

        try {
            ResourceManager.load();
        } catch (Exception e) {
            throw new RuntimeException("Could not load resource files", e);
        }

        setIconImage(ResourceManager.BLACKHOLE_IMAGE.getScaledInstance(256, 256, Image.SCALE_SMOOTH));

        running = true;

        guiEntities.add(new CurrentCashEntity());
        guiEntities.add(new ShopEntity());
        guiEntities.add(new OpenShopEntity());
        entities.add(new BlackholeEntity());

        addMouseListener(new ClickHandler());
        addWindowListener(new ExitHandler());
        addComponentListener(new ResizeListener());

        createBufferStrategy(2);
        gameThread = new Thread(this);
        gameThread.start();
        generatorThread = new Thread(new Generator());
        generatorThread.start();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setFont(ResourceManager.MONOCRAFT_FONT);

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (!entity.isAlive()) continue;
            g.setColor(Color.WHITE);
            entity.render(g);
        }

        for (Entity entity : guiEntities) {
            g.setColor(Color.WHITE);
            entity.render(g);
        }
        
        g.dispose();
        bs.show();
    }

    @Override
    public void run() {

        lastFrameTime = System.nanoTime();
        while (running) {
            long currentTime = System.nanoTime();
            long diffTime = currentTime - lastFrameTime;
            double deltaTime = diffTime / 1_000_000_000.0; // convert nanoseconds to seconds. Math.pow(10, 9);
            lastFrameTime = currentTime;

            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).update(deltaTime);
            }

            for (Entity entity : guiEntities) {
                entity.update(deltaTime);
            }

            render();

            // clean up dead entities
            removeDeadEntities();

            // fpsAccumulator += deltaTime;
            // if (fpsAccumulator >= 5) {
            //     fpsAccumulator = 0;
            //     System.out.println("FPS: " + 1 / deltaTime);
            // }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {}
        }
    }

    public void stop() {
        if (!running) return;
        try {
            running = false;
            gameThread.join();
            generatorThread.interrupt();
            generatorThread.join();
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException("Unable to stop the game thread", e);
        }
    }

    private void removeDeadEntities() {

        ArrayList<Entity> noDeadEntities = new ArrayList<>();

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.isAlive()) noDeadEntities.add(entity);
        }

        entities = noDeadEntities;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public double getCash() {
        return cash;
    }

    public void addCash(double cash) {
        this.cash += cash;
    }
}