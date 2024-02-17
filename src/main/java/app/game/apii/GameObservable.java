package app.game.apii;

import java.util.Optional;

import app.game.view.GameObserverImpl;

/**
 * An interface that rapresent the observable component.
 */

public interface GameObservable {

    /**
     * @param obs the observer we want to update, it will be only one
     */

    void registerObserver(GameObserverImpl obs);

    /**
     * @param obj  is an optional of a generic object
     * @param code is the string that identifies the type of action that the
     *             observer will do
     * 
     * @return true if the action was succesfull, false otherwise
     */

    boolean updateObserver(Optional<Object> obj, String code);

}
