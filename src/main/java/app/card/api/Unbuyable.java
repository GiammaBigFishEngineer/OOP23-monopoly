package app.card.api;

import app.player.api.Player;

/**
 * An Interface representing static, non-purchasable boxes.
 */
public interface Unbuyable extends Card {
    /**
	 * @param player on who make an action
	 */
    void makeAction(Player player);

    /**
     * @param action is the name of method to call by reflection in app.card.utils.StaticActions
     */
    String getAction();
}
