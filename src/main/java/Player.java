import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int points;

    public Player(String name){
        this.name = name;
        this.points = 0;
    }

    public Player(String name, ArrayList<Card> hand){
        this.name = name;
        this.hand = hand;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int value){
        this.points += value;
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public String toString(){
        return this.name + " has " + this.points + " points\n" + this.name + "'s cards: " + this.hand;
    }
}
