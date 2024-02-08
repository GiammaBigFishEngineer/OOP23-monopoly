package app.player.apii;

import app.card.apii.Card;

/**
 * Interface which defines how the logic of PlayerPanelView works.
 */
public interface PlayerPanelLogic {
    /**
     * @param player
     * @param currentBox
     *                   This method sets the Player of the current shift and the
     *                   Card on which the player is.
     */
    void setPlayer(Player player, Card currentBox);

    /**
     * @param currentBox
     *                   This method sets the updated value of the box on which the
     *                   Player is.
     */
    void setCurrentBox(Card currentBox);

    /**
     * Method which refresh the PlayerPanelView with updated values.
     */
    void refresh();
}
