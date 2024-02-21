package app.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import app.card.api.Buildable;
import app.card.api.Buyable;
import app.card.api.Card;
import app.card.api.CardFactory;
import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.player.api.Player;
import app.player.impl.PlayerImpl;

/**
 * Test for all cards.
 */
class CardTest {

    private CardFactory factory;
    private static final int ID_TEST = 5;
    private static final int ID_TEST_PRISON = 18;
    private static final int ID_TEST_GO = 0;
    private static final int AMOUNT_TEST_MONEY = 200;
    private static final int AMOUNT_TEST_PRICE = 10;
    private final List<Card> cards = new LinkedList<>();
    private final Player newPlayer = new PlayerImpl("Player", ID_TEST, cards, 0);

    /**
     * inizitalize factory.
     */
    @BeforeEach
    void init() {
        this.factory = new CardFactoryImpl();
    }

    /**
     * test empty property.
     */
    @Test
    void testEmptyProperty() {
        final Buildable buildable = this.factory.createProperty(this.factory.createCard(ID_TEST, null), 0, 0, 0);
        assertEquals(buildable.getHousePrice(), 0);
        assertEquals(buildable.getCardId(), ID_TEST);
        assertEquals(buildable.getName(), null);
        assertEquals(buildable.getPrice(), 0);
    }

    /**
     * test normal property.
     */
    @Test
    void testProperty() {
        final Buildable buildable = this.factory.createProperty(
                this.factory.createCard(ID_TEST, "prova"),
                AMOUNT_TEST_PRICE, AMOUNT_TEST_PRICE, 0);
        assertEquals(buildable.getHousePrice(), AMOUNT_TEST_PRICE);
        assertEquals(buildable.getCardId(), ID_TEST);
        assertEquals(buildable.getName(), "prova");
        assertEquals(buildable.getPrice(), 10);
    }

    /**
     * test normal property with no owner.
     */
    @Test
    void testPropertyEmptyOwner() {
        final Buildable buildable = this.factory.createProperty(
                this.factory.createCard(ID_TEST, "prova"),
                AMOUNT_TEST_PRICE, AMOUNT_TEST_PRICE, 0);
        assertFalse(buildable.isOwned());
    }

    /**
     * test normal station.
     */
    @Test
    void testStation() {
        final Buyable station = this.factory.createStation(
                this.factory.createCard(ID_TEST, "North"),
                AMOUNT_TEST_PRICE, AMOUNT_TEST_PRICE);
        assertEquals(station.getCardId(), ID_TEST);
        assertEquals(station.getName(), "North");
        assertEquals(station.getPrice(), AMOUNT_TEST_PRICE);
    }

    /**
     * test the first card "GO".
     */
    @Test
    void testStaticCardGo() {
        final Unbuyable staticCard = this.factory.createStaticCard(
                this.factory.createCard(ID_TEST_GO, "Go"),
                "giveMoneyPlayer", AMOUNT_TEST_MONEY);
        staticCard.makeAction(newPlayer);
        assertEquals(AMOUNT_TEST_MONEY, newPlayer.getBankAccount().getBalance());
    }

    /**
     * test the card "Prison".
     */
    @Test
    void testStaticCardPrison() {
        final Unbuyable staticCard = this.factory.createStaticCard(
                this.factory.createCard(ID_TEST_PRISON, "prison"),
                "movePlayer", ID_TEST_PRISON);
        staticCard.makeAction(newPlayer);
        assertEquals(staticCard.getCardId(), newPlayer.getCurrentPosition());
    }

    /**
     * test the inizialitation of table.
     * the list is not sorted by ascending id, therefore I check that each id is
     * contained between 0 and 23.
     */
    @Test
    void testInitializer() throws IOException {
        final List<Card> list = this.factory.cardsInitializer();
        final int finalSize = 23;
        for (int i = 0; i <= finalSize; i++) {
            assertTrue(listContains(i, list));
        }
    }

    private Boolean listContains(final int i, final List<Card> list) throws IOException {
        for (final var l : list) {
            if (l.getCardId() == i) {
                return true;
            }
        }
        return false;
    }
}
