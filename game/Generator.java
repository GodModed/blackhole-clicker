package game;

import java.awt.Point;

import game.entity.entities.DestroyableEntity;
import game.upgrade.GeneratorUpgrade;
import game.upgrade.Upgrade;
import game.upgrade.UpgradeManager;

public class Generator implements Runnable {

    @Override
    public void run() {
        while (Game.INSTANCE.running) {

            for (Upgrade upgrade : UpgradeManager.getUpgrades()) {
                spawnObject(upgrade);
            }

            try {
                Thread.sleep(5000); // do this every 5 seconds
            } catch (InterruptedException e) {}
        }
    }

    private void spawnObject(Upgrade upgrade) {
        if (upgrade == null) return;
        if (!(upgrade instanceof GeneratorUpgrade)) return;
        if (upgrade.getCurrentLevel() == 0) return;
        GeneratorUpgrade generatorUpgrade = (GeneratorUpgrade) upgrade;
        Point randomPoint = generateRandomPos(getRadius());
        DestroyableEntity object = new DestroyableEntity(randomPoint.getX(), randomPoint.getY(), generatorUpgrade.getCurrentIcon(), generatorUpgrade.getCurrentCash());
        Game.INSTANCE.getEntities().add(object);
    }

    private double getRadius() {
        if (Game.HEIGHT > Game.WIDTH) return Game.WIDTH;
        return Game.HEIGHT;
    }

    private Point generateRandomPos(double radius) {
        // get random point around the center
        double theta = Math.random() * 2 * Math.PI;
        double x = Math.cos(theta) * radius + Game.WIDTH / 2;
        double y = Math.sin(theta) * radius + Game.HEIGHT / 2;
        return new Point((int) x, (int) y);
    }

}