package game.entity.entities;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.Game;
import game.entity.Entity;

import java.awt.image.BufferedImage;

public class DestroyedFramentEntity extends Entity {

    private final double fadeTime;
    private double currentFade;
    private final BufferedImage image;

    public DestroyedFramentEntity(double x, double y, BufferedImage image, double fadeTime, double rotation) {
        super(x, y);
        this.image = image;
        this.fadeTime = fadeTime;
        this.currentFade = fadeTime;
        this.setRotation(rotation);
    }

    @Override
    public void render(Graphics2D g) {
        double alpha = currentFade / fadeTime; // get fade amount
        Composite composite = g.getComposite(); // get old composite
        AffineTransform oldTransform = g.getTransform(); // get old transform
        g.setComposite(AlphaComposite.SrcOver.derive((float) alpha)); // fade the entity outs
        g.rotate(getRotation(), getX(), getY()); // rotate the entity
        g.drawImage(image, (int) getX() - image.getWidth() / 2, (int) getY() - image.getHeight() / 2, Game.INSTANCE); // draw the image
        g.setTransform(oldTransform); // set transform back to default
        g.setComposite(composite); // set compose back to defaults
    }

    @Override
    public void update(double dt) {
        currentFade -= dt; // fade out over time
        if (currentFade <= 0) remove(); // remove if the entity has faded out of existance
    }
    
}
