package game;

public class UpgradeManager {

    public static double CLICK_MULTIPLIER = 1;
    public static ChairLevel CHAIR_LEVEL = ChairLevel.WOOD;

    public enum ChairLevel {
        NONE(0),
        WOOD(15),
        STONE(20),
        IRON(25),
        DIAMOND(35),
        RUBY(50);

        public double cash;

        private ChairLevel(double cash) {
            this.cash = cash;
        }
    }

}
