package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Entity;
import game.Game;

public class BlackholeEntity extends Entity {

    public static BlackholeEntity INSTANCE;
    private final static BufferedImage IMAGE = Game.BLACKHOLE_IMAGE;

    public BlackholeEntity() {
        super(Game.WIDTH / 2 - IMAGE.getWidth() / 2, Game.HEIGHT / 2 - IMAGE.getHeight() / 2);
        if (INSTANCE != null) throw new RuntimeException("Blackhole already exists.");
        INSTANCE = this;
    }

    @Override
    public void render(Graphics2D g) {
        // AffineTransform oldTransform = g.getTransform();
        // g.rotate(getRotation(), Game.WIDTH / 2, Game.HEIGHT / 2);
        g.drawImage(IMAGE, (int) getX(), (int) getY(), Game.INSTANCE);
        // g.setTransform(oldTransform);
    }

    @Override
    public void update(double dt) {
        // addRotation(dt / 2);
    }
    
}
