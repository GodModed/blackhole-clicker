package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;

import game.Entity;
import game.Game;
import game.ResourceManager;

public class OpenShopEntity extends Entity {

    public static OpenShopEntity INSTANCE;
    private static final BufferedImage IMAGE = ResourceManager.SHOP_IMAGE;

    public OpenShopEntity() {
        super(Game.WIDTH - IMAGE.getWidth() / 2 - 20, IMAGE.getHeight() - 20);
        if (INSTANCE != null) throw new RuntimeException("Open shop already exists.");
        INSTANCE = this;
    }

    @Override
    public void render(Graphics2D g) {    
        g.drawImage(IMAGE, (int) (getX() - IMAGE.getWidth() / 2), (int) (getY() - IMAGE.getHeight() / 2), Game.INSTANCE);
    }

    @Override
    public void update(double dt) {
        setX(Game.WIDTH - IMAGE.getWidth() / 2 - 20);
        setY(IMAGE.getHeight() - 20);
    }

    public void click(MouseEvent e) {
        double distX = Math.abs(e.getX() - getX());
        double distY = Math.abs(e.getY() - getY());

        if (Math.pow(distX, 2) + Math.pow(distY, 2) >= Math.pow(64, 2)) return;
        ShopEntity.INSTANCE.toggle();
    }
    
}
