package app.game.apii;

import app.player.apii.Player;

public interface GameObservable {

    void registerObserver(GameObserver obs);

    boolean updateObserver(Integer diceValue, Player currentPlayer, String string);

}
