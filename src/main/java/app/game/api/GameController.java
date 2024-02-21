package app.game.api;

import app.card.api.Card;
import app.player.api.Player;

/**
 * An interface that rapresent the logic of the player actions.
 */

public interface GameController {
    /**
     * Roll dice.
     */
    void rollDice();

    /**
     * @param currentPlayer that want to buy the propriety.
     */
    void buyPropriety(Player currentPlayer);

    /**
     * @param currentPlayer that want to build an house.
     */
    void buildHouse(Player currentPlayer);

    /**
     * @param currentPlayer that want to sell the propriety.
     */
    void sellPropriety(Player currentPlayer);

    /**
     * End turn.
     */
    void endTurn();

    /**
     * @param currentPlayer that need to pay the fees
     * @param box of the player who is to receive the money
     */
    void payFees(Player currentPlayer, Card box);

    /**
     * @param currentPlayer
     * @param box
     */
    void checkBox(Player currentPlayer, Card box);
    /** 
     * @param currentPlayer
     * @return card
     */
    Card currentBox(Player currentPlayer);
}
