package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;

import game.Entity;
import game.Game;
import game.ResourceManager;
import game.UpgradeManager;

public class BlackholeEntity extends Entity {

    public static BlackholeEntity INSTANCE;
    private final static BufferedImage IMAGE = ResourceManager.BLACKHOLE_IMAGE;

    public BlackholeEntity() {
        super(Game.WIDTH / 2, Game.HEIGHT / 2);
        if (INSTANCE != null) throw new RuntimeException("Blackhole already exists.");
        INSTANCE = this;
    }

    @Override
    public void render(Graphics2D g) {
        // AffineTransform oldTransform = g.getTransform();
        // g.rotate(getRotation(), Game.WIDTH / 2, Game.HEIGHT / 2);
        // TODO: make click animation
        g.drawImage(IMAGE, (int) getX() - IMAGE.getWidth() / 2, (int) getY() - IMAGE.getHeight() / 2, Game.INSTANCE);
        // g.setTransform(oldTransform);
    }

    @Override
    public void update(double dt) {
        // addRotation(dt / 2);
    }

    public void click(MouseEvent e) {

        double distX = Math.abs(e.getX() - Game.WIDTH / 2);
        double distY = Math.abs(e.getY() - Game.HEIGHT / 2);

        if (Math.pow(distX, 2) + Math.pow(distY, 2) >= Math.pow(128, 2)) return;

        double cash = UpgradeManager.CLICK_MULTIPLIER;
        Game.INSTANCE.getEntities().add(
            new CashEntity((double) e.getX(), (double) e.getY(), cash, 3)
        );
        Game.INSTANCE.addCash(UpgradeManager.CLICK_MULTIPLIER);
    }
    
}
