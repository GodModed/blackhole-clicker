package game.upgrades;

import java.awt.image.BufferedImage;

import game.ResourceManager;
import game.Upgrade;

public class ChairUpgrade extends Upgrade {

    @Override
    public String getId() {
        return "chair_upgrade";
    }

    @Override
    public String getName() {
        return "Chair Upgrade";
    }

    @Override
    public BufferedImage getIcon() {
        return ResourceManager.WOOD_CHAIR_IMAGE;
    }

    @Override
    public long getBaseCost() {
        return 100;
    }

    @Override
    public long getMaxLevel() {
        return 5;
    }
    
}
