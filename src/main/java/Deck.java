import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values){
        cards = new ArrayList<Card>();
        Card card;
        for (int i = 0; i < ranks.length; i++){
            card = new Card(suits[i], ranks[i], values[i]);
            cards.add(card);
        }

        cardsLeft = cards.size();
    }

    public boolean isEmpty(){
        if (cardsLeft == 0){
            return true;
        }
        else {
            return false;
        }
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
        for (int i = cardsLeft; i > 0; i--){
            r = (int)(Math.random() * (i + 1));
            holder = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r, holder);
        }
    }
}
