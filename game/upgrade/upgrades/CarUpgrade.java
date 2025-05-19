package game.upgrade.upgrades;

import game.upgrade.GeneratorUpgrade;

public class CarUpgrade extends GeneratorUpgrade {

    public CarUpgrade(long level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Car Upgrade";
    }

    @Override
    public long getBaseCost() {
        return 2000;
    }
    
}
