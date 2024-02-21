package app.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import app.card.api.Card;
import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.card.impl.Unforseen;
import app.player.api.Player;
import app.player.impl.PlayerImpl;

/**
 * Test all unforseens.
 */
class UnforseenTest {

    private static final int ID_TEST = 5;
    private final List<Card> cards = new LinkedList<>();
    private final Player player = new PlayerImpl("Player", ID_TEST, cards, 0);

    /**
     * Test first unforseen.
     */
    @Test
    void testU1Action() {
        final int actual = 100;
        Unforseen.U0.getCard().makeAction(player);
        assertEquals(player.getBankAccount().getBalance(), actual);
    }

    /**
     * Test second unforseen.
     */
    @Test
    void testU2Action() {
        final int actual = 15;
        Unforseen.U1.getCard().makeAction(player);
        assertEquals(player.getCurrentPosition(), actual);
    }

    /**
     * @param player who's got action
     * @return true if changes are checked
     */
    private boolean checkChanges(final Player player) {
        /* position and balance start from -1, i check the changes */
        return player.getCurrentPosition() != 1 || player.getBankAccount().getBalance() != -1;
    }

    /**
     * test the first unforseen in table.
     * 
     * @throws IOException
     */
    @Test
    void testUseUnforseen() throws IOException {
        final var list = new CardFactoryImpl().cardsInitializer();
        final Unbuyable card = (Unbuyable) list.get(2);
        card.makeAction(player);
        assertTrue(checkChanges(player));
    }

    /**
     * test the extraction of one unforseen.
     * 
     * @throws IOException
     */
    @Test
    void testExtractUnforseen() {
        /*
         * eseguo il test per piu' volte in modo da aumentare la probabilita' di trovare
         * errori
         * in quanto unforseen() usa una generazione randomica.
         */
        for (int i = 0; i < 100; i++) {
            final var factory = new CardFactoryImpl();
            factory.createStaticCard(factory.createCard(i, "prova"),
                    "unforseen",
                    0)
                    .makeAction(player);
            assertTrue(checkChanges(player));
        }
    }
}
