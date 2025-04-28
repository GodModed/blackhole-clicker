package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import game.entities.BlackholeEntity;

public class Game extends JFrame implements Runnable {

    public static Game INSTANCE;
    public static int WIDTH = 1200;
    public static int HEIGHT = 900;
    public static String NAME = "Blackhole Clicker";
    public static BufferedImage BLACKHOLE_IMAGE;
    public static Color BACKGROUND_COLOR = Color.BLACK;

    static {
        try {
            BLACKHOLE_IMAGE = ImageIO.read(new File("resources/blackhole.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean running;
    private long lastFrameTime;

    public Game() {
        super(NAME);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(BLACKHOLE_IMAGE.getScaledInstance(256, 256, Image.SCALE_SMOOTH));
        setResizable(false);
        setBackground(BACKGROUND_COLOR);
        setVisible(true);

        running = true;

        new BlackholeEntity();

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

        for (IRenderable renderable : Entity.getEntities()) {
            renderable.render(g);
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
            double deltaTime = diffTime / Math.pow(10, 9);

            for (IUpdatable updatable : Entity.getEntities()) {
                updatable.update(deltaTime);
            }

            render();
            lastFrameTime = currentTime;
        }
    }
}
