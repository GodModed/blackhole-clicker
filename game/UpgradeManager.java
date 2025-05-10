package game;

import java.util.HashMap;
import java.util.Map;

import game.upgrades.ChairUpgrade;

public class UpgradeManager {

    public static UpgradeManager INSTANCE;

    private Map<Class<? extends Upgrade>, Upgrade> upgradeMap = new HashMap<>();

    public UpgradeManager() {
        if (INSTANCE != null) throw new RuntimeException("UpgradeManager already exists");
        INSTANCE = this;
        register(
            new ChairUpgrade()
        );
    }

    private <T extends Upgrade> void register(T upgrade) {
        upgradeMap.put(upgrade.getClass(), upgrade);
    }

    private <T extends Upgrade> void register(T... upgrades) {
        for (T upgrade : upgrades) {
            register(upgrade);
        }
    }

    public Upgrade getUpgrade(Class<? extends Upgrade> clazz) {
        return upgradeMap.get(clazz);
    }


}
