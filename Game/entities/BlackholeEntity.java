package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Entity;
import game.Game;

public class BlackholeEntity extends Entity {

    public static BlackholeEntity INSTANCE;
    private static BufferedImage IMAGE = Game.BLACKHOLE_IMAGE;

    public BlackholeEntity() {
        super(Game.WIDTH / 2 - IMAGE.getWidth() / 2, Game.HEIGHT / 2 - IMAGE.getHeight() / 2);
        if (INSTANCE != null) throw new RuntimeException("Blackhole already exists.");
        INSTANCE = this;
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(IMAGE, (int) getX(), (int) getY(), Game.INSTANCE);
    }

    @Override
    public void update(double dt) {
        if (System.nanoTime() % 10000 == 0) {
            System.out.println("Updating: " + 1 / dt);
        }
    }
    
}
