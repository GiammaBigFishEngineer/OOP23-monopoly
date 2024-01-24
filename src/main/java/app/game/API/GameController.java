package app.game.api;

import app.card.api.Card;
import app.player.api.Player;


public interface GameController {
    void rollDice();
    void buyPropriety(Player currentPlayer);
    void buildHouse(Player currentPlayer);
    void sellPropriety(Player currentPlayer);
    void endTurn();
    void payFees(Player currentPlayer, Card box);
    void checkBox(Player currentPlayer, Card box);
    Card currentBox(Player currentPlayer);
}
