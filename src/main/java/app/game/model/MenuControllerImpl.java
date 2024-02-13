package app.game.model;

import app.game.apii.MenuController;
import app.player.apii.Player;
import app.player.impl.PlayerImpl;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Implementation of MenuController with its logic.
 */
public class MenuControllerImpl implements MenuController {
    private boolean isFirstSave;

    private static final String FILE_NAME = "saved_games.txt";
    private static final String ERROR_LOG_FILE = "error.log";
    private static final int MIN_NUM_PLAYER = 2;
    private static final int MAX_NUM_PLAYER = 5;
    private static final Logger LOGGER = Logger.getLogger(MenuControllerImpl.class.getName());
    private static final int INITIAL_AMOUNT = 1500;

    /**
     * Start a new game with the provided list of players.
     * 
     * @param players the list of players participating in the game
     * @return {@code true} if the game is successfully started, {@code false} otherwise
     */
    @Override
    public boolean startGame(final List<Player> players) {
        if (players.size() < MIN_NUM_PLAYER || players.size() > MAX_NUM_PLAYER) {
            return false;
        }

        this.isFirstSave = true;
        return true;
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

    /**
     * Inserts new players.
     * 
     * @param currentPlayerNames the list of the player names to be inserted
     * @return a list of player objects representing the inserted players for the current game
     */
    @Override
    public List<Player> insertPlayers(final List<String> currentPlayerNames) {
        final List<Player> playersList = new ArrayList<>();
        final Set<String> uniqueNames = new HashSet<>();

        for (int i = 0; i < currentPlayerNames.size(); i++) {
            final String playerName = currentPlayerNames.get(i);

            if (!uniqueNames.add(playerName)) {
                return new ArrayList<>();
            }

            if (playerName == null || playerName.isEmpty()) {
                return new ArrayList<>();
            }

            final Player player = new PlayerImpl(playerName, i + 1, new ArrayList<>(), INITIAL_AMOUNT);
            playersList.add(player);
        }

        return playersList;
    }

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
            } else {
                throw new IllegalStateException("Impossibile salvare la partita. Inizia una partita prima di salvare.");
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
    }

    /**
     * Checks for changes in the provided list of players.
     * 
     * @param players is the list of players to check
     * @return {@code true} if there are changes in the player, {@code false} otherwise
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
            writeErrorToLogFile("Errore di I/O durante il salvataggio del gioco.", e);
        }

        return savedGames;
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
