import javax.swing.SwingUtilities;

import game.Game;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Game();  
        });
    }
}