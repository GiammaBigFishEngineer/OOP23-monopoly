package app.game.api;

import java.util.List;

/**
 * An interface that defines operations that can be performed to manage the menu.
 */
public interface MenuController {

    /**
     * Starts a new game with the provided list of player names.
     *
     * @param playerNames the list of players participating in the game
     * @return {@code true} if the game is successfully started, {@code false} otherwise
     */
    boolean startGame(List<String> playerNames);

    /**
     * Quits the current game.
     */
    void quitGame();
}
