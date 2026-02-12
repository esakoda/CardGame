import java.awt.*;
import java.util.Objects;

public class Card {
    private String suit;
    private String rank;
    private int value;
    private Image image;

    // Images are arranged in Spades, Hearts, Diamonds, Clubs --- Ace through King

    // Constructor with all instance variables
    public Card(String suit, String rank, int value, Image image){
        this.suit = suit;
        this.rank = rank;
        this.value= value;
        this.image = image;
    }

    // Constructor with no image - easier to create cards in Game
    public Card(String suit, String rank, int value){
        this.suit = suit;
        this.rank = rank;
        this.value= value;
    }

    // Added constructor without value because the user won't input the value of their card
    public Card(String suit, String rank){
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString(){
        return this.rank + " of " + this.suit;
    }

    public Image getImage(){
        return this.image;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(suit, card.suit) && Objects.equals(rank, card.rank);
    }
}
