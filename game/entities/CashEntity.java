package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

import game.Entity;
import game.NumberFormatter;

public class CashEntity extends Entity {

    private final double cash;
    private final double fadeTime;
    private double currentFade;

    public CashEntity(double x, double y, double cash, double fadeTime) {
        super(x, y);
        this.cash = cash;
        this.fadeTime = fadeTime;
        this.currentFade = fadeTime;
        setDeltaY(-150);
    }

    @Override
    public void render(Graphics2D g) {

        FontMetrics metric = g.getFontMetrics();
        String cashString = "+$" + NumberFormatter.format(cash);

        int width = metric.stringWidth(cashString);

        double alpha = (currentFade / fadeTime) * 255;
        Color color = new Color(0, 255, 0, (int) alpha);
        g.setColor(color);
        g.drawString(cashString, (int) getX() - width / 2, (int) getY());
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        currentFade -= dt;
        if (currentFade <= 0) remove();
        
        addDeltaX((Math.random() - 0.5) * 1500 * dt);
    }
}