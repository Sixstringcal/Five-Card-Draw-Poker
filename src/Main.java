import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Creates the deck of cards
        LinkedList<Card> deck = new LinkedList<>();

        // Creates the user's hand
        LinkedList<Card> hand = new LinkedList<>();

        // Scanner to read the input from the user
        Scanner scanner = new Scanner(System.in);

        // Creates the random number generator
        Random rng = new Random();

        // Iterates through all card values for each suite,
        // populating the deck with all 52 cards
        for (int i = 1; i < 14; i++) {
            deck.add(new Card("hearts", i));
            deck.add(new Card("diamonds", i));
            deck.add(new Card("spades", i));
            deck.add(new Card("clubs", i));
        }

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

        //Explains to the user how to play
        System.out.println("Select the cards you would like to discard (example: \"1, 2, 4\").");
        System.out.println("Type \"hold\" to hold");

        // Reads and deciphers the user's input
        String input = scanner.nextLine();
        if (!input.toLowerCase().equals("hold")) {
            try {
                String[] split = input.split(",");
                // Hash set used to deal with duplicates.
                HashSet<Integer> toSwap = new HashSet<>();

                // Gets the index of each card to be swapped
                for (int i = 0; i < split.length; i++) {
                    toSwap.add(Integer.parseInt(split[i].trim()) - 1);
                }

                // Return the cards from the hand to the deck
                for (int temp : toSwap) {
                    deck.add(hand.get(temp));
                }

                // Replaces the cards in the hand
                for (int temp : toSwap) {
                    hand.set(temp, deck.remove(rng.nextInt(deck.size())));
                }

            } catch (Exception e) {
                System.out.println("Invalid input");
            }

        }

        // Displays to the user the new hand
        System.out.println("Your new hand is:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("\t" + hand.get(i).printValue + " of " + hand.get(i).suite);
        }
    }
}
