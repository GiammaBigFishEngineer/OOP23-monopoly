package app.game.api;

import java.util.Optional;

import app.game.utils.ObserverCodeEnum;

/**
 * An interface that rapresent the observer component.
 * This observer is used to receive a value from the observable component and
 * update the game graphics accordingly
 */

public interface ViewObserver {

    /**
     * @param obj  is an optional of a generic object
     * @param code is a code that rapresent the action that this method will do
     * @return true if the action was successful, false otherwise
     */

    boolean update(Optional<Object> obj, ObserverCodeEnum code);

}
