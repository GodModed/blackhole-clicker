package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import game.entities.BlackholeEntity;
import game.entities.CurrentCashEntity;
import game.entities.DestroyableEntity;
import game.listeners.ClickHandler;

public class Game extends JFrame implements Runnable {

    public static Game INSTANCE;
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 900;
    public final static String NAME = "Blackhole Clicker";
    public final static Color BACKGROUND_COLOR = Color.BLACK;

    private boolean running;
    private long lastFrameTime;
    // private double fpsAccumulator;
    private double cash;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Entity> guiEntities = new ArrayList<>();

    public Game() {
        super(NAME);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(BACKGROUND_COLOR);
        setVisible(true);

        try {
            ResourceManager.load();
        } catch (Exception e) {
            throw new RuntimeException("Could not load resource files", e);
        }

        setIconImage(ResourceManager.BLACKHOLE_IMAGE.getScaledInstance(256, 256, Image.SCALE_SMOOTH));

        running = true;

        guiEntities.add(new CurrentCashEntity());
        entities.add(new BlackholeEntity());
        // entities.add(new CashEntity(WIDTH / 2, HEIGHT / 2, Math.random() * 10000, 3));
        // entities.add(new DestroyableEntity(50.0, 50.0, BLACKHOLE_IMAGE, 2.0));
        for (int i = 0; i < 10; i++) {
            entities.add(new DestroyableEntity(Math.random() * WIDTH, Math.random() * HEIGHT, ResourceManager.WOOD_CHAIR_IMAGE, Math.random() * 1000));
        }

        addMouseListener(new ClickHandler());

        createBufferStrategy(2);
        new Thread(this).start();

        if (INSTANCE != null) {
            throw new RuntimeException("Game already exists.");
        }

        INSTANCE = this;
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(BACKGROUND_COLOR);
        g.clearRect(0, 0, WIDTH, HEIGHT);
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
        }
    }

    public void removeDeadEntities() {
        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity entitiy = entities.get(i);
            if (!entitiy.isAlive()) entities.remove(i);
        }
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
