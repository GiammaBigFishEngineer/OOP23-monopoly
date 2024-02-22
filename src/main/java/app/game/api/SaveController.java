package app.game.api;

import java.util.List;
import java.util.Optional;

import app.player.api.Player;

/**
 * An interface that defines operations that can be used for saving data.
 */
public interface SaveController {

    /**
     * Saves the current state of the game players to a file.
     * 
     * @param gamePlayerList the list of player that needs to be saved
     */
    void saveGame(List<Player> gamePlayerList);

    /**
     * Displays the data of the saved games.
     *
     * @return a list of strings representing the data of the saved games
     */
    List<String> viewSavedGames();

    /**
     * Provides a formatted output of saved games for the view.
     * 
     * @return an {@code Optional} containing a formatted string representing the
     *         saved data.
     */
    Optional<String> getOutputSavedGames();
}
