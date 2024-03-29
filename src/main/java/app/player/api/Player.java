package app.player.api;

import java.util.List;
import java.util.Optional;

import app.card.api.Buildable;
import app.card.api.Buyable;
import app.card.api.Card;

import java.util.Map;

/**
 * Interface which models a Player.
 */
public interface Player {
    /**
     * @return currentPosition of the Player
     */
    int getCurrentPosition();

    /**
     * @param position of the Player on the table.
     */
    void setPosition(int position);

    /**
     * @return true if there are changes in position, false otherwise
     */
    boolean hasPositionChanged();

    /**
     * @return name of the Player
     */
    String getName();

    /**
     * @return ID of the player
     */
    int getID();

    /**
     * @return the String with color associated with the Player.
     */
    String getColor();

    /**
     * @return player's map which indicates the proprieties he owns
     *         and the number of houses built on a Card.
     */
    Map<Card, Optional<Integer>> getMap();

    /**
     * It sets the value of the map of the player.
     * 
     * @param map
     */
    void setMap(Map<Card, Optional<Integer>> map);

    /**
     * @return a boolean which indicates if a player is in jail
     */
    boolean isInJail();

    /**
     * @param isInJail
     */
    void setInJail(boolean isInJail);

    /**
     * @return player's bankAccount
     */
    BankAccount getBankAccount();

    /**
     * @return buyable boxes owned by the current Player
     */
    List<Buyable> getBuyableOwned();

    /**
     * @return buildable boxes owned by the current Player
     */
    List<Buildable> getBuildableOwned();

    /**
     * @param built
     * @return Optional<Integer> defining the number of houses built on the current
     *         box.
     */
    Optional<Integer> getHouseBuilt(Buildable built);

    /**
     * @return number of Stations owned by the current player
     */
    int getNumberStationOwned();

    /**
     * @param box whose type is Buyable
     * @return boolean which indicates if the player can buy the box.
     */
    boolean buyBox(Buyable box);

    /**
     * @param box whose type is Buildable
     * @return boolean which indicates if the operation was concluded or not.
     */
    boolean buildHouse(Buildable box);

    /**
     * @param box whose type is Buyable
     */
    void sellBuyable(Buyable box);

    /**
     * @param amount
     */
    void receivePayment(int amount);

    /**
     * @param player
     * @param amount
     * @return boolean
     */
    boolean payPlayer(Player player, int amount);

    /**
     * @param balance
     */
    void setBalance(int balance);
}
