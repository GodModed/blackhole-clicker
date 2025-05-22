package game.upgrade.upgrades;

import game.upgrade.GeneratorUpgrade;

public class PlanetUpgrade extends GeneratorUpgrade {

    public PlanetUpgrade(long level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Planet Upgrade";
    }

    @Override
    public long getBaseCost() {
        return 100_000;
    }

}