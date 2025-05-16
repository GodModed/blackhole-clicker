package game.entities;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.Entity;
import game.Game;
import game.NumberFormatter;
import game.ResourceManager;

public class CurrentCashEntity extends Entity {

    public CurrentCashEntity() {
        super(Game.WIDTH / 2, ResourceManager.BLACKHOLE_IMAGE.getHeight() / 2 + 20);
    }

    @Override
    public void render(Graphics2D g) {

        Font oldFont = g.getFont();
        g.setFont(oldFont.deriveFont(50f));

        FontMetrics metrics = g.getFontMetrics();
        String totalCashString = "$" + NumberFormatter.format(Game.INSTANCE.getCash());
        Rectangle2D bounds = metrics.getStringBounds(totalCashString, g);

        g.drawString(totalCashString, (int) (getX() - bounds.getWidth() / 2), (int) (getY() - bounds.getHeight() / 2 ));
        g.setFont(oldFont);
    }

    @Override
    public void update(double dt) {
        setX(Game.WIDTH / 2);
        setY(ResourceManager.BLACKHOLE_IMAGE.getHeight() / 2 + 20);
    }
    
}
