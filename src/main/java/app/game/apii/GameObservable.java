package app.game.apii;

import java.util.Optional;

import app.game.view.*;;

public interface GameObservable {

    void registerObserver(GameObserverImpl obs);

    boolean updateObserver(Optional<Object> obj, String code);

}
