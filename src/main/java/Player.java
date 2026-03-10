import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;


public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int points;
    private int num;

    public Player(String name){
        this.name = name;
        this.points = 0;
    }

    // Added num to the constructor, so I can easily know and access what number player is playing in game
    public Player(String name, int num){
        this.name = name;
        this.num = num;
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

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    // Remove a card from the players hand
    public void removeCard(Card card){
        for (int i = 0 ; i < this.hand.size(); i++){
            if (this.hand.get(i).equals(card)){
                this.hand.remove(i);
            }
        }
    }

    public void sortHand() {
        if (!this.hand.isEmpty()) {
            Collections.sort(this.hand, new CardSorter());
        }
    }

    public int getNum() {
        return num;
    }

    public String toString(){
        return this.name + " has " + this.points + " points\n" + this.name + "'s cards: " + this.hand;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return points == player.points && Objects.equals(name, player.name) && Objects.equals(hand, player.hand);
    }
}
