package app.game.apii;

import app.player.apii.Player;

import java.util.Optional;

import app.game.view.*;;

public interface GameObservable {

    void registerObserver(GameObserverImpl obs);

    boolean updateObserver(Integer diceValue, Optional<Player> currentPlayer, String string);

}
