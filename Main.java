import javax.swing.SwingUtilities;
import java.util.Scanner;

import game.Game;

public class Main {
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true"); // I heard somewhere that this makes the game run faster. However, I have not noticed an effect.
        forceAffirm();        
        SwingUtilities.invokeLater(() -> {
            new Game(); // start the game!
        });
    }

    public static void forceAffirm() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Have you read the directions? [ENTER] ");
        scanner.nextLine();
        scanner.close();
    }

}