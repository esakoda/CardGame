import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Player[] players;
    private Deck deck;
    private ArrayList<Card> trick;
    private Player taker;
    private Player current;
    private Card choice;
    private Card cardFromHand;
    private boolean heartsBroken;
    private String ledSuit;
    private String[] ranks;
    private Card takerCard;
    private static final int NUM_PLAYERS = 4;
    private static final int POINTS_MOON = 26;
    private static final Card twoOfClubs = new Card("Clubs", "2", 0);
    private static final String INSTRUCTIONS = "Welcome to the game of Hearts! The game is played over 13 tricks. The first trick must start " +
            "with the 2 of clubs, \nand the other players must play a club if they have one; if they don’t, they may " +
            "play any card. \nThe player who plays the highest card of the suit that was led wins the trick.\n" +
            "\n" +
            "For every new trick, the player who won the previous one leads. They may play any suit except hearts, \n" +
            "unless hearts have been “broken” (which happens when a player has no cards of the led suit and \nchooses " +
            "to play a heart). All following players must play the same suit if possible; if not, they may play any " +
            "card.\n" +
            "\n" +
            "Your goal is to finish with the fewest points. Each heart is worth 1 point, and the Queen of Spades is " +
            "worth 13 points.\nHowever, if you choose to attempt “shooting the moon,” you must take all 13 hearts " +
            "plus the Queen of Spades. \nIf you succeed, you win; if not, the player with the lowest score after all " +
            "13 tricks wins the game.";
    private GameView window;
    // State tracker variables
    public static final int STATE_TITLE = 0;
    public static final int STATE_INSTR = 1;
    public static final int STATE_GAME = 2;
    public static final int STATE_END = 3;
    private int state;

    public Game(){
        // Set up frontend
        this.window = new GameView(this);
        // Show the title screen
        state = STATE_TITLE;
        window.repaint();
        // Get the name of each player
        Scanner scanner = new Scanner(System.in);
        String[] names = new String[4];
        for (int i = 1; i < 5; i++) {
            System.out.println("Player " + i + ": ");
            names[i - 1] = scanner.nextLine();
        }
        // Create main deck of cards
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        this.ranks = ranks;
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        int[] values = {0, 1, 13};
        deck = new Deck(ranks, suits, values);

        players = new Player[NUM_PLAYERS];

        // Create 4 Players
        for (int i = 0; i < NUM_PLAYERS; i++){
            players[i] = new Player(names[i], i);
        }

        // Deal the deck out to each player
        for (int i = 0; i < NUM_PLAYERS; i++){
            players[i].setHand(new ArrayList<Card>());
        }

        int cardsPerPlayer = (deck.getCardsLeft() / 4);
        for (int i = 0; i < cardsPerPlayer; i++)
        {
            for (int j = 0; j < NUM_PLAYERS; j++){
                players[j].getHand().add(deck.deal());
            }
        }

        // Create trick pile
        trick = new ArrayList<>();

        // Hearts have not been broken yet
        heartsBroken = false;
    }

    public void printInstructions(){
        // Display instructions on frontend
        state = STATE_INSTR;
        window.repaint();
        System.out.println(INSTRUCTIONS);
    }

    // Print out a hand
    public void showHand(ArrayList<Card> hand){
        for (int i = 0; i < hand.size(); i++){
            System.out.println("Card" + i + ": " + hand.get(i));
        }
    }

    // Find the player who starts the game
    public Player playerWithTwoOfClubs(){
        for (int i = 0; i < NUM_PLAYERS; i++){
            for (int j = 0; j < players[i].getHand().size(); j++){
                if (players[i].getHand().get(j).equals(twoOfClubs)){
                    return players[i];
                };
            }
        }
        return null;
    }

    // Get the next Player based on previous
    public Player nextPlayer(Player player){
        if (player.getNum() == 3){
            return players[0];
        }
        else{
            return players[(player.getNum() + 1)];
        }
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

    // Getters for GameView
    public int getState(){
        return state;
    }

    public String getInstructions(){
        return INSTRUCTIONS;
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
        // Allow the user to read the instructions before switching the screen to the game
        String ans;
        Scanner input = new Scanner(System.in);
        System.out.println("Press return to start the game!");
        ans = input.nextLine();
        // Show game screen
        state = STATE_GAME;
        window.repaint();
        // The player with the 2 of clubs starts the game
        Player starter = playerWithTwoOfClubs();
        System.out.println("\n" + starter.getName() + " starts with the 2 of clubs.");
        starter.removeCard(twoOfClubs);
        trick.add(twoOfClubs);
        taker = starter;
        takerCard = twoOfClubs;
        ledSuit = "Clubs";
        current = nextPlayer(starter);

        // Other 3 players play their first card
        for (int i = 0; i < 3; i++){
            System.out.println("\n" + current.getName() + "'s turn:");
            System.out.println(current.toString());

            Scanner scanner = new Scanner(System.in);
            boolean validPlay = false;

            // If the player inputs an invalid card
            while(!validPlay) {
                System.out.println("Pick your card (format: rank suit):");
                String userInput = scanner.nextLine().trim();
                int spaceIndex = userInput.indexOf(" ");

                // If the user formats their input wrong
                if (spaceIndex == -1) {
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
                if (index == -1) {
                    System.out.println("You don't have that card!");
                    // Take them back to the beginning of the loop so they input another card
                    continue;
                }

                // Get the actual card from their hand (has points)
                cardFromHand = current.getHand().get(index);

                // Check if they are following Clubs
                if (cardFromHand.getSuit().equals("Clubs")) {
                    validPlay = true;
                } else {
                    // Check if they have any clubs
                    boolean hasClubs = false;
                    for (int j = 0; j < current.getHand().size(); j++) {
                        if (current.getHand().get(j).getSuit().equals("Clubs")) {
                            hasClubs = true;
                            break;
                        }
                    }
                    if (hasClubs) {
                        System.out.println("You must play clubs if you have one!");
                        continue;
                    } else {
                        // If they don't have clubs they can play anything
                        // Check if they break hearts
                        if (cardFromHand.getSuit().equals("Hearts")) {
                            heartsBroken = true;
                        }
                        validPlay = true;
                    }
                }
                if (validPlay) {
                    current.getHand().remove(index);
                    trick.add(cardFromHand);
                    System.out.println(current.getName() + " plays " + cardFromHand);

                    // Check if it's the same suit and higher rank, if so this player becomes the taker
                    if (cardFromHand.getSuit().equals("Clubs")) {
                        if (getRankValue(cardFromHand.getRank()) > getRankValue(takerCard.getRank())) {
                            taker = current;
                            takerCard = cardFromHand;
                        }
                    }
                }
            }
            current = nextPlayer(current);
        }
        // First trick is over
        System.out.println("\n" + taker.getName() + " wins the first trick!");
        // Calculate how many points the taker of the trick takes
        int trickPoints = 0;
        for(int i = 0; i < trick.size(); i++){
            trickPoints += trick.get(i).getValue();
        }
        if (trickPoints > 0){
            System.out.println(taker.getName() + " takes " + trickPoints + " points.");
            taker.addPoints(trickPoints);
        }

        // Clear trick
        while (trick.size() > 0){
            trick.remove(0);
        }

        // PLay remaining 12 tricks
        for(int round = 2; round <= 13; round++){
            System.out.println("\n---------- Trick " + round + " ----------");
            playRound();
        }

        // Game over - count up points and determine the winner
        System.out.println("\n---------- GAME OVER ----------");
        System.out.println("\nFinal Scores:");
        for (int i = 0; i < NUM_PLAYERS; i++){
            System.out.println(players[i].getName() + ": " + players[i].getPoints() + " points");
        }

        // Check if someone shot the moon
        boolean moonShot = false;
        for (int i = 0; i < NUM_PLAYERS; i++){
            if (players[i].getPoints() == POINTS_MOON){
                System.out.println("\n" + players[i].getName() + " shot the moon and wins!!");
                moonShot = true;
                break;
            }
        }

        if (!moonShot){
            Player winner = players[0];
            for (int j = 1; j < NUM_PLAYERS; j++){
                if (players[j].getPoints() < winner.getPoints()){
                    winner = players[j];
                }
            }
            System.out.println("\n" + winner.getName() + " wins with the lowest score!");
        }

        // Show end screen
        state = STATE_END;
        window.repaint();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.printInstructions();
        game.playGame();
    }
}
