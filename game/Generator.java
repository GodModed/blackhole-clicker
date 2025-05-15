package game;

import java.awt.Point;

import game.entities.DestroyableEntity;
import game.upgrades.ChairUpgrade;
import game.upgrades.ChairUpgrade.ChairLevel;

public class Generator implements Runnable {

    @Override
    public void run() {
        while (Game.INSTANCE.running) {


            Point randomPos = generateRandomPos(500);
            Upgrade chairUpgrade = UpgradeManager.getUpgrade(ChairUpgrade.class);
            if (chairUpgrade.getCurrentLevel() != 0) {
                DestroyableEntity chair = new DestroyableEntity(randomPos.getX(), randomPos.getY(), ChairLevel.values()[(int) chairUpgrade.getCurrentLevel()].image, ChairLevel.values()[(int) chairUpgrade.getCurrentLevel()].cash);
                Game.INSTANCE.getEntities().add(chair);
            }
            

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {}
        }
    }

    private Point generateRandomPos(double radius) {
        double theta = Math.random() * 2 * Math.PI;
        double x = Math.cos(theta) * radius + Game.WIDTH / 2;
        double y = Math.sin(theta) * radius + Game.HEIGHT / 2;
        return new Point((int) x, (int) y);
    }

}