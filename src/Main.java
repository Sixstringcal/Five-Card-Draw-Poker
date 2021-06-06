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


        // Displays to the user the hand
        System.out.println("Your hand is:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("\t (" + (i + 1) + ") " + hand.get(i).printValue + " of " + hand.get(i).suite);
        }

        //Explains to the user how to play.
        System.out.println("Select the cards you would like to discard (example: \"1, 2, 4\").");
        System.out.println("Type \"hold\" to hold");



    }
}
