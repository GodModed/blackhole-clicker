import javax.swing.SwingUtilities;
import java.util.Scanner;

import game.Game;

public class Main {
    
    public static void main(String[] args) {

        System.out.println("|~~\\|       |  |       |      /~~|'   |");
        System.out.println("|--<|/~~|/~~|_/|/~\\ /~\\|/~/  |   ||/~~|_//~/|/~\\");
        System.out.println("|__/|\\__|\\__| \\|   |\\_/|\\/_   \\__||\\__| \\\\/_|");
                                                
        System.out.println("|         |~~\\     |           |");
        System.out.println("|~~\\\\  /  |   |\\  /|/~~||/~\\   |  /~/\\  /\\  /");
        System.out.println("|__/ \\/   |__/  \\/ |\\__||   |  |__\\/_ \\/  \\/");
        System.out.println("    _/         _/                        _/");

        System.out.println("In this game, you start out with no money. Your goal is to obtain as much money as you can.");
        System.out.println("To start, click on the black hole to gain a little bit of money. Then, buy upgrades.");
        System.out.println("Be aware of the white holes that ocassioanly fly in! They will take 10% of your cash away. Stay alert and click on them to prevent this from happening.");

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