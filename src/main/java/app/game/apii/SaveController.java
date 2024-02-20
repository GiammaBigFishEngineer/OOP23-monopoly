package app.game.apii;

import java.util.List;
import java.util.Optional;

import app.player.apii.Player;

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
     * Determines whether the game should be saved.
     * The game should be saved if it is the first save or if there are changes in player positions or balances.
     * It is used to know when to save game data and, consequently, to create a summary of all game sessions.
     * 
     * @param gamePlayerList the list of players that must be saved if there are changes
     * @return {@code true} if the game should be saved, {@code false} otherwise.
     */
    boolean shouldSaveGame(List<Player> gamePlayerList);

    /**
     * Displays the data of the saved games.
     *
     * @return a list of strings representing the data of the saved games
     */
    List<String> viewSavedGames();

    /**
     * Provides a formatted output of saved games for the view.
     * 
     * @return an {@code Optional} containing a formatted string representing the saved data.
     */
    Optional<String> getOutputSavedGames();
}
