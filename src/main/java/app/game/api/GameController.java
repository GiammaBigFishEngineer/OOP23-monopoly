package app.game.api;

import app.card.api.Card;
import app.game.utils.BtnCodeEnum;
import app.game.utils.BtnCodeState;
import app.game.utils.Dice;
import app.player.api.Player;

import java.util.List;
import java.util.Map;

/**
 * An interface that rapresent the logic of the game.
 */

public interface GameController {
    /**
     * @param b is true if we want to start the turn after rolling the dice, false.
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
     * this method is used to sell the propriety on which the owner is positioned.
     */
    void sellPropriety();

    /**
     * this method is called if the current player has landed on an unbuyable card
     * and in particular if he is on an unforseen.
     * It acts differently by checking whether the unforseen concerns movement or
     * payment.
     */
    void pickUnforseen();

    /**
     * this method is used at the end of every turn and at the start of the game,
     * in.
     * fact it serve to move on to the next player in the players list
     */
    void newTurn();

    /**
     * this method is used to calculate the new player position and the card he has.
     * landed on.
     * If the player passes over the GO card, he earns an amount of money
     */
    void startTurn();

    /**
     * @return true if current player is in jail
     */
    Boolean isCurrentPlayerInJail();

    /**
     * this method is called if the current player decides to pay the bail.
     */

    void hasPayedBail();

    /**
     * this method is called when the current player is in jail and he refuses to.
     * pay the bail, in fact he can try his luck and in case two dice returnthha
     * same value he can get out of jail
     */
    void tryLuckyBail();

    /**
     * @return a map where the key is a code that identifies a button and.
     *         value can be ENABLED if the button it corrisponds to is enabled,
     *         DISABLED otherwhise
     */
    Map<BtnCodeEnum, BtnCodeState> getBtnStatus();

    /**
     * @param code is the code of the button we want to enable
     */
    void enableSingleButton(BtnCodeEnum code);

    /**
     * @param code is the code of the button we want to disable
     */
    void disableSingleButton(BtnCodeEnum code);

    /**
     * this method is used to set every value in the button list to false.
     */
    void disableAllBtn();

    /**
     * @return true if the player list contains only one player, false otherwise
     */
    boolean isOver();

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
     * @return a defensive copy of the sorted list, which is the same of table list.
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

    /**
     * @return true if the current player has been eliminated, false otherwise
     */
    boolean isCurrentPlayerDefeated();

    /**
     * @return true if the current player has landed on an unforseen, false.
     *         otherwise
     */
    boolean isCurrentPlayerOnUnforseen();

    /**
     * @return the message corresponding to the unforseen
     */
    String getUnforseenMessage();

    /**
     * @return true if the current player has landed on an owned propriety, false.
     *         otherwise
     */
    boolean isCurrentPlayerOnOwnedPropriety();

    /**
     * @return the name of the owner of the propriety on which the current player.
     *         landed
     */
    String getOwner();

    /**
     * this method is used to save the game.
     */
    void saveGame();

    /**
     * this method is used to quit the game.
     */
    void quitGame();

}
