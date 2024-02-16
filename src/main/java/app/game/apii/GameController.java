package app.game.apii;

import app.card.apii.Card;
import app.game.view.BtnCodeEnum;
import app.player.apii.Player;

import java.util.*;

/**
 * An interface that rapresent the logic of the player actions.
 */

public interface GameController {
    /**
     * Roll dice.
     */
    Integer rollDice(Boolean b);

    /**
     * @param currentPlayer that want to buy the propriety.
     */
    boolean buyPropriety();

    /**
     * @param currentPlayer that want to build an house.
     */
    boolean buildHouse();

    /**
     * @param currentPlayer that want to sell the propriety.
     */
    void sellPropriety();

    /**
     * @param currentPlayer that need to pay the fees
     */
    void payFees(Player owner);

    void newTurn();

    void startTurn();

    void handleCard();

    Boolean isCurrentPlayerInJail();

    void tryLuckyBail();

    Map<BtnCodeEnum, Boolean> getBtnStatus();

    void enableSingleButton(BtnCodeEnum code);

    void disableSingleButton(BtnCodeEnum code);

    boolean isOver();

    void defeatPlayer();

    void initializePlayer();

    // get

    Player getCurrentPlayer();

    Integer getDiceValue();

    Card getCurrentCard();

    List<Card> getTableList();

    List<Card> getCardList();

    List<Player> getPlayerList();

    List<Player> getDefeatedList();

    // set

    void setDiceValue(int value);

}
