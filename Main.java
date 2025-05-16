import javax.swing.SwingUtilities;

import game.Game;

public class Main {
    
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true"); // I heard somewhere that this makes the game run faster. However, I have not noticed an effect.
        SwingUtilities.invokeLater(() -> {
            new Game(); // start the game!
        });
    }

}