
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("************************************");
        System.out.println("       JAVA MILLIONAIRE CHALLENGE");
        System.out.println("************************************");

        System.out.print("Enter Your Name: ");

        String name = input.nextLine().trim();

        if (name.isEmpty()) {
            name = "Player";
        }

        Player player = new Player(name);

        Game game = new Game(player, input);

        game.startGame();

        input.close();
    }
}
