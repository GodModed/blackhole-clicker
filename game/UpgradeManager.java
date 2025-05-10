package game;

import java.util.HashMap;
import java.util.Map;

import game.upgrades.ChairUpgrade;
import game.upgrades.ClickUpgrade;

public class UpgradeManager {

    private static Map<Class<? extends Upgrade>, Upgrade> upgradeMap = new HashMap<>();

    public static void load() {
        register(
            new ChairUpgrade(1),
            new ClickUpgrade(1)
        );
    }

    private static <T extends Upgrade> void register(T upgrade) {
        upgradeMap.put(upgrade.getClass(), upgrade);
    }

    @SuppressWarnings("all")
    private static <T extends Upgrade> void register(T... upgrades) {
        for (T upgrade : upgrades) {
            register(upgrade);
        }
    }

    public static Upgrade getUpgrade(Class<? extends Upgrade> clazz) {
        return upgradeMap.get(clazz);
    }


}
