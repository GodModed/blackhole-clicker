package game.entities;

import game.Game;
import game.Entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class DestroyableEntity extends Entity {

    private final double cash;
    private final BufferedImage image;
    private static int WIDTH = 50;
    private static int HEIGHT = 50;

    public DestroyableEntity(double x, double y, BufferedImage image, double cash) {
        super(x, y);
        this.image = image;
        this.cash = cash;
        setDeltaX(5);
    }
    
    @Override
    public void render(Graphics2D g) {
        g.drawImage(image, (int) getX() - image.getWidth() / 2, (int) getY() - image.getHeight() / 2, Game.INSTANCE);
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        if (Math.pow(getX() - Game.WIDTH / 2, 2) + Math.pow(getY() - Game.HEIGHT / 2, 2) < Math.pow(128, 2)) {
            remove();
            Game.INSTANCE.getEntities().add(
                new CashEntity(getX(), getY(), cash, 3)
            );
            return;
        }

        
        double distX = Math.abs(getX() - Game.WIDTH / 2);
        double distY = Math.abs(getY() - Game.HEIGHT / 2);

        double angle = Math.atan(distY / distX);

        double ddx = 25 * Math.cos(angle) * dt;
        if (getX() > Game.WIDTH / 2) {
            ddx *= -1;
        }

        double ddy = 25 * Math.sin(angle) * dt;
        if (getY() > Game.HEIGHT / 2) {
            ddy *= -1;
        }

        addDeltaX(ddx);
        addDeltaY(ddy);
    }

}