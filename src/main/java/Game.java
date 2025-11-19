import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Deck deck;

    public Game(){
        Scanner scanner = new Scanner(System.in);
        String[] names = new String[4];
        for (int i = 0; i < 4; i++) {
            System.out.println("Player " + i + ": ");
            names[i] = scanner.nextLine();
        }

    }
    public static void main(String[] args) {
        System.out.println("Testing");
    }
}
