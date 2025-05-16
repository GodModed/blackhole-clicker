package game;

import java.awt.Point;

import game.entity.entities.DestroyableEntity;
import game.upgrade.Upgrade;
import game.upgrade.UpgradeManager;
import game.upgrade.upgrades.ChairUpgrade;
import game.upgrade.upgrades.ChairUpgrade.ChairLevel;

public class Generator implements Runnable {

    @Override
    public void run() {
        while (Game.INSTANCE.running) {


            // spawn chair with current level at random position with radius 500 from center
            Point randomPos = generateRandomPos(500);
            Upgrade chairUpgrade = UpgradeManager.getUpgrade(ChairUpgrade.class);
            if (chairUpgrade.getCurrentLevel() != 0) {
                DestroyableEntity chair = new DestroyableEntity(randomPos.getX(), randomPos.getY(), ChairLevel.values()[(int) chairUpgrade.getCurrentLevel()].image, ChairLevel.values()[(int) chairUpgrade.getCurrentLevel()].cash);
                Game.INSTANCE.getEntities().add(chair);
            }
            

            try {
                Thread.sleep(5000); // do this every 5 seconds
            } catch (InterruptedException e) {}
        }
    }

    private Point generateRandomPos(double radius) {
        // get random point around the center
        double theta = Math.random() * 2 * Math.PI;
        double x = Math.cos(theta) * radius + Game.WIDTH / 2;
        double y = Math.sin(theta) * radius + Game.HEIGHT / 2;
        return new Point((int) x, (int) y);
    }

}