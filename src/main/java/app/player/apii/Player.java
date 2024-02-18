package app.player.apii;

import java.util.List;
import java.util.Optional;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;

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
     * @return player's map which indicates the proprieties he owns
     *         and the number of houses built on a Card.
     */
    Map<Card, Optional<Integer>> getMap();

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
     * @param box
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
     * As the method getBankAccount() returns a defensive copy of the bankAccount of
     * the player,
     * only the player can modify this object.
     * Who uses the method getBankAccount() would modify the copy of the account:
     * as a result, I have created the following methods which effectively modify
     * the player's account.
     * 
     * @param amount
     */
    void receivePayment(int amount);

    boolean payPlayer(final Player player, final int amount);

    String getColor();

    void setMap(final Map<Card, Optional<Integer>> map);

}
