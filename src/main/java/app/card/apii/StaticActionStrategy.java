package app.card.apii;

import java.util.Optional;

import app.card.impl.Unforseen;
import app.player.apii.Player;

/**
 * A functional interface for design actions of static cards.
 * This is a Strategy patterns with lamda expression
 */
@FunctionalInterface
public interface StaticActionStrategy {
    /**
     * 
     * @param player who get the action
     * @return an empty optional if the action is not unforseen
     */
    Optional<Unforseen> myAction(Player player);
}
