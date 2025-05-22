package game.entity.entities;

import game.Game;
import game.ResourceManager;
import game.entity.Entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
        AffineTransform oldTransform = g.getTransform(); // save old transform
        g.rotate(getRotation(), getX(), getY()); // rotate the object that is floating into the black hole
        g.drawImage(image, (int) getX() - image.getWidth() / 2, (int) getY() - image.getHeight() / 2, Game.INSTANCE); // draw image in the center
        g.setTransform(oldTransform); // set transform back to default
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        addRotation(dt / 2); // rotate entity more

        double distX = Math.abs(getX() - Game.WIDTH / 2);
        double distY = Math.abs(getY() - Game.HEIGHT / 2);

        if (Math.pow(distX, 2) + Math.pow(distY, 2) < Math.pow(128, 2)) { // if the centtiy is insise of the black hole, kill it
            remove(); // remove entity
            Game.INSTANCE.addCash(cash); // add cash
            Game.INSTANCE.addEntity(
                new CashEntity(getX(), getY(), cash, 3)
            ); // create cash entity that pops up
            Game.INSTANCE.addEntity(
                new DestroyedFramentEntity(getX(), getY(), image, 1, getRotation())
            ); // make the object fade out

            ResourceManager.SWALLOW_SOUND.play(); // play swallow sound

            return;
        }

        

        double angle = Math.atan(distY / distX); // get angle to te black hole

        double ddx = 25 * Math.cos(angle) * dt; // calculate acceleration towards black hole
        if (getX() > Game.WIDTH / 2) {
            ddx *= -1;
        } // correct acceleration so it is always facing the black hole

        double ddy = 25 * Math.sin(angle) * dt; // calculate acceleration towards black hole
        if (getY() > Game.HEIGHT / 2) {
            ddy *= -1;
        } // correct acceleration so it is always facing blackhole

        addDeltaX(ddx); // add the acceleration
        addDeltaY(ddy); // add the acceleration
    }

}