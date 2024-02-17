package app.game.apii;

import app.card.apii.Card;
import app.game.utils.Dice;
import app.game.view.BtnCodeEnum;
import app.player.apii.Player;

import java.util.List;
import java.util.Map;

/**
 * An interface that rapresent the logic of the player actions.
 */

public interface GameController {
    /**
     * @param b is true if we want to start the turn after rolling the dice, false
     *          otherwise
     */
    void rollDice(Boolean b);

    /**
     * @return true if the player buys correctly one propriety, false otherwise
     */
    boolean buyPropriety();

    /**
     * @return true if the player builds correctly one house, false otherwise
     */
    boolean buildHouse();

    /**
     * this method is used to sell the propriety on which the owner is positioned
     */
    void sellPropriety();

    /**
     * @param owner is the player who own the propriety and who will receive the
     *              payment
     */
    void payFees(Player owner);

    /**
     * 
     */
    void newTurn();

    /**
     * 
     */
    void startTurn();

    /**
     * 
     */
    void handleCard();

    /**
     * 
     */
    Boolean isCurrentPlayerInJail();

    /**
     * 
     */
    void tryLuckyBail();

    /**
     * 
     */
    Map<BtnCodeEnum, Boolean> getBtnStatus();

    /**
     * @param code is the code of the button we want to enable
     */
    void enableSingleButton(BtnCodeEnum code);

    /**
     * @param code is the code of the button we want to disable
     */
    void disableSingleButton(BtnCodeEnum code);

    /**
     * this method check if the player list contains only one player
     */
    boolean isOver();

    /**
     * this method remove one player from the player list and add him in the
     * defeated player list
     */
    void defeatPlayer();

    /**
     * 
     */
    void initializePlayer();

    /**
     * @return a defensive copy of the current player in the game
     */
    Player getCurrentPlayer();

    /**
     * @return a defensive copy of the current dice in the game
     */
    Dice getDice();

    /**
     * @return a defensive copy of the card on which the current player is placed
     */
    Card getCurrentCard();

    /**
     * @return a defensive copy of the table list
     */
    List<Card> getTableList();

    /**
     * @return a defensive copy of the sorted list, which is the same of table list
     *         but sortet by card id
     */
    List<Card> getCardList();

    /**
     * @return a defensive copy of the players list
     */
    List<Player> getPlayerList();

    /**
     * @return a defensive copy of the defeated players list
     */
    List<Player> getDefeatedList();

    /**
     * @param value is the total value we want the dice to take
     */
    void setDiceValue(int value);

    boolean isCurrentPlayerDefeated();

    boolean isCurrentPlayerOnUnforseen();

    String getUnforseenMessage();

    boolean isCurrentPlayerOnOwnedPropriety();

    String getOwner();

    void endGame();

}
