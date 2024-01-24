package app.card.apii;

/**
 * this is an Adapter pattern for adapt Cards.
 */
public interface CardAdapter {

    /**
     * @param card is the card to adapt
     * @return buildable type card
     */
    static Buildable buildableAdapter(Card card) {
        if (!(card instanceof Buildable)) {
            throw new IllegalArgumentException("Card passed is not Buildable");
        }
        return (Buildable) card;
    }

    /**
     * @param card is the card to adapt
     * @return Buyable type card
     */
    static Buyable buyableAdapter(Card card) {
        if (!(card instanceof Buyable)) {
            throw new IllegalArgumentException("Card passed is not Buyable");
        }
        return (Buyable) card;
    }

    /**
     * @param card is the card to adapt
     * @return Unbuyable type card
     */
    static Unbuyable unbuyableAdapter(Card card) {
        if (!(card instanceof Unbuyable)) {
            throw new IllegalArgumentException("Card passed is not Unbuyable");
        }
        return (Unbuyable) card;
    }

}
