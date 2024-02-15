package app.game.model;

import app.game.apii.MenuController;
import java.awt.Window;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of MenuController with its logic.
 */
public class MenuControllerImpl implements MenuController {

    private static final int MIN_NUM_PLAYER = 2;
    private static final int MAX_NUM_PLAYER = 5;

    /**
     * Start a new game with the provided list of players.
     * 
     * @param playerNames the list of player names participating in the game
     * @return {@code true} if the game is successfully started, {@code false} otherwise
     */
    @Override
    public boolean startGame(final List<String> playerNames) {
        if (playerNames.size() < MIN_NUM_PLAYER || playerNames.size() > MAX_NUM_PLAYER) {
            return false;
        }

        /*Set<String> uniqueNames = new HashSet<>(playerNames);
        if (uniqueNames.size() != playerNames.size() || playerNames.contains(null) || playerNames.contains("")) {
            return false;
        }*/

        // Rimozione eventuali spazi
        final Set<String> uniqueNames = new HashSet<>();
        for (final String name : playerNames) {
            final String normalizedName = name.trim(); //.toLowerCase;   es: Gio e gio sarebbero uguali
            uniqueNames.add(normalizedName);
        }

        /*if (uniqueNames.size() != playerNames.size() || playerNames.contains(null) || playerNames.contains("")) {
            return false;
        }*/

        return !(uniqueNames.size() != playerNames.size() || playerNames.contains(null) || playerNames.contains(""));
    }

    /**
     * Quits the current game.
     */
    @Override
    public void quitGame() {
        //System.exit(0); not used to resolve spotbug error
        final Window[] windows = Window.getWindows();
        for (final Window window : windows) {
            window.dispose();
        }
    }
}
