package game.entity.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

import game.NumberFormatter;
import game.entity.Entity;

public class CashEntity extends Entity {

    private final double cash;
    private final double fadeTime;
    private double currentFade;

    public CashEntity(double x, double y, double cash, double fadeTime) {
        super(x, y);
        this.cash = cash;
        this.fadeTime = fadeTime;
        this.currentFade = fadeTime;
        setDeltaY(-150); // go up at 150 pixels / sec
    }

    @Override
    public void render(Graphics2D g) {

        FontMetrics metric = g.getFontMetrics();
        String plusOrNegative = cash < 0 ? "-" : "+";
        String cashString = plusOrNegative + "$" + NumberFormatter.format(Math.abs(cash)); // format cash string

        int width = metric.stringWidth(cashString); // get width so it can be centered

        double alpha = (currentFade / fadeTime) * 255; // get alpha scaled from 0 to 255
        Color color = new Color(0, 255, 0, (int) alpha);
        if (cash < 0) {
            color = new Color(255, 0 , 0, (int) alpha);
        }
        g.setColor(color); // set color of cash
        g.drawString(cashString, (int) getX() - width / 2, (int) getY()); // draw cash
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        currentFade -= dt; // decrease fade
        if (currentFade <= 0) remove(); // remove the entity when it is faded out of existance
        
        addDeltaX((Math.random() - 0.5) * 1500 * dt); // make it jitter around randomly
    }
}