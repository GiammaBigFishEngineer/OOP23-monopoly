package card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;
import app.card.apii.CardFactory;
import app.card.apii.Unbuyable;
import app.card.impl.CardFactoryImpl;

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
        assertEquals(buildable.getId(), ID_TEST);
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
        assertEquals(buildable.getId(), ID_TEST);
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
        assertEquals(station.getId(), ID_TEST);
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
        final var newPlayer = new TestLazyPlayer();
        staticCard.makeAction(newPlayer);
        assertEquals(AMOUNT_TEST_MONEY - 1, newPlayer.getBankAccount().getBalance()); 
    }

    /**
     * test the card "Prison".
     */
    @Test
    void testStaticCardPrison() {
        final Unbuyable staticCard = this.factory.createStaticCard(
            this.factory.createCard(ID_TEST_PRISON, "prison"),
            "movePlayer", ID_TEST_PRISON);
        final var newPlayer = new TestLazyPlayer();
        staticCard.makeAction(newPlayer);
        assertEquals(staticCard.getId(), newPlayer.getCurrentPosition()); 
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
        for (final var l: list) {
            if (l.getId() == i) {
                return true;
            }
        }
        return false;
    }
}
