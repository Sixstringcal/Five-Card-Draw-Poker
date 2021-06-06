import java.util.LinkedList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // Creates the deck of cards
        LinkedList<Card> deck = new LinkedList<>();

        // Creates the user's hand
        LinkedList<Card> hand = new LinkedList<>();

        // Iterates through all card values for each suite,
        // populating the deck with all 52 cards
        for (int i = 1; i < 14; i++) {
            deck.add(new Card("hearts", i));
            deck.add(new Card("diamonds", i));
            deck.add(new Card("spades", i));
            deck.add(new Card("clubs", i));
        }

        // Creates the random number generator
        Random rng = new Random();

        // Takes the cards from the deck and places them in
        // the user's hand
        for (int i = 0; i < 5; i++) {
            hand.add(deck.remove(rng.nextInt(deck.size())));
        }


    }
}
