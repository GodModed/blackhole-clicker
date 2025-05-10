package game;

import java.awt.image.BufferedImage;

public abstract class Upgrade {

    private long currentLevel;
    
    public Upgrade(long currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Upgrade() {
        this.currentLevel = 0;
    }
    
    public abstract String getId();
    public abstract String getName();
    public abstract BufferedImage getIcon();
    public abstract long getBaseCost();
    public abstract long getMaxLevel();

    public long getCurrentLevel() {
        return currentLevel;
    }

    public double getCost(long level) {
        if (level > getMaxLevel()) return -1.0;
        return getBaseCost() * Math.pow(1.15, level);
    }

    public double getCost() {
        return getCost(currentLevel + 1);
    }

    public void addLevel() {
        if (currentLevel >= getMaxLevel()) return;
        currentLevel++;
    }

    
    
}
