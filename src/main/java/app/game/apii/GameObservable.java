package app.game.apii;

import app.player.apii.Player;
import app.game.view.*;;

public interface GameObservable {

    void registerObserver(GameObserverImpl obs);

    boolean updateObserver(Integer diceValue, Player currentPlayer, String string);

}
