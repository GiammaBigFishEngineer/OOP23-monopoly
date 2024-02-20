package app.game.apii;

import java.util.Optional;

import app.game.utils.ObserverCodeEnum;
import app.game.view.ViewObserverImpl;

/**
 * An interface that rapresent the observable component.
 * This interface is extended by the button panel and based on the game
 * controller outputs it serves to invoke a certain action of the observer
 */

public interface ViewObservable {

    /**
     * @param obs the observer we want to update, it will be only one
     */

    void registerObserver(ViewObserverImpl obs);

    /**
     * @param obj  is an optional of a generic object
     * @param code is the code that identifies the type of action that the
     *             observer will do
     * 
     * @return true if the action was succesfull, false otherwise
     */

    boolean updateObserver(Optional<Object> obj, ObserverCodeEnum code);

}
