package app.game.apii;

import app.card.apii.Card;
import app.player.apii.Player;


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
