package app.card.apii;

import app.card.apii.StaticActionStrategy.TriggeredEvent;
import app.player.apii.Player;

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
