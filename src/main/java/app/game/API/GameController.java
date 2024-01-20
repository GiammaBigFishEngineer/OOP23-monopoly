package app.game.API;

import app.card.API.Card;
import app.player.API.Player;

public interface GameController {
    /**
     * @return void
     */
    void rollDice();

    void buyPropriety(Player currentPlayer);

    void buildHouse(Player currentPlayer);

    void sellPropriety(Player currentPlayer);

    void endTurn();

    void payFees(Player currentPlayer, Card box);

    void checkBox(Player currentPlayer, Card box);

    Card currentBox(Player currentPlayer);
}
