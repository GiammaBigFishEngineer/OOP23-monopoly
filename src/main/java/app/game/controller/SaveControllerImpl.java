package app.game.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.game.apii.SaveController;
import app.player.apii.Player;

/**
 * Implementation of SaveController with its logic.
 */
public class SaveControllerImpl implements SaveController {

    private static final String FILE_NAME = "saved_games.txt";
    private static final String ERROR_LOG_FILE = "error.log";
    private static final int MIN_NUM_PLAYER = 2;
    private static final int MAX_NUM_PLAYER = 5;
    private static final Logger LOGGER = Logger.getLogger(SaveControllerImpl.class.getName());
    private boolean isFirstSave = true;

    /**
     * Saves the current state of the game to a file.
     */
    @Override
    public void saveGame(final List<Player> gamePlayerList) {
        if (gamePlayerList == null 
            || gamePlayerList.size() < MIN_NUM_PLAYER 
            || gamePlayerList.size() > MAX_NUM_PLAYER) {
                throw new IllegalStateException("Inserire un numero corretto di giocatori (da 2 a 5).");
        }
        final List<Player> players = new ArrayList<>(gamePlayerList);
        try {
            if (!players.isEmpty() && shouldSaveGame(players)) {
                saveGameToFile(players);
                isFirstSave = false;
            }
        } catch (IOException e) {
            writeErrorToLogFile("Errore di I/O durante il salvataggio del gioco.", e);
        }
    }

    /**
     * Determines whether the game should be saved. 
     * This method is designed for extension. So the subclass should provide additional logic for
     * deciding when the game should be saved.
     * 
     * @return {@code true} if the game should be saved, {@code false} otherwise
     */
    @Override
    public boolean shouldSaveGame(final List<Player> playersList) {
        return isFirstSave || checkForChanges(playersList);
        //return checkForChanges(playersList);
    }

    /**
     * Checks for changes in the provided list of players.
     * 
     * @param players is the list of players to check
     * @return {@code true} if there are changes in the player, {@code false} otherwhise
     */
    private boolean checkForChanges(final List<Player> players) {
        return players.stream().anyMatch(player -> 
                player.hasPositionChanged() || player.getBankAccount().hasBalanceChanged());
    }

    private void saveGameToFile(final List<Player> players) throws IOException {
        final File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IOException("Impossibile creare un nuovo file.");
                }
            } catch (IOException e) {
                writeErrorToLogFile("Errore nella creazione del file.", e);
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, StandardCharsets.UTF_8, true))) {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss", Locale.ITALY);
            final String timestamp = dateFormat.format(new Date());
            writer.println("*** Partita del: " + timestamp + " ***");

            players.forEach(currentPlayer -> {
                writer.println("Player: " + currentPlayer.getName()
                               + ", Id: " + currentPlayer.getID()
                               + ", Posizione: " + currentPlayer.getCurrentPosition()
                               + ", Denaro: " + currentPlayer.getBankAccount().getBalance());
            });
            // to do: add a method that prints informations about each player's properties
            writer.println("\n");

            if (writer.checkError()) {
                writeErrorToLogFile("Errore durante la scrittura nel file", new IOException("Errore di scrittura nel file"));
            }
        } catch (IOException e) {
            writeErrorToLogFile("Errore di I/O durante il salvataggio del gioco.", e);
        }
    }

    /**
     * Displays the data of the saved games.
     * 
     * @return a list of strings representing the data of the saved games 
     *         with the various data of the players of the game
     */
    @Override
    public List<String> viewSavedGames() {
        final List<String> savedGames = new ArrayList<>();

        final File file = new File(FILE_NAME);
        if (!file.exists()) {
            return savedGames;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME, StandardCharsets.UTF_8))) {
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    savedGames.add(line);
                }
            } while (line != null);
        } catch (IOException e) {
            writeErrorToLogFile("Errore durante la lettura del file.", e);
        }

        return savedGames;
    }

    /**
     * Provides a formatted output of saved games for the view.
     * 
     * @return an {@code Optional} containing a formatted string representing the saved data or
     *         an empty {@code Optional} if there are no saved data yet.
     */
    @Override
    public Optional<String> getOutputSavedGames() {
        final List<String> savedGames = viewSavedGames();
        if (savedGames.isEmpty()) {
            return Optional.empty();
        }
        final String formattedInfo = "Storico partite\n\n" + String.join("\n", savedGames);
        return Optional.of(formattedInfo);
    }

    private void writeErrorToLogFile(final String message, final Exception exception) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ERROR_LOG_FILE, StandardCharsets.UTF_8, true))) {
            LOGGER.severe(message);
            exception.printStackTrace(writer);
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, message, ioException);
        }
    }
}
