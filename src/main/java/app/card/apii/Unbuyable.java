package app.card.apii;

import java.util.Optional;

import app.card.impl.Unforseen;
import app.player.apii.Player;

/**
 * An Interface representing static, non-purchasable boxes.
 */
public interface Unbuyable extends Card {
    /**
     * @param player on who make an action
     * @return Optional empty if makeAction is not for ad Unforseen but for static card in table
     */
    Optional<Unforseen> makeAction(Player player);
}
