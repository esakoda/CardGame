import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private ArrayList<Card> hand1;
    private ArrayList<Card> hand2;
    private ArrayList<Card> hand3;
    private ArrayList<Card> hand4;
    private Deck deck;
    private Card twoOfClubs;
    private ArrayList<Card> trick;
    private Player taker;
    private Player current;
    private Card choice;
    private Card cardFromHand;

    public Game(){
        // Get the name of each player
        Scanner scanner = new Scanner(System.in);
        String[] names = new String[4];
        for (int i = 1; i < 5; i++) {
            System.out.println("Player " + i + ": ");
            names[i - 1] = scanner.nextLine();
        }
        // Create main deck of cards
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Diamonds", "Clubs", "Spades", "Hearts"};
        int[] values = {0, 1, 13};
        deck = new Deck(ranks, suits, values);

        // Deal the deck out to each player
        hand1 = new ArrayList<Card>();
        hand2 = new ArrayList<Card>();
        hand3 = new ArrayList<Card>();
        hand4 = new ArrayList<Card>();

        int cardsPerPlayer = (deck.getCardsLeft() / 4);
        for (int i = 0; i < cardsPerPlayer; i++)
        {
            hand1.add(deck.deal());
            hand2.add(deck.deal());
            hand3.add(deck.deal());
            hand4.add(deck.deal());
        }

        // Create 4 Players
        player1 = new Player(names[0], hand1, 1);
        player2 = new Player(names[1], hand2, 2);
        player3 = new Player(names[2], hand3, 3);
        player4 = new Player(names[3], hand4, 4);

        // Initialize 2 of Clubs
        twoOfClubs = new Card("Clubs", "2", 0);

        // Create trick pile
        trick = new ArrayList<>();
    }

    public void printInstructions(){
        System.out.println("Welcome to the game of Hearts! The game is played over 13 tricks. The first trick must start " +
                "with the 2 of clubs, and the other players must play a club if they have one; if they don’t, they may " +
                "play any card. The player who plays the highest card of the suit that was led wins the trick.\n" +
                "\n" +
                "For every new trick, the player who won the previous one leads. They may play any suit except hearts, " +
                "unless hearts have been “broken” (which happens when a player has no cards of the led suit and chooses " +
                "to play a heart). All following players must play the same suit if possible; if not, they may play any " +
                "card.\n" +
                "\n" +
                "Your goal is to finish with the fewest points. Each heart is worth 1 point, and the Queen of Spades is " +
                "worth 13 points. However, if you choose to attempt “shooting the moon,” you must take all 13 hearts " +
                "plus the Queen of Spades. If you succeed, you win; if not, the player with the lowest score after all " +
                "13 tricks wins the game.");
    }

    // Print out a hand
    public void showHand(ArrayList<Card> hand){
        for (int i = 0; i < hand.size(); i++){
            System.out.println("Card" + i + ": " + hand.get(i));
        }
    }

    // Find the player who starts the game
    public Player playerWithTwoOfClubs(){
        for (int i = 0; i < 13;i++){
            if (player1.getHand().get(i).equals(twoOfClubs)){
                return player1;
            }
            if (player2.getHand().get(i).equals(twoOfClubs)){
                return player2;
            }
            if (player3.getHand().get(i).equals(twoOfClubs)){
                return player3;
            }
            if (player4.getHand().get(i).equals(twoOfClubs)){
                return player4;
            }
        }
        return null;
    }

    public Player nextPlayer(Player player){
        if (player.equals(player1)){
            return player2;
        }
        if (player.equals(player2)){
            return player3;
        }
        if (player.equals(player3)){
            return player4;
        }
        if (player.equals(player4)){
            return player1;
        }
        return null;
    }

    public void playRound(){

    }

    public void playGame(){
        // The player with the 2 of clubs starts the game
        System.out.println(playerWithTwoOfClubs().getName() + " starts with the 2 of clubs.");
        playerWithTwoOfClubs().removeCard(twoOfClubs);
        trick.add(twoOfClubs);
        taker = playerWithTwoOfClubs();
        current = nextPlayer(playerWithTwoOfClubs());
        // Next player sees hand and then plays
        System.out.println(current.toString());
        System.out.println("Pick your card (reminder: you have to play the same suit if you can!): ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        int spaceIndex = userInput.indexOf(" ");
        // Take user input and get the rank
        String rank = userInput.substring(0,spaceIndex);
        // Take user input and get the suit
        String suit = userInput.substring(spaceIndex);
        // Take the card they inputted and remove it from their hand and add to trick pile
        choice = new Card(suit, rank);
        int index = current.getHand().indexOf(choice);
        if (index != -1){
            Card cardFomHand = current.getHand().remove(index);
            // Notes: check if it's the same suit as previous suit played and if rank is higher
            // If it is make this player the taker
            trick.add(cardFomHand);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.printInstructions();
        game.playGame();
    }
}
