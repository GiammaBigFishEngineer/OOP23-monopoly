package card;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.card.api.CardAdapter;
import app.card.api.CardFactory;
import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.card.api.Buildable;
import app.card.api.Buyable;
import app.card.api.Card;

import java.io.IOException;
import java.util.List;

/**
 * Test che Adapter pattern for cards.
 */
public class AdapterTest {

    private CardFactory factory;
    private List<Card> list;

    /**
    * @throws IOException
    */
    @BeforeEach
    public void init() throws IOException {
        this.factory = new CardFactoryImpl();
        this.list = factory.cardsInitializer();
    }

    /**
    * test exeption adapting a static card to buildable.
    */
    @Test
    public void testThrowsBuildableAdapter() {
        final var card = list.get(0); /* passo la prima cella del via */
        assertThrows(IllegalArgumentException.class, () -> CardAdapter.buildableAdapter(card));
    }

    /**
    * test exeption adapting a static card to buyable.
    */
    @Test
    public void testThrowsBuyableAdapter() {
        final var card = list.get(0); /* passo la prima cella del via */
        assertThrows(IllegalArgumentException.class, () -> CardAdapter.buyableAdapter(card));
    }

    /**
    * test exeption adapting a buyable card to unbuyable.
    */
    @Test
    public void testThrowsUnbuyableAdapter() {
        final var card = list.get(1); /* passo la prima proprietà */
        assertThrows(IllegalArgumentException.class, () -> CardAdapter.unbuyableAdapter(card));
    }

    /**
    * test adapting all cards type Card to subinstance Buyable or Unbuyable or Buildable.
    */
    @Test
    public void testIteratorAdapter() {
        for (var i: list) {
            if (i.isBuildable()) {
                final var adapted = CardAdapter.buildableAdapter(i);
                assertTrue(adapted instanceof Buildable);
            } else if (i.isBuyable()) {
                final var adapted = CardAdapter.buyableAdapter(i);
                assertTrue(adapted instanceof Buyable);
            } else if (i.isUnbuyable()) {
                final var adapted = CardAdapter.unbuyableAdapter(i);
                assertTrue(adapted instanceof Unbuyable);
            }
        }
    }
}
