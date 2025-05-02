package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Font;

import javax.imageio.ImageIO;

public class ResourceManager {
    public static BufferedImage BLACKHOLE_IMAGE;
    public static BufferedImage WOOD_CHAIR_IMAGE;
    public static BufferedImage STONE_CHAIR_IMAGE;
    public static BufferedImage IRON_CHAIR_IMAGE;
    public static BufferedImage DIAMOND_CHAIR_IMAGE;
    public static BufferedImage RUBY_CHAIR_IMAGE;
    public static Font MONOCRAFT_FONT;

    public static void load() throws Exception {
        BLACKHOLE_IMAGE = ImageIO.read(new File("resources/blackhole.png"));

        WOOD_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/wood_chair.png"));
        STONE_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/stone_chair.png"));
        IRON_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/iron_chair.png"));
        DIAMOND_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/diamond_chair.png"));
        RUBY_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/ruby_chair.png"));

        MONOCRAFT_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("resources/Monocraft.ttf")).deriveFont(24f);
    }

}
