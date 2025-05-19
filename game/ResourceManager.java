package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.awt.Font;
import java.awt.font.TextAttribute;

import javax.imageio.ImageIO;

import game.upgrade.GeneratorSlot;

public class ResourceManager {
    public static BufferedImage BLACKHOLE_IMAGE;
    public static BufferedImage SHOP_IMAGE;
    public static BufferedImage WOOD_CHAIR_IMAGE;
    public static BufferedImage STONE_CHAIR_IMAGE;
    public static BufferedImage IRON_CHAIR_IMAGE;
    public static BufferedImage DIAMOND_CHAIR_IMAGE;
    public static BufferedImage RUBY_CHAIR_IMAGE;
    public static BufferedImage SMART_CAR_IMAGE;
    public static Font MONOCRAFT_FONT;
    public static Sound SWALLOW_SOUND;
    public static Sound CLICK_SOUND;
    public static Sound SWOOSH_SOUND;
    public static Sound UPGRADE_SOUND;

    public static Map<String, List<GeneratorSlot>> GENERATOR_MAP = new HashMap<>();

    @SuppressWarnings("all")
    public static void load() throws Exception {
        // basically, this method just loads all resources before the game starts so they can be used by reference
        BLACKHOLE_IMAGE = ImageIO.read(new File("resources/blackhole.png"));

        SHOP_IMAGE = ImageIO.read(new File("resources/shop.png"));

        WOOD_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/wood_chair.png"));
        STONE_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/stone_chair.png"));
        IRON_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/iron_chair.png"));
        DIAMOND_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/diamond_chair.png"));
        RUBY_CHAIR_IMAGE = ImageIO.read(new File("resources/chair/ruby_chair.png"));

        SMART_CAR_IMAGE = ImageIO.read(new File("resources/car/smart_car.png"));

        MONOCRAFT_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("resources/Monocraft.ttf")).deriveFont(24f);
        Map attributes = MONOCRAFT_FONT.getAttributes();
        attributes.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
        MONOCRAFT_FONT = MONOCRAFT_FONT.deriveFont(attributes);

        SWALLOW_SOUND = new Sound(new File("resources/audio/swallow.wav"));
        CLICK_SOUND = new Sound(new File("resources/audio/click.wav"));
        SWOOSH_SOUND = new Sound(new File("resources/audio/swoosh.wav"));
        UPGRADE_SOUND = new Sound(new File("resources/audio/upgrade.wav"));

        loadGenerators();

    }

    public static void loadGenerators() throws IOException {
        File file = new File("resources");
        for (File dir : file.listFiles()) {
            loadGenerator(dir);
        }
    }

    public static void loadGenerator(File dir) throws IOException {
        if (!dir.isDirectory()) return;
        File manifestFile = null;
        for (File file : dir.listFiles()) {
            if (file.getName().equals("manifest.txt")) {
                manifestFile = file;
                break;
            }
        }

        if (manifestFile == null) return;

        Scanner scanner = new Scanner(
            new FileInputStream(manifestFile)
        );

        String name = scanner.nextLine();
        List<GeneratorSlot> slots = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ");
            String imageFileName = splitLine[0];
            long cost = Long.parseLong(splitLine[1]);
            File imageFile = new File(dir.getPath() + "/" + imageFileName + ".png");
            BufferedImage image = ImageIO.read(imageFile);
            slots.add(new GeneratorSlot(image, cost));
        }

        scanner.close();

        GENERATOR_MAP.put(name, slots);
    }

}
