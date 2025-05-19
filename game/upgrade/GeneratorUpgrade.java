package game.upgrade;

import java.awt.image.BufferedImage;

import game.ResourceManager;

public abstract class GeneratorUpgrade extends Upgrade implements IGeneratorUpgrade {

    public GeneratorUpgrade(long level) {
        super(level);
    }

    @Override
    public BufferedImage getIcon() {
        long iconLevel = getCurrentLevel() + 1;
        if (iconLevel > getMaxLevel()) iconLevel = getMaxLevel();
        return ResourceManager.GENERATOR_MAP.get(getName()).get((int) (iconLevel - 1)).image;
    }
    
    @Override
    public BufferedImage getCurrentIcon() {
        return ResourceManager.GENERATOR_MAP.get(getName()).get((int) getCurrentLevel() - 1).image;
    }

    @Override
    public long getCurrentCash() {
        return ResourceManager.GENERATOR_MAP.get(getName()).get((int) getCurrentLevel() - 1).cash;
    }

}
