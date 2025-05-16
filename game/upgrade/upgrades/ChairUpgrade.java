package game.upgrade.upgrades;

import java.awt.image.BufferedImage;

import game.ResourceManager;
import game.upgrade.Upgrade;

public class ChairUpgrade extends Upgrade {

    public ChairUpgrade(long level) {
        super(level);
    }

    public enum ChairLevel {
        NONE(0, ResourceManager.WOOD_CHAIR_IMAGE),
        WOOD(5, ResourceManager.WOOD_CHAIR_IMAGE),
        STONE(10, ResourceManager.STONE_CHAIR_IMAGE),
        IRON(20, ResourceManager.IRON_CHAIR_IMAGE),
        DIAMOND(50, ResourceManager.DIAMOND_CHAIR_IMAGE),
        RUBY(100, ResourceManager.RUBY_CHAIR_IMAGE);

        public long cash;
        public BufferedImage image;

        private ChairLevel(long cash, BufferedImage image) {
            this.cash = cash;
            this.image = image;
        }
    }

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
        if (getCurrentLevel() == getMaxLevel()) return ChairLevel.values()[(int) getMaxLevel()].image;
        return ChairLevel.values()[(int) getCurrentLevel() + 1].image;
    }

    @Override
    public long getBaseCost() {
        return 500;
    }

    @Override
    public long getMaxLevel() {
        return 5;
    }
    
}