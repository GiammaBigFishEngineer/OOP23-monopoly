package app.player.apii;

import app.card.apii.Buildable;
import app.card.apii.Buyable;

import java.io.Serializable;
import java.util.List;

/**
 * Interface which models a Player. 
 */
public interface Player extends Serializable {

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
    int getId();

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
     * @return buildable boxes owned by the current Player
     */
    List<Buyable> getBuildableOwned();

    /**
     * @param box 
     */
    void sellBuyable(Buyable box); 

    /**
     * @param built
     * @return number of houses built by the current Player
     */
    int getHouseBuilt(Buildable built); 

    /**
     * @param position of the Player on the TABELLONE CONTROLLA COME CHIAMARLO
     */
    void setPosition(int position);
}
