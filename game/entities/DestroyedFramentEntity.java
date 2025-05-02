package game.entities;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import game.Entity;
import game.Game;

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
        double alpha = currentFade / fadeTime;
        Composite composite = g.getComposite();
        AffineTransform oldTransform = g.getTransform();
        g.setComposite(AlphaComposite.SrcOver.derive((float) alpha));
        g.rotate(getRotation(), getX(), getY());
        g.drawImage(image, (int) getX() - image.getWidth() / 2, (int) getY() - image.getHeight() / 2, Game.INSTANCE);
        g.setTransform(oldTransform);
        g.setComposite(composite);       
    }

    @Override
    public void update(double dt) {
        currentFade -= dt;
        if (currentFade <= 0) remove();
    }
    
}
