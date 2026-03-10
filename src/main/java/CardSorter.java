import java.util.Comparator;

public class CardSorter implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        // Order the suits by alphabetical order
        int suitCompare = c1.getSuit().compareTo(c2.getSuit());
        if (suitCompare != 0) {
            return suitCompare;
        }

        // Compared the values of the assigned order with the compareTo for Integer
        // Will never be equal; therefore, do not need to check for 0
        return Integer.compare(c1.getOrder(), c2.getOrder());
    }
}
