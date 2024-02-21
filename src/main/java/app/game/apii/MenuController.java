package app.game.apii;

import java.util.List;

/**
 * An interface that defines operations that can be performed to manage the menu.
 */
public interface MenuController {

    /**
     * Starts a new game with the provided list of player names.
     *
     * @param playerNames the list of player names participating in the game
     * @return {@code true} if the game is successfully started, {@code false} otherwise.
     *         The game can start only if the number of player names is between
     *         2 and 5 and there are no duplicates in names.
     */
    boolean startGame(List<String> playerNames);

    /**
     * Quits the current game.
     */
    void quitGame();
}
