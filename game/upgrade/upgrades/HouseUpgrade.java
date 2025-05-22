package game.upgrade.upgrades;

import game.upgrade.GeneratorUpgrade;

public class HouseUpgrade extends GeneratorUpgrade {

    public HouseUpgrade(long level) {
        super(level);
    }

    @Override
    public String getName() {
        return "House Upgrade";
    }

    @Override
    public long getBaseCost() {
        return 10_000;
    }
    
}
