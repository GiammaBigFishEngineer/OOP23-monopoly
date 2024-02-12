package app.game.apii;

import app.card.apii.Card;
import app.player.apii.Player;

/**
 * GameController.
 */
public interface GameController {
    /**
     * 
     */
    void rollDice();
    /**
     * 
     * @param currentPlayer
     */
    void buyPropriety(Player currentPlayer);
    /**
     * @param currentPlayer
     */
    void buildHouse(Player currentPlayer);
    /**
     * @param currentPlayer
     */
    void sellPropriety(Player currentPlayer);
    /**
     * 
     */
    void endTurn();
    /**
     * @param currentPlayer
     * @param box
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
