package app.card.api;

import java.util.Optional;

import app.card.impl.Unforseen;
import app.player.api.Player;

/**
 * A functional interface for design actions of static cards.
 */
@FunctionalInterface
public interface StaticAction {
    /**
     * 
     * @param player who get the action
     * @param amount is the amount of action
     * @return an empty optional if the action is not unforseen
     */
    Optional<Unforseen> myAction(Player player, int amount);
}
