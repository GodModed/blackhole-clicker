package game.upgrade.upgrades;

import java.awt.image.BufferedImage;

import game.ResourceManager;
import game.upgrade.Upgrade;

public class ClickUpgrade extends Upgrade {

    public ClickUpgrade(long level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Click Upgrade";
    }

    @Override
    public BufferedImage getIcon() {
        return ResourceManager.RESOURCE_MAP.get("click_upgrade.png");
    }

    @Override
    public long getBaseCost() {
        return 50;
    }

    @Override
    public long getMaxLevel() {
        return Long.MAX_VALUE;
    }
    
}