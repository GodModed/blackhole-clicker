package game.upgrade;

import java.awt.image.BufferedImage;

import game.Game;
import game.ResourceManager;

public abstract class Upgrade {

    private long currentLevel;
    
    public Upgrade(long currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Upgrade() {
        this.currentLevel = 0;
    }
    
    public abstract String getName();
    public abstract BufferedImage getIcon();
    public abstract long getBaseCost();
    public abstract long getMaxLevel();

    public long getCurrentLevel() {
        return currentLevel;
    }

    public double getCost(long level) {
        if (level >= getMaxLevel()) return -1.0; // if max, return impossible value
        return getBaseCost() * Math.pow(1.3, level); // multiply by 1.3 for every level
    }

    public double getCost() {
        return getCost(currentLevel);
    }

    public void addLevel() {
        if (isMaxLevel()) return;
        currentLevel++;
    }

    public boolean isMaxLevel() {
        return currentLevel >= getMaxLevel();
    }

    public void upgrade() {
        if (isMaxLevel()) return; // can't go above max level
        if (Game.INSTANCE.getCash() < getCost()) return; // check if it has enough cash
        ResourceManager.UPGRADE_SOUND.play(); // play upgrade sound
        Game.INSTANCE.addCash(-getCost()); // remove cash
        addLevel(); // upgrade
    }

    
    
}
