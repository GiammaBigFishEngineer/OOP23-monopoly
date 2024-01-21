package app.game.api;

import app.player.api.Player; 

import java.util.*;

/**
 * An interface that defines operations that can be performed to manage the menu.
 */
public interface MenuController {

    /**
     * Starts a new game with the provided list of players.
     *
     * @param players the list of players participating in the game
     * @return true if the game is successfully started, false otherwise
     */
    boolean startGame(List<Player> players);

    /**
     * Quits the current game
     */
    void quitGame();

    /**
     * Inserts new players.
     *
     * @param playerNames the list of player names to be inserted
     * @return a list of player objects representing the inserted players
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
}