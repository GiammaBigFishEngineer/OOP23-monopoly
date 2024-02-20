package app.game.apii;

import java.util.List;

import app.player.apii.Player;

/**
 * An interface that defines operations that can be performed to manage the
 * menu.
 */
public interface MenuController {

    /**
     * Starts a new game with the provided list of player names.
     *
     * @param playerNames the list of players participating in the game
     * @return {@code true} if the game is successfully started, {@code false}
     *         otherwise
     */
    boolean startGame(List<String> playerNames);

    /**
     * Quits the current game.
     */
    void quitGame();

    /**
     * Inserts new players.
     *
     * @param playerNames the list of player names to be inserted
     * @return a list of player objects representing the inserted players for the
     *         current game
     */
    List<Player> insertPlayers(List<String> playerNames);

    /**
     * Saves the current state of the game to a file.
     */
    void saveGame();

    /**
     * Displays the data of the saved games.
     *
     * @return a list of strings representing the data of the saved games
     */
    List<String> viewSavedGames();

    /**
     * Determines whether the game should be saved.
     * The game should be saved if it is the first save or if there are changes in
     * player positions or balances.
     * It is used to know when to save game data and, consequently, to create a
     * summary of all game sessions.
     * 
     * @return true if the game should be saved, false otherwise.
     */
    boolean shouldSaveGame();
}
