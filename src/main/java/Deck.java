import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] values){
        cards = new ArrayList<Card>();
        Card card;
        int points;
        for (int i = 0; i < suits.length; i++) {
            if (suits[i].equals("Hearts"))
            {
                points = 1;
            }
            else
            {
                points = 0;
            }
            for (int j = 0; j < ranks.length; j++) {
                // Queen of Spades card is worth 13 points
                if (suits[i].equals("Spades") && ranks[j].equals("Queen")){
                    points = 13;
                }
                card = new Card(suits[i], ranks[j], points);
                cards.add(card);
            }
        }

        cardsLeft = cards.size();
        shuffle();
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
        for (int i = (cardsLeft - 1); i > 0; i--){
            r = (int)(Math.random() * (i + 1));
            holder = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r, holder);
        }
    }
}
