package game.upgrade.upgrades;

import java.awt.image.BufferedImage;

import game.ResourceManager;
import game.upgrade.Upgrade;

public class WhiteholeDeleterUpgrade extends Upgrade {

    public WhiteholeDeleterUpgrade(long level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Whitehole Deleter Upgrade";
    }

    @Override
    public BufferedImage getIcon() {
        return ResourceManager.RESOURCE_MAP.get("whitehole.png");
    }

    @Override
    public long getBaseCost() {
        return 3000;
    }

    @Override
    public long getMaxLevel() {
        return 10;
    }
    
}
