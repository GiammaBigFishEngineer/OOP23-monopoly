package app.card.api;

/**
 * this is an Adapter pattern for adapt Cards.
 */
public interface CardAdapter {

    /**
     * @return buildable type card
     */
    default Buildable asBuildable() {
        if (!(this instanceof Buildable)) {
            throw new IllegalArgumentException("Card passed is not Buildable");
        }
        return (Buildable) this;
    }

    /**
     * @return Buyable type card
     */
    default Buyable asBuyable() {
        if (!(this instanceof Buyable)) {
            throw new IllegalArgumentException("Card passed is not Buyable");
        }
        return (Buyable) this;
    }

    /**
     * @return Unbuyable type card
     */
    default Unbuyable asUnbuyable() {
        if (!(this instanceof Unbuyable)) {
            throw new IllegalArgumentException("Card passed is not Unbuyable");
        }
        return (Unbuyable) this;
    }
}
