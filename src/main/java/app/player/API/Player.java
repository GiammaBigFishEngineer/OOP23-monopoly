package app.player.api;

import app.card.api.Buildable;
import app.card.api.Buyable;

import java.util.List;
import java.util.Optional;

/**
 * Interface which models a Player. 
 */
public interface Player {

    /**
     * @return currentPosition of the Player
     */
    int getCurrentPosition();
    /**
     * @return name of the Player
     */
    String getName();
    /**
     * @return ID of the player
     */
    int getID();
    /**
     * @param box
     */
    void buyBox(Buyable box);
    /**
     * @param box
     * @throws IllegalArgumentException
     */
    void buildHouse(Buildable box) throws IllegalArgumentException;
    /**
     * @return number of Stations owned by the current player
     */
    int getNumberStationOwned();
    /**
     * @return information about the player's bankAccount
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
     * @param box 
     */
    void sellBuyable(Buyable box);
    /**
     * @param built
     * @return Optional<Integer> defining the number of houses built on the current box.
     */
    Optional<Integer> getHouseBuilt(Buildable built);
    /**
     * @param position of the Player on the table.
     */
    void setPosition(int position);
    /**
     * @return a boolean which indicates if a player is in jail
     */
    boolean isInJail();
    /**
     * @param isInJail
     */
    void setInJail(boolean isInJail);
}
