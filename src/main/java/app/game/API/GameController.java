package app.game.API;

import app.card.API.Card;
import app.player.API.Player;

/**
 * An interface that rapresent the logic of the player actions.
 */

public interface GameController {
    /**
     * @return void
     */
    void rollDice();

    /**
     * @param currentPlayer that want to buy the propriety
     * @return void
     */
    void buyPropriety(Player currentPlayer);

    /**
     * @param currentPlayer that want to build an house
     * @return void
     */
    void buildHouse(Player currentPlayer);

    /**
     * @param currentPlayer that want to sell the propriety
     * @return void
     */
    void sellPropriety(Player currentPlayer);

    /**
     * @return void
     */
    void endTurn();

    /**
     * @param currentPlayer that need to pay the fees
     * @param box           of the player who is to receive the money
     * @return void
     */
    void payFees(Player currentPlayer, Card box);

    /**
     * @param currentPlayer
     * @param box
     * @return void
     */
    void checkBox(Player currentPlayer, Card box);

    /**
     * @param currentPlayer we want to verify his position
     * @return the card the player is on
     */
    Card currentBox(Player currentPlayer);
}
