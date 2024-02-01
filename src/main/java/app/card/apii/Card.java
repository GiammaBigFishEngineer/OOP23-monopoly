package app.card.apii;

import java.io.Serializable;

/**
 * An interface that represents each individual box.
 */
public interface Card extends Serializable {
    /**
     * @param 
     * @return name of card
     */
    String getName();

    /**
     * @param 
     * @return id of card on table
     */
    int getId();

    /**
     * @param 
     * @return true if card object is type Buildable
     */
    default boolean isBuildable() {
        return this instanceof Buildable;
    }

    /**
     * @param 
     * @return true if card object is type Buyable
     */
    default boolean isBuyable() {
        return this instanceof Buyable;
    }

    /**
     * @param 
     * @return true if card object is type Unbuyable
     */
    default boolean isUnbuyable() {
        return this instanceof Unbuyable;
    }
}
