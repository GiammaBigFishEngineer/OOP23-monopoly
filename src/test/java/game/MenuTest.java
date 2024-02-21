package game;

import app.game.api.MenuController;
import app.game.model.MenuControllerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for menu logic: {@link app.game.model.MenuControllerImpl}.
 */
class MenuTest {
    private MenuController menuController;
    private static final String DUMMY_PLAYER_PREFIX = "Player";
    private static final int ONE_PLAYER = 1;
    private static final int TWO_PLAYERS = 2;
    private static final int THREE_PLAYERS = 3;
    private static final int FOUR_PLAYERS = 4;
    private static final int FIVE_PLAYERS = 5;
    private static final int SIX_PLAYERS = 6;

    /**
     * The initialization: a new istance of the menu controller is created.
     */
    @BeforeEach
    void init() {
        this.menuController = new MenuControllerImpl();
    }

    /**
     * Test that the game starts correctly with 2 player names.
     */
    @Test
    void successfulStartGameWith2Players() {
        final List<String> playerNames = createDummyPlayers(TWO_PLAYERS);
        assertEquals(2, playerNames.size(), "Expected 2 player names.");
        assertTrue(menuController.startGame(playerNames));
    }

    /**
     * Test that the game starts correctly with 3 player names.
     */
    @Test
    void successfulStartGameWith3Players() {
        final List<String> playerNames = createDummyPlayers(THREE_PLAYERS);
        assertEquals(3, playerNames.size(), "Expected 3 player names.");
        assertTrue(menuController.startGame(playerNames));
    }

    /**
     * Test that the game starts correctly with 4 player names.
     */
    @Test
    void successfulStartGameWith4Players() {
        final List<String> playerNames = createDummyPlayers(FOUR_PLAYERS);
        assertEquals(4, playerNames.size(), "Expected 4 player names.");
        assertTrue(menuController.startGame(playerNames));
    }

    /**
     * Test that the game starts correctly with 5 player names.
     */
    @Test
    void successfulStartGameWith5Players() {
        final List<String> playerNames = createDummyPlayers(FIVE_PLAYERS);
        assertEquals(FIVE_PLAYERS, playerNames.size(), "Expected 5 player names.");
        assertTrue(menuController.startGame(playerNames));
    }

    /**
     * Test that the game does not start with only 1 player name.
     * The game starts correctly only with a number of player names between 2 and 5.
     */
    @Test
    void unsuccessfulStartGameWithTooFewPlayers() {
        final List<String> playerNames = createDummyPlayers(ONE_PLAYER);
        assertEquals(1, playerNames.size(), "Expected a number of player names between 2 and 5.");
        assertFalse(menuController.startGame(playerNames));
    }

    /**
     * Test that the game does not start with 6 player names.
     * The game starts correctly only with a number of players between 2 and 5.
     */
    @Test
    void unsuccessfulStartGameWithTooManyPlayers() {
        final List<String> playerNames = createDummyPlayers(SIX_PLAYERS);
        assertEquals(SIX_PLAYERS, playerNames.size(), "Expected a number of player names between 2 and 5.");
        assertFalse(menuController.startGame(playerNames));
    }

    /**
     * Test that the game does not start with duplicate player name.
     */
    @Test
    void unsuccessfulStartGameWithDuplicatePlayerNames() {
        final List<String> playerNames = List.of(DUMMY_PLAYER_PREFIX, DUMMY_PLAYER_PREFIX);
        final Set<String> uniqueNames = new HashSet<>(playerNames);
        assertEquals(1, uniqueNames.size(), "Expected duplicate players, so there is only one player name.");
        assertFalse(menuController.startGame(playerNames));
    }

    /**
     * Test that the game does not start with duplicate names, in particular even
     * considering the distinction between case sensitivity.
     */
    @Test
    void unsuccessfulStartGameWithSameNameDifferentCasing() {
        final List<String> playerNames = List.of("Giovanni", "giovanni");
        // Using 'Set' it consideres correctly 2 different elements
        final Set<String> uniqueNames = new HashSet<>(playerNames);
        assertEquals(2, uniqueNames.size(), "Expected two players becasuse elements are different.");
        // Using my 'startGame' method it returns false because elements are the same
        assertFalse(menuController.startGame(playerNames));
    }

    /**
     * Test that the game does not start with an empty player name.
     */
    @Test
    void unsuccessfulStartGameWithPlayerNameEmpty() {
        final List<String> playerNames = List.of("Player1", "", "");
        assertFalse(menuController.startGame(playerNames));
    }

    /**
     * Test the functionality of quitting the game without any exception.
     */
    @Test
    void testQuitGame() {
        assertDoesNotThrow(() -> menuController.quitGame());
    }

    /**
     * Utility method: it creates a list of dummy player names.
     * 
     * @param numPlayers the number of dummy names to create
     * @return a list of dummy player names, each distinguished
     *         by a numerical index (es: Player1, Player2, Player3, ...)
     */
    private List<String> createDummyPlayers(final int numPlayers) {
        final List<String> playerNames = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            playerNames.add(DUMMY_PLAYER_PREFIX + i);
        }
        return playerNames;
    }
}
