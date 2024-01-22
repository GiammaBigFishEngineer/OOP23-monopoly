package app.player.api;

import app.card.API.Buildable;
import app.card.API.Buyable;
import java.util.List;

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
    int getId();

    /**
     * @param box
     * @throws AlreadyBuyedBoxException current box is already owned by someone else
     * @throws NotEnoughMoneyException player doesn't have enough money for buying a box
     */
    void buyBox(Buyable box) throws AlreadyBuyedBoxException, NotEnoughMoneyException;

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
