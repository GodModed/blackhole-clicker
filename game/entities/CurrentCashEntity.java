package game.entities;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import game.Entity;
import game.Game;

public class CurrentCashEntity extends Entity {

    public CurrentCashEntity() {
        super(Game.WIDTH / 2, 75);
    }

    @Override
    public void render(Graphics2D g) {
        FontMetrics metrics = g.getFontMetrics();
        String totalCashString = "$" + (long) Game.INSTANCE.getCash();
        int width = metrics.stringWidth(totalCashString);

        g.drawString(totalCashString, (int) (getX() - width / 2), (int) getY());
    }
    
}
