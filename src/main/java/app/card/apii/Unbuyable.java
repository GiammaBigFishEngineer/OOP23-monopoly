package app.card.apii;

import app.player.apii.Player;

/**
 * An Interface representing static, non-purchasable boxes.
 */
public interface Unbuyable {
    /**
     * @param player on who make an action
     */
    void makeAction(Player player);
}
