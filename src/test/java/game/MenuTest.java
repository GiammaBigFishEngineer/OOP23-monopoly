package game;

import app.game.apii.MenuController;
import app.game.model.MenuControllerImpl;
import app.player.apii.Player;
import app.player.impl.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for menu logic.
 */
class MenuTest {
    private MenuController menuController;
    private static final String DUMMY_PLAYER = "Player";
    private static final int ONE_PLAYER = 1;
    private static final int TWO_PLAYERS = 2;
    private static final int THREE_PLAYERS = 3;
    private static final int FOUR_PLAYERS = 4;
    private static final int FIVE_PLAYERS = 5;
    private static final int SIX_PLAYERS = 6;
    private static final int DUMMY_BALANCE = 1000;
    private static final int DUMMY_POSITION = 22;

    /**
     * The initialization: a new istance of the menu controller is created.
     */
    @BeforeEach
    void init() {
        this.menuController = new MenuControllerImpl();
    }

    /**
     * Test that the game starts correctly with 2 players.
     */
    @Test
    void successfulStartGameWith2Players() {
        final List<Player> players = createDummyPlayers(TWO_PLAYERS);
        assertEquals(2, players.size());
        assertTrue(menuController.startGame(players));
    }

    /**
     * Test that the game starts correctly with 3 players.
     */
    @Test
    void successfulStartGameWith3Players() {
        final List<Player> players = createDummyPlayers(THREE_PLAYERS);
        assertEquals(3, players.size());
        assertTrue(menuController.startGame(players));
    }

    /**
     * Test that the game starts correctly with 4 players.
     */
    @Test
    void successfulStartGameWith4Players() {
        final List<Player> players = createDummyPlayers(FOUR_PLAYERS);
        assertEquals(4, players.size());
        assertTrue(menuController.startGame(players));
    }

    /**
     * Test that the game starts correctly with 5 players.
     */
    @Test
    void successfulStartGameWith5Players() {
        final List<Player> players = createDummyPlayers(FIVE_PLAYERS);
        assertEquals(FIVE_PLAYERS, players.size());
        assertTrue(menuController.startGame(players));
    }

    /**
     * Test that the game does not start with only 1 player.
     * The game starts correctly only with a number of players between 2 and 5.
     */
    @Test
    void unsuccessfulStartGameWithTooFewPlayers() {
        final List<Player> players = createDummyPlayers(ONE_PLAYER);
        assertEquals(1, players.size());
        assertFalse(menuController.startGame(players));
    }

    /**
     * Test that the game does not start with 6 players.
     * The game starts correctly only with a number of players between 2 and 5.
     */
    @Test
    void unsuccessfulStartGameWithTooManyPlayers() {
        final List<Player> players = createDummyPlayers(SIX_PLAYERS);
        assertEquals(SIX_PLAYERS, players.size());
        assertFalse(menuController.startGame(players));
    }

    /**
     * Test that verifies the correct insertion of the players' names.
     */
    @Test
    void validInsertPlayers() {
        final List<String> currentPlayerNames = new ArrayList<>();
        currentPlayerNames.add(DUMMY_PLAYER + "1");
        currentPlayerNames.add(DUMMY_PLAYER + "2");
        final List<Player> insertedPlayers = menuController.insertPlayers(currentPlayerNames);
        assertEquals(currentPlayerNames.size(), insertedPlayers.size());
        assertEquals("Player1", insertedPlayers.get(0).getName());
        assertEquals(1, insertedPlayers.get(0).getID());
        assertEquals("Player2", insertedPlayers.get(1).getName());
        assertEquals(2, insertedPlayers.get(1).getID());
    }

    /**
     * Test that verifies for incorrect insertion of players' names due to a null value.
     * Then it checks that the game cannot start.
     */
    @Test
    void invalidInsertPlayersCaseNull() {
        final List<String> currentPlayerNames = new ArrayList<>();
        currentPlayerNames.add(DUMMY_PLAYER + "1");
        currentPlayerNames.add(null);
        final List<Player> insertedPlayers = menuController.insertPlayers(currentPlayerNames);
        assertTrue(insertedPlayers.isEmpty());
        assertFalse(menuController.startGame(insertedPlayers));
    }

    /**
     * Test that verifies for incorrect insertion of players' names due to a empty value.
     * Then it checks that the game cannot start.
     */
    @Test
    void invalidInsertPlayersCaseEmpty() {
        final List<String> currentPlayerNames = new ArrayList<>();
        currentPlayerNames.add(DUMMY_PLAYER + "1");
        currentPlayerNames.add("");
        final List<Player> insertedPlayers = menuController.insertPlayers(currentPlayerNames);
        assertEquals(0, insertedPlayers.size());
        assertFalse(menuController.startGame(insertedPlayers));
    }

    /**
     * Test that verifies that is impossible to insert players with the same name.
     * Then it checks that the game cannot start.
     */
    @Test
    void invalidInsertPlayersWithSameNames() {
        final List<String> currentPlayerNames = new ArrayList<>();
        currentPlayerNames.add(DUMMY_PLAYER);
        currentPlayerNames.add(DUMMY_PLAYER);
        currentPlayerNames.add(DUMMY_PLAYER);
        final Set<String> uniqueNames = new HashSet<>(currentPlayerNames);
        assertEquals(1, uniqueNames.size());
        final List<Player> insertedPlayers = menuController.insertPlayers(currentPlayerNames);
        assertEquals(0, insertedPlayers.size());
        assertFalse(menuController.startGame(insertedPlayers));
    }

    /**
     * Test that verifies the correct saving of data without exceptions occurring.
     */
    @Test
    void testSaveGame() {
        final List<Player> players = createDummyPlayers(TWO_PLAYERS);
        assertTrue(menuController.startGame(players));
        assertDoesNotThrow(() -> menuController.saveGame());
    }

    /**
     * Test that verifies that it is impossible to save data if the game has not started.
     */
    @Test
    void testSaveGameWhenGameNotStarted() {
        final List<Player> players = createDummyPlayers(ONE_PLAYER);
        assertFalse(menuController.startGame(players));

        final Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                menuController.saveGame();
            }
        };

        assertThrows(IllegalStateException.class, executable);
    }

    /**
     * Test that verifies if the game should be saved or not.
     */
    @Test
    void testShouldSaveGame() {
        final List<Player> players = createDummyPlayers(TWO_PLAYERS);
        assertTrue(menuController.startGame(players));
        assertTrue(menuController.shouldSaveGame());

        menuController.saveGame();
        assertFalse(menuController.shouldSaveGame());

        players.get(0).setPosition(DUMMY_POSITION);
        assertTrue(menuController.shouldSaveGame());
        menuController.saveGame();

        players.get(0).getBankAccount().setBalance(DUMMY_BALANCE);
        assertTrue(menuController.shouldSaveGame());
        menuController.saveGame();
    }

    /**
     * Test that verifies that the saved data can be read.
     */
    @Test
    void testViewSavedGames() {
        final List<Player> players = createDummyPlayers(THREE_PLAYERS);
        assertTrue(menuController.startGame(players));
        menuController.saveGame();
        final List<String> savedGames = menuController.viewSavedGames();
        assertNotNull(savedGames);
        assertFalse(savedGames.isEmpty());
    }

    /**
     * Utility method: it creates a list of dummy players with id and names.
     * @param numPlayers the number of dummy players to create
     * @return a list of dummy players
     */
    private List<Player> createDummyPlayers(final int numPlayers) {
        final List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new PlayerImpl(i + 1, DUMMY_PLAYER + i));
        }
        return players;
    }
}
