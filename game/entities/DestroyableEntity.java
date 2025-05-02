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
    }
    
    @Override
    public void render(Graphics2D g) {
        g.drawImage(image, (int) getX() - image.getWidth() / 2, (int) getY() - image.getHeight() / 2, Game.INSTANCE);
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        double distX = Math.abs(getX() - Game.WIDTH / 2);
        double distY = Math.abs(getY() - Game.HEIGHT / 2);

        if (Math.pow(distX, 2) + Math.pow(distY, 2) < Math.pow(128, 2)) {
            remove();
            Game.INSTANCE.addCash(cash);
            Game.INSTANCE.getEntities().add(
                new CashEntity(getX(), getY(), cash, 3)
            );
            Game.INSTANCE.getEntities().add(
                new DestroyedFramentEntity(getX(), getY(), image, 1)
            );

            return;
        }

        

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