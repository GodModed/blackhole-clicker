package game;

import java.awt.Point;

import game.entity.entities.DestroyableEntity;
import game.entity.entities.ThreatEntity;
import game.upgrade.GeneratorUpgrade;
import game.upgrade.Upgrade;
import game.upgrade.UpgradeManager;
import game.upgrade.upgrades.GeneratorSpeedUpgrade;
import game.upgrade.upgrades.WhiteholeDeleterUpgrade;

public class Generator implements Runnable {

    @Override
    public void run() {
        while (Game.INSTANCE.running) {
            if (Math.random() < 1.0 / (4 + UpgradeManager.getUpgrade(WhiteholeDeleterUpgrade.class).getCurrentLevel())) { // chance that a threat spawns
                Point randomPoint = generateRandomPos(getRadius());
                Game.INSTANCE.addEntity(
                    new ThreatEntity(randomPoint.getX(), randomPoint.getY(), Game.INSTANCE.getCash() / 10)
                );
            }
            

            for (Upgrade upgrade : UpgradeManager.getUpgrades()) {
                spawnObject(upgrade);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }

            try {
                Thread.sleep(5000 - UpgradeManager.getUpgrade(GeneratorSpeedUpgrade.class).getCurrentLevel() * 1000); // 5 seconds - generator level
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void spawnObject(Upgrade upgrade) {
        if (upgrade == null) return;
        if (!(upgrade instanceof GeneratorUpgrade)) return;
        if (upgrade.getCurrentLevel() == 0) return;
        GeneratorUpgrade generatorUpgrade = (GeneratorUpgrade) upgrade;
        Point randomPoint = generateRandomPos(getRadius());
        DestroyableEntity object = new DestroyableEntity(randomPoint.getX(), randomPoint.getY(), generatorUpgrade.getCurrentIcon(), generatorUpgrade.getCurrentCash());
        Game.INSTANCE.addEntity(object);
    }

    private double getRadius() {
        if (Game.HEIGHT > Game.WIDTH) return Game.WIDTH / 2;
        return Game.HEIGHT / 2;
    }

    private Point generateRandomPos(double radius) {
        // get random point around the center
        double theta = Math.random() * 2 * Math.PI;
        double x = Math.cos(theta) * radius + Game.WIDTH / 2;
        double y = Math.sin(theta) * radius + Game.HEIGHT / 2;
        return new Point((int) x, (int) y);
    }

}