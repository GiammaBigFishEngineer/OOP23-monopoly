package app.game.controller;

import java.awt.Window;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import app.game.api.MenuController;

/**
 * Implementation of MenuController with its logic.
 */
public class MenuControllerImpl implements MenuController {

    private static final int MIN_NUM_PLAYER = 2;
    private static final int MAX_NUM_PLAYER = 5;

    /**
     * Starts a new game with the provided list of player names.
     * 
     * @param playerNames the list of player names participating in the game
     * @return {@code true} if the game is successfully started, 
     *         {@code false} otherwise.
     *         Th game can start only if the number of player names is between
     *         2 and 5 and there are no duplicates in names, ignoring case
     *         sensitivity.
     */
    @Override
    public boolean startGame(final List<String> playerNames) {
        if (playerNames == null || playerNames.isEmpty() 
            || playerNames.size() < MIN_NUM_PLAYER || playerNames.size() > MAX_NUM_PLAYER) {
                return false;
        }
        final Set<String> uniqueNames = playerNames.stream()
                            .filter(name -> name != null && !name.trim().isEmpty())
                            .map(String::toLowerCase)
                            .collect(Collectors.toSet());

        return uniqueNames.size() == playerNames.size();
    }

    /**
     * Quits the current game by disposing of all active windows.
     */
    @Override
    public void quitGame() {
        // System.exit(0); not used to resolve spotbug error
        final Window[] windows = Window.getWindows();
        for (final Window window : windows) {
            window.dispose();
        }
    }
}
