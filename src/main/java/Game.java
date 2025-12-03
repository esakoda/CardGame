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
    private boolean heartsBroken;
    private String ledSuit;
    private String[] ranks;
    private Card takerCard;

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
        this.ranks = ranks;
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

        // Hearts have not been broken yet
        heartsBroken = false;
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

    // Get the value of each rank, so I can compare them (2 is the lowest, ace is the highest)
    public int getRankValue(String rank){
        for (int i = 0; i < ranks.length; i++){
            if (ranks[i].equals(rank)){
                return i;
            }
        }
        return -1;
    }

    // Play one trick (all 4 players place down cards)
    public void playRound(){
        // The player who won the last trick leads the next trick
        Player leader = taker;
        current = leader;

        // Play 4 cards
        for (int i = 0; i < 4; i++){
            System.out.println("\n" + current.getName() + "'s turn:");
            System.out.println(current.toString());

            Scanner scanner = new Scanner(System.in);
            boolean validPlay = false;

            // If the player inputs an invalid card
            while(!validPlay){
                System.out.println("Pick your card (format: rank suit):");
                String userInput = scanner.nextLine().trim();
                int spaceIndex = userInput.indexOf(" ");

                // If the user formats their input wrong
                if (spaceIndex == -1){
                    System.out.println("Invalid format! (format: rank suit)");
                    // Prevents error of Java trying to split a string with no space
                    continue;
                }

                // Take user input and get the rank
                String rank = userInput.substring(0, spaceIndex);
                // Take user input and get the suit
                String suit = userInput.substring(spaceIndex + 1);

                // Create the card they want to play
                choice = new Card(suit, rank);
                int index = current.getHand().indexOf(choice);

                // If the user doesn't have the card they wanted to place
                if (index == -1){
                    System.out.println("You don't have that card!");
                    // Take them back to the beginning of the loop so they input another card
                    continue;
                }

                // Get the actual card from their hand (has points)
                cardFromHand = current.getHand().get(index);

                // Check if it's a valid play
                if (i == 0){
                    // First player of the trick (leader)
                    // Can't lead hearts unless hearts have been broken
                    if (cardFromHand.getSuit().equals("Hearts") && !heartsBroken){
                        // Check if they have any non hearts they can lead with
                        boolean hasNonHearts = false;
                        for (int j = 0; j < current.getHand().size(); j++){
                            if (!current.getHand().get(j).getSuit().equals("Hearts")){
                                hasNonHearts = true;
                                break;
                            }
                        }
                        if (hasNonHearts){
                            System.out.println("You can't lead hearts until hearts are broken!");
                            continue;
                        }
                        // If they only have hearts, then they can play hearts
                    }
                    //
                    ledSuit = cardFromHand.getSuit();
                    validPlay = true;
                }
                else {
                    // Not the first player
                    // Must follow the suit the leader picked, if possible
                    if (cardFromHand.getSuit().equals(ledSuit)){
                        validPlay = true;
                    }
                    else {
                        // Check if they have any cards of the led suit
                        boolean hasLedSuit = false;
                        for (int j = 0; j < current.getHand().size(); j++){
                            if (current.getHand().get(j).getSuit().equals(ledSuit)){
                                hasLedSuit = true;
                                break;
                            }
                        }
                        if (hasLedSuit){
                            System.out.println("You must place the same suit as the first card placed if you can!");
                            continue;
                        }
                        else {
                            // If they don't have the led suit, they can play anything
                            // Check if they played a heart because if so, hearts are now broken
                            if (cardFromHand.getSuit().equals("Hearts")){
                                heartsBroken = true;
                            }
                            validPlay = true;
                        }
                    }
                }
                // If valid play, remove hand and add to trick
                if (validPlay){
                    current.getHand().remove(index);
                    trick.add(cardFromHand);
                    System.out.println(current.getName() + " plays " + cardFromHand);

                    // Check if this player wins the trick
                    if ( i == 0){
                        // First player will take trick if no one places higher
                        taker = current;
                        takerCard = cardFromHand;
                    }
                    else {
                        // If another player places a card that is the same suit as led suit and is a higher rank
                        // They become the taker
                        if(cardFromHand.getSuit().equals(ledSuit)){
                            if (getRankValue(cardFromHand.getRank()) > getRankValue(takerCard.getRank())){
                                taker = current;
                                takerCard = cardFromHand;
                            }
                        }
                    }
                }
            }

            // Move to next player
            current = nextPlayer(current);
        }

        // After all 4 players have played, taker takes the trick
        System.out.println("\n" + taker.getName() + " takes the trick!");

        // Add up points from this trick
        int trickPoints = 0;
        for (int i = 0; i <trick.size(); i++) {
            trickPoints += trick.get(i).getValue();
        }

        // Give the points from the trick to the taker
        if (trickPoints > 0) {
            System.out.println(taker.getName() + " takes " + trickPoints + " points.");
            taker.addPoints(trickPoints);
        }

        // Clear the trick for next round
        while (trick.size() > 0) {
            trick.remove(0);
        }
    }

    public void playGame(){
        // The player with the 2 of clubs starts the game
        System.out.println(playerWithTwoOfClubs().getName() + " starts with the 2 of clubs.");
        playerWithTwoOfClubs().removeCard(twoOfClubs);
        trick.add(twoOfClubs);
        taker = playerWithTwoOfClubs();
        current = nextPlayer(playerWithTwoOfClubs());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.printInstructions();
        game.playGame();
    }
}
