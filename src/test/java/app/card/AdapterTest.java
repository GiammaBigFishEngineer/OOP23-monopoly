package app.card;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.card.impl.CardFactoryImpl;

import java.io.IOException;
import java.util.List;

/**
 * Test che Adapter pattern for cards.
 */
class AdapterTest {

    private List<Card> list;

    /**
     * @throws IOException
     */
    @BeforeEach
    void init() throws IOException {
        this.list = new CardFactoryImpl().cardsInitializer();
    }

    /**
     * test exeption adapting a static card to buildable.
     */
    @Test
    void testThrowsBuildableAdapter() {
        final var card = list.get(0); /* passo la prima cella del via */
        assertThrows(IllegalArgumentException.class, () -> CardAdapter.buildableAdapter(card));
    }

    /**
     * test exeption adapting a static card to buyable.
     */
    @Test
    void testThrowsBuyableAdapter() {
        final var card = list.get(0); /* passo la prima cella del via */
        assertThrows(IllegalArgumentException.class, () -> CardAdapter.buyableAdapter(card));
    }

    /**
     * test exeption adapting a buyable card to unbuyable.
     */
    @Test
    void testThrowsUnbuyableAdapter() {
        final var card = list.get(1); /* passo la prima proprietà */
        assertThrows(IllegalArgumentException.class, () -> CardAdapter.unbuyableAdapter(card));
    }

    /**
     * test adapting all cards type Card to subinstance Buyable or Unbuyable or Buildable.
     */
    @Test
    void testIteratorAdapter() {
        for (final var i: list) {
            if (i.isBuildable()) {
                final var adapted = CardAdapter.buildableAdapter(i);
                assertTrue(adapted.isBuildable());
            } else if (i.isBuyable()) {
                final var adapted = CardAdapter.buyableAdapter(i);
                assertTrue(adapted.isBuyable());
            } else if (i.isUnbuyable()) {
                final var adapted = CardAdapter.unbuyableAdapter(i);
                assertTrue(adapted.isUnbuyable());
            }
        }
    }
}
