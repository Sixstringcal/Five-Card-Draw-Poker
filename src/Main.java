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
        boolean notAccepted = true;
        while (notAccepted) {
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
                    notAccepted = false;
                }
                // Happens when the user inputs something that isn't recognized.
                catch (Exception e) {
                    System.out.println("Invalid input.  Try again.");
                }
            } else {
                notAccepted = false;
            }

        }

        // Displays to the user the new hand
        System.out.println("Your new hand is:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("\t" + hand.get(i).printValue + " of " + hand.get(i).suite);
        }

        // Informs the user what their score is
        System.out.println("You got a " + scoreHand(hand) + "!");
    }

    public static String scoreHand(LinkedList<Card> hand) {
        LinkedList<Integer> handValues = getValues(hand);

        // Checks for royal flush
        if (allSameSuite(hand) && containsValues(handValues, new int[]{1, 10, 11, 12, 13})) {
            return "royal flush";
        }

        // Checks for a straight flush
        else if (allSameSuite(hand) && checkForStraight(handValues)) {
            return "straight flush";
        }

        // Checks for a four-of-a-kind
        else if (isNOfAKind(handValues, 4)) {
            return "four-of-a-kind";
        }

        // Checks for a full house
        else if (isFullHouse(handValues)) {
            return "full house";
        }

        // Checks for a flush
        else if (allSameSuite(hand)) {
            return "flush";
        }

        // Checks for a straight
        else if (checkForStraight(handValues)) {
            return "straight";
        }

        // Checks for three-of-a-kind
        else if (isNOfAKind(handValues, 3)) {
            return "three-of-a-kind";
        }

        // Checks for a two-pair
        else if (isTwoPair(handValues)) {
            return "two-pair";
        }

        // Checks for a pair
        else if (isNOfAKind(handValues, 2)) {
            return "pair";
        }

        // If it matches none of the others, it returns an "X" high
        return getMax(handValues) + "-high";
    }

    /**
     * Checks that all cards in a set are the same suite
     *
     * @param hand - the set of cards to check their suite.
     * @return - true if all cards are the same suite.  False otherwise
     */
    public static boolean allSameSuite(LinkedList<Card> hand) {
        String firstSuite = hand.getFirst().suite;
        for (Card temp : hand) {
            if (!temp.suite.equals(firstSuite)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets a LinkedList of all of the values of the hand
     *
     * @param hand - the hand of cards
     * @return - the values of all of the cards in the form of a LinkedList
     */
    public static LinkedList<Integer> getValues(LinkedList<Card> hand) {
        LinkedList<Integer> values = new LinkedList<>();
        for (Card temp : hand) {
            values.add(temp.value);
        }
        return values;
    }

    /**
     * Checks if every value from values is contained in cardValues
     *
     * @param cardValues - the values of the cards in this set
     * @param values     - the values you want to check if the cards have
     * @return - true if every value is present in cardValues.  False otherwise
     */
    public static boolean containsValues(LinkedList<Integer> cardValues, int[] values) {
        for (int temp : values) {
            if (!cardValues.contains(temp)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if there is any straight sequence
     *
     * @param cardValues - the values of each card
     * @return - true if there is a straight.  False otherwise
     */
    public static boolean checkForStraight(LinkedList<Integer> cardValues) {
        if (containsValues(cardValues, new int[]{1, 10, 11, 12, 13})) {
            return true;
        }
        for (int i = 1; i < 10; i++) {
            if (containsValues(cardValues, new int[]{i, i + 1, i + 2, i + 3, i + 4})) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if it is an "n" of a kind
     *
     * @param cardValues - the values of the cards
     * @param n          - the number of kind it is.  For example "four of a kind"
     *                   would be n = 4
     * @return - true if it is a "n" of a kind.  False otherwise
     */
    public static boolean isNOfAKind(LinkedList<Integer> cardValues, int n) {
        for (int i = 0; i + n <= cardValues.size(); i++) {
            int total = 1;
            for (int j = i + 1; j < cardValues.size(); j++) {
                if (cardValues.get(i) == cardValues.get(j)) {
                    total++;
                }
            }
            if (total >= n) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the cards make a full house.  It is assumed that it is not a
     * four-of-a-kind, as that would score higher.  Because of that, if there
     * are only two unique values, it is a full house.
     *
     * @param cardValues - the values of the cards
     * @return - true if it is a full house.  False otherwise
     */
    public static boolean isFullHouse(LinkedList<Integer> cardValues) {
        int value1 = cardValues.getFirst();
        int value2 = -1;
        for (int i = 1; i < cardValues.size(); i++) {
            if (value1 != cardValues.get(i)) {
                if (value2 == -1) {
                    value2 = cardValues.get(i);
                } else if (value2 != cardValues.get(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the hand is a two-pair.  Since full house, four-of-a-kind, and
     * three-of-a-kind have already been checked (as they're higher value),
     * if there are no more than 3 unique values, it is a two-pair
     *
     * @param cardValues - the values of the cards in the hand
     * @return - true if it is a two-pair.  False otherwise
     */
    public static boolean isTwoPair(LinkedList<Integer> cardValues) {
        int value1 = cardValues.getFirst();
        int value2 = -1;
        int value3 = -1;
        for (int i = 1; i < cardValues.size(); i++) {
            if (value1 != cardValues.get(i)) {
                if (value2 == -1) {
                    value2 = cardValues.get(i);
                } else if (value2 != cardValues.get(i)) {
                    if (value3 == -1) {
                        value3 = cardValues.get(i);
                    } else if (value3 != cardValues.get(i)) {
                        return false;

                    }
                }
            }
        }
        return true;
    }


    /**
     * Gets the maximum value in cardValues
     *
     * @param cardValues - the values of the cards in the hand
     * @return - the maximum value in cardValues
     */
    public static String getMax(LinkedList<Integer> cardValues) {
        int max = 0;
        for (int temp : cardValues) {
            if (temp > max) {
                max = temp;
            }
        }
        switch (max) {
            case 1:
                return "ace";
            case 11:
                return "jack";
            case 12:
                return "queen";
            case 13:
                return "king";
            default:
                return max + "";
        }
    }


}
