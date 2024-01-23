package app.card.api;

import app.player.api.Player;

/**
 * An interface that represents a purchasable box.
 */
public interface Buyable extends Card {
    /**
     * @param 
     * @return price for buy one house
     */
    int getPrice();

    /**
     * @param 
     * @return if is owned
     */
    boolean isOwned();

    /**
     * @param player who need to check if he has got the property
     * @return price for build one house
     */
    boolean isOwnedByPlayer(Player player);

    /**
     * @param 
     * @return player who has got the property
     */
    Player getOwner();

    /**
     * @param 
     * @return price to pay to the owner
     */
    int getTransitFees();


    /**
     * @param player who buy property
     */
    void setOwner(Player player);
}
