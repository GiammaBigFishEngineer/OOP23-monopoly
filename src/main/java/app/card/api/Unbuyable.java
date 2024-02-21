package app.card.api;

import app.card.api.StaticActionStrategy.TriggeredEvent;
import app.player.api.Player;

/**
 * An Interface representing static, non-purchasable boxes.
 */
public interface Unbuyable extends Card {
    /**
     * @param player on who make an action
     * @return the event treggered doing the action
     */
    TriggeredEvent makeAction(Player player);
}
