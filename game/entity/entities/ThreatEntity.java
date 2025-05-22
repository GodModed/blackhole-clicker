package game.entity.entities;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import game.Game;
import game.ResourceManager;

public class ThreatEntity extends DestroyableEntity {

    private final static BufferedImage IMAGE = ResourceManager.RESOURCE_MAP.get("whitehole.png");

    public ThreatEntity(double x, double y, double cash) {
        super(x, y, IMAGE, -cash);
    }

    @Override
    public void click(MouseEvent e) {
        double distX = Math.abs(e.getX() - getX());
        double distY = Math.abs(e.getY() - getY());

        // check if click is in range
        if (Math.pow(distX, 2) + Math.pow(distY, 2) >= Math.pow(64, 2)) return;

        remove();
        Game.INSTANCE.addEntity(
            new DestroyedFramentEntity(getX(), getY(), IMAGE, 1, 0)  
        );

        ResourceManager.SWALLOW_SOUND.play();
    }

}
