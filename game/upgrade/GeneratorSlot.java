package game.upgrade;

import java.awt.image.BufferedImage;

public class GeneratorSlot {
    public final BufferedImage image;
    public final long cash;

    public GeneratorSlot(BufferedImage image, long cost) {
        this.image = image;
        this.cash = cost;
    }
}
