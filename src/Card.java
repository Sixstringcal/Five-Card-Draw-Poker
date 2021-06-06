public class Card {

    /**
     * The suite the card is a part of
     */
    public String suite;

    /**
     * The numerical value of the card
     */
    public int value;

    /**
     * The value the user sees for the card
     */
    public String printValue;

    /**
     * Initializes the Card
     *
     * @param suite - the suite the card is in (hearts, clubs, spades, or diamonds)
     * @param value - the numerical value of the card
     */
    public Card(String suite, int value) {
        this.suite = suite;
        this.value = value;

        // Creates a user-friendly "print value" that will be
        // used to convey to the user what value their card is
        switch (value) {
            case 1:
                printValue = "ace";
                break;
            case 11:
                printValue = "jack";
                break;
            case 12:
                printValue = "queen";
                break;
            case 13:
                printValue = "king";
                break;
            default:
                printValue = value + "";
        }
    }
}
