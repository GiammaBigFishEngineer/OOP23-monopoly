package app.player.api;

/**
 * Interface whose method is used to manage the mechanism of bail.
 */
public interface BailLogic {

    /**
     * @param player
     * @return true if the player has payed the bail.
     */
    boolean hasPayed(Player player);
}
