package game.upgrade.upgrades;

import game.upgrade.GeneratorUpgrade;

public class ChairUpgrade extends GeneratorUpgrade {

    public ChairUpgrade(long level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Chair Upgrade";
    }

    @Override
    public long getBaseCost() {
        return 500;
    }
    
}