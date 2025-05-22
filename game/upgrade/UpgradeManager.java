package game.upgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.upgrade.upgrades.CarUpgrade;
import game.upgrade.upgrades.ChairUpgrade;
import game.upgrade.upgrades.ClickUpgrade;
import game.upgrade.upgrades.GeneratorSpeedUpgrade;
import game.upgrade.upgrades.HouseUpgrade;
import game.upgrade.upgrades.PlanetUpgrade;
import game.upgrade.upgrades.WhiteholeDeleterUpgrade;

public class UpgradeManager {

    private static Map<Class<? extends Upgrade>, Upgrade> upgradeMap = new HashMap<>();
    private static ArrayList<Upgrade> upgrades = new ArrayList<>();

    public static void load() {
        register(
            new ClickUpgrade(1),
            new ChairUpgrade(0),
            new GeneratorSpeedUpgrade(0),
            new CarUpgrade(0),
            new WhiteholeDeleterUpgrade(0),
            new HouseUpgrade(0),
            new PlanetUpgrade(0)
        );
    }

    private static void register(Upgrade upgrade) {
        upgradeMap.put(upgrade.getClass(), upgrade);
        upgrades.add(upgrade);
    }

    @SuppressWarnings("all")
    private static void register(Upgrade... upgrades) {
        for (Upgrade upgrade : upgrades) {
            register(upgrade);
        }
    }

    public static Upgrade getUpgrade(Class<? extends Upgrade> clazz) {
        return upgradeMap.get(clazz);
    }

    public static ArrayList<Upgrade> getUpgrades() {
        return upgrades;
    }


}
