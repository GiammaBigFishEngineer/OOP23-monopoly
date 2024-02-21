package game;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.card.apii.Card;
import app.game.apii.SaveController;
import app.game.controller.SaveControllerImpl;
import app.player.apii.Player;
import app.player.impl.PlayerImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for save logic: {@link app.game.controller.SaveControllerImpl}.
 */
class SaveTest {
    private SaveController saveController;
    private static final String TEST_FILE_NAME = "saved_games.txt";
    private static final int DUMMY_POSITION = 22;
    private static final int DUMMY_BALANCE = 777;

    /**
     * The initialization: a new istance of the save controller is created.
     */
    @BeforeEach
    void init() {
        this.saveController = new SaveControllerImpl();
    }

    /**
     * Method that is called after each test method to clean up the file created
     * during the test to verify the save.
     * It deletes the test file used for saving data if it exists.
     * 
     * @throws IOException if an I/O error occurs during the file deletion
     */
    @AfterEach
    void clean() throws IOException {
        final File testFile = new File(TEST_FILE_NAME);
        if (testFile.exists() && !testFile.delete()) {
                throw new IOException("Unable to delete the test file.");
        }
    }

    /**
     * Test that verifies the correct saving of data without exceptions occurring.
     */
    @Test
    void testSaveGame() throws IOException {
        final List<Player> players = createDummyPlayers(2);
        assertDoesNotThrow(() -> saveController.saveGame(players));
        final File testFile = new File(TEST_FILE_NAME);
        assertTrue(testFile.exists());
        assertTrue(testFile.length() > 0);
    }

    /**
     * Test that verifies the behavior of saveGame method when attempting to save a game with fewer players 
     * than the required minimum (2 players).
     * 
     * @throws IllegalStateException when attempting to save a game with an insufficient number of players
     */
    @Test
    void testSaveGameWithLessThanRequiredPlayers() {
        final List<Player> players = createDummyPlayers(1);
        assertThrows(IllegalStateException.class, () -> saveController.saveGame(players));
    }

    /**
     * Test that verifies the behavior of saveGame method when attempting to save a game with more players 
     * than the required maximum (5 players).
     * 
     * @throws IllegalStateException when attempting to save a game with an excessive number of players
     */
    @Test
    void testSaveGameWithMoreThanRequiredPlayers() {
        final List<Player> players = createDummyPlayers(7);
        assertThrows(IllegalStateException.class, () -> saveController.saveGame(players));
    }

    /**
     * Test that verifies if the game should be saved or not.
     */
    @Test
    void testShouldSaveGame() {
        final List<Player> players = createDummyPlayers(2);
        assertTrue(saveController.shouldSaveGame(players));
        saveController.saveGame(players);
        assertFalse(saveController.shouldSaveGame(players));
        players.get(0).setPosition(DUMMY_POSITION);
        assertTrue(saveController.shouldSaveGame(players));
        saveController.saveGame(players);
        players.get(0).getBankAccount().setBalance(DUMMY_BALANCE);
        assertTrue(saveController.shouldSaveGame(players));
        saveController.saveGame(players);
    }
    /**
     * Test that verifies that the saved data can be read.
     */
    @Test
    void testViewSavedGames() {
        final List<Player> players = createDummyPlayers(3);
        saveController.saveGame(players);
        final List<String> savedGames = saveController.viewSavedGames();
        assertNotNull(savedGames);
        assertFalse(savedGames.isEmpty());
    }

    /**
     * Utility method: it creates a list of dummy players with a name, id, list of cards and initial amount.
     * 
     * @param numPlayers the number of dummy players to create
     * @return a list of dummy players
     */
    private List<Player> createDummyPlayers(final int numPlayers) {
        final List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            final String name = "Player" + i;
            final int id = i;
            final List<Card> cards = createDummyCards();
            final int initialAmount = 1500;
            final Player player = new PlayerImpl(name, id, cards, initialAmount);
            players.add(player);
        }
        return players;
    }

    /**
     * Utility method: it creates a list of dummy cards.
     * 
     * @return a list of dummy cards.
     */
    private List<Card> createDummyCards() {
        return new ArrayList<>();
    }
}
