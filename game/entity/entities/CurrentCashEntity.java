package game.entity.entities;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.Game;
import game.NumberFormatter;
import game.ResourceManager;
import game.entity.Entity;

public class CurrentCashEntity extends Entity {

    public CurrentCashEntity() {
        super(Game.WIDTH / 2, ResourceManager.BLACKHOLE_IMAGE.getHeight() / 2 + 20);
    }

    @Override
    public void render(Graphics2D g) {

        Font oldFont = g.getFont();
        g.setFont(oldFont.deriveFont(50f)); // increase font size

        FontMetrics metrics = g.getFontMetrics();
        String totalCashString = "$" + NumberFormatter.format(Game.INSTANCE.getCash()); // format cash string
        Rectangle2D bounds = metrics.getStringBounds(totalCashString, g); // get bounds so it can stay in the middle

        g.drawString(totalCashString, (int) (getX() - bounds.getWidth() / 2), (int) (getY() - bounds.getHeight() / 2 )); // draw text centered on teh screen
        g.setFont(oldFont); // set font size back to default
    }

    @Override
    public void resize() {
        setX(Game.WIDTH / 2); // stay in middle on resize
        setY(ResourceManager.BLACKHOLE_IMAGE.getHeight() / 2 + 20); // stay in same spot on resize
    }
    
}
