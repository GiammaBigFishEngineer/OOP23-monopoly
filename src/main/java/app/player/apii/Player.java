package app.player.apii;

import java.util.List;
import java.util.Optional;

import app.card.apii.Buildable;
import app.card.apii.Buyable;

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
     * @return Id of the player
     */
    int getID();

    /**
     * @param box
     */
    void buyBox(Buyable box);

    /**
     * @param box
     */
    void buildHouse(Buildable box);

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
     * @return number of houses built by the current Player
     */
    Optional<Integer> getHouseBuilt(Buildable built);
    /**
     * @param position of the Player on the table
     */
    void setPosition(int position);

    /**
     * @return true if there are changes in position, false otherwise
     */
    boolean hasPositionChanged();
}
