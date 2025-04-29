package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import game.entities.BlackholeEntity;
import game.entities.CashEntity;
import game.entities.DestroyableEntity;

public class Game extends JFrame implements Runnable {

    public static Game INSTANCE;
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 900;
    public final static String NAME = "Blackhole Clicker";
    public final static BufferedImage BLACKHOLE_IMAGE;
    public final static Color BACKGROUND_COLOR = Color.BLACK;

    static {
        try {
            BLACKHOLE_IMAGE = ImageIO.read(new File("resources/blackhole.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean running;
    private long lastFrameTime;
    private double fpsAccumulator;
    private ArrayList<Entity> entities = new ArrayList<>();

    public Game() {
        super(NAME);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(BLACKHOLE_IMAGE.getScaledInstance(256, 256, Image.SCALE_SMOOTH));
        setResizable(false);
        setBackground(BACKGROUND_COLOR);
        setVisible(true);

        running = true;

        entities.add(new BlackholeEntity());
        // entities.add(new CashEntity(WIDTH / 2, HEIGHT / 2, Math.random() * 10000, 3));
        entities.add(new DestroyableEntity(50.0, 50.0, BLACKHOLE_IMAGE, 2.0));

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

        for (Entity entity : entities) {
            if (!entity.isAlive()) continue;
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

            for (IUpdatable updatable : entities) {
                updatable.update(deltaTime);
            }

            render();
            lastFrameTime = currentTime;

            // clean up dead entities
            removeDeadEntities();

            fpsAccumulator += deltaTime;
            if (fpsAccumulator >= 5) {
                fpsAccumulator = 0;
                System.out.println("FPS: " + 1 / deltaTime);
            }
        }
    }

    public void removeDeadEntities() {
        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity entitiy = entities.get(i);
            if (!entitiy.isAlive()) entities.remove(entitiy);
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
