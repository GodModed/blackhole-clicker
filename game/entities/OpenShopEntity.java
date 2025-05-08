package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Entity;
import game.Game;
import game.ResourceManager;

public class OpenShopEntity extends Entity {

    private static final BufferedImage IMAGE = ResourceManager.SHOP_IMAGE;

    public OpenShopEntity() {
        super(Game.WIDTH - IMAGE.getWidth() / 2 - 20, IMAGE.getHeight() - 20);
    }

    @Override
    public void render(Graphics2D g) {
        
        g.drawImage(IMAGE, (int) (getX() - IMAGE.getWidth() / 2), (int) (getY() - IMAGE.getHeight() / 2), Game.INSTANCE);
        
    }
    
}
