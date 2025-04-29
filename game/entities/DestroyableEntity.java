package game.entities;

import game.Game;
import game.Entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class DestroyableEntity extends Entity {

    private final double cash;
    private final BufferedImage image;

    public DestroyableEntity(double x, double y, BufferedImage image, double cash) {
        super(x, y);
        this.image = image;
        this.cash = cash;
        setDeltaX(50);
    }
    
    @Override
    public void render(Graphics2D g) {
        g.drawRect((int) getX(), (int) getY(), 50, 50);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        double xDiff = getX() - Game.WIDTH / 2;
        double yDiff = getY() - Game.HEIGHT / 2;

        addDeltaX(-xDiff * dt / 100);
        addDeltaY(-yDiff * dt / 100);
    }

}