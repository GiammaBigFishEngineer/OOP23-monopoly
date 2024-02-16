package app.card.apii;

import app.player.apii.Player;

/**
 * A functional interface for design actions of static cards.
 * This is a Strategy patterns with lamda expression
 */
@FunctionalInterface
public interface StaticActionStrategy {
    /**
     * @param player who get the action
     * @return pass the event treggered doing the action to unbuyable
     */
    TriggeredEvent myAction(Player player);
}
