import javax.swing.*;
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values, int[] order){
        cards = new ArrayList<Card>();
        int points;
        int num = 0;
        for (int i = 0; i < ranks.length; i++){
            for (int j = 0; j < suits.length; j++){
                // All hearts are worth 1 point
                if (suits[j].equals("Hearts")){
                    points = 1;
                }
                // Queen of Spades is worth 13 points
                else if (suits[j].equals("Spades") && ranks[i].equals("Queen")){
                    points = 13;
                }
                else {
                    points = 0;
                }
                num++;
                cards.add(new Card(suits[j], ranks[i], points, order[i], new ImageIcon("src/main/resources/" + num +".png").getImage()));
            }
        }
        cardsLeft = cards.size();
        shuffle();
    }

    public boolean isEmpty(){
        return (cardsLeft == 0);
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    public Card deal(){
        if (isEmpty() == true){
            return null;
        }

        cardsLeft--;
        return cards.get(cardsLeft);
    }

    public void shuffle(){
        int r;
        Card holder;
        for (int i = (cardsLeft - 1); i > 0; i--){
            r = (int)(Math.random() * (i + 1));
            holder = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r, holder);
        }
    }
}
