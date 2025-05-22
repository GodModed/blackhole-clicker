package game.upgrade.upgrades;

import java.awt.image.BufferedImage;

import game.ResourceManager;
import game.upgrade.Upgrade;

public class GeneratorSpeedUpgrade extends Upgrade {

    public GeneratorSpeedUpgrade(long level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Generator Speed Upgrade";
    }

    @Override
    public BufferedImage getIcon() {
        return ResourceManager.RESOURCE_MAP.get("upgrade.png");
    }

    @Override
    public long getBaseCost() {
        return 750;
    }

    @Override
    public long getMaxLevel() {
        return 4;
    }
    
}
