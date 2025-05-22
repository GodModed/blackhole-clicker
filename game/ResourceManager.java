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
import java.util.concurrent.CompletableFuture;
import java.awt.Font;
import java.awt.font.TextAttribute;

import javax.imageio.ImageIO;

import game.upgrade.GeneratorSlot;

public class ResourceManager {

    public static Font MONOCRAFT_FONT;
    public static Sound SWALLOW_SOUND;
    public static Sound CLICK_SOUND;
    public static Sound SWOOSH_SOUND;
    public static Sound UPGRADE_SOUND;

    private static ArrayList<CompletableFuture<Void>> futures = new ArrayList<>();
    public static Map<String, BufferedImage> RESOURCE_MAP = new HashMap<>();
    public static Map<String, List<GeneratorSlot>> GENERATOR_MAP = new HashMap<>();

    @SuppressWarnings("all")
    public static void load() throws Exception {
        // basically, this method just loads all resources before the game starts so they can be used by reference
        loadImage("blackhole.png");
        loadImage("whitehole.png");

        loadImage("shop.png");
        loadImage("click_upgrade.png");

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

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

    public static void loadImage(String path) {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            try {
                RESOURCE_MAP.put(path, ImageIO.read(new File("resources/" + path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });

        futures.add(future);
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
