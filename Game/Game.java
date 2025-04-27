package Game;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
    public static int WIDTH = 1200;
    public static int HEIGHT = 900;
    public static String NAME = "Blackhole Clicker";
    public static ImageIcon BLACKHOLE_ICON = new ImageIcon("Resources/blackhole.png");
    public static Color BACKGROUND_COLOR = Color.BLACK;

    private boolean running;
    private long lastFrameTime;

    public Game() {
        super(NAME);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(BLACKHOLE_ICON.getImage());
        setResizable(false);
        setBackground(BACKGROUND_COLOR);
        setVisible(true);

        running = true;

        new Thread(this).start();
    }

    public void render(long dt) {
        
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("game logic");
        }
    }
}
