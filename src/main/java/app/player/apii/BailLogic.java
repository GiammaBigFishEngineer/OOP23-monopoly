package app.player.apii;

/**
 * Interface whose methods are used to manage the mechanism of bail.
 */
public interface BailLogic {

    /**
     * @param player
     * @return true if the player has payed the bail.
     */
    boolean hasPayed(Player player);
    /**
     * Method which indicates the Player hasn't payed to go out of prison. 
     * As a result, he stays in jail.
     * @param player who is currently in shift.
     */
    void notPayed(Player player);
}