package app.card.apii;

import java.util.Optional;

/**
 * Functional interface, it rappresents the event triggered when
 * a static card unbuyable call an action on player.
 */
@FunctionalInterface
public interface TriggeredEvent {

    /**
     * This method is used for send information about event called.
     * @return empty if the event did not go through, 
     * otherwise the warning message relating to the event
     */
    Optional<String> showMessage();


}
