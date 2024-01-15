package app.card.apii;

import app.player.apii.Player;

/**
 * An Interface representing static, non-purchasable boxes.
 */
public interface Unbuyable extends Card {
    /**
	 * @param player on who make an action
     * @param action is the name of method to call by reflection in app.card.utils.StaticActions
	 */
    void makeAction(Player player);
}
