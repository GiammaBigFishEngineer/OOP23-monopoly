package app.game.apii;

import java.util.Optional;

/**
 * An interface that rapresent the observer component.
 */

public interface GameObserver {

    /**
     * @param obj is an optional of a generic object
     * @param str is a string that rapresent the action that this method will do
     * @return true if the action was successful, false otherwise
     */

    public boolean update(Optional<Object> obj, String str);

}
