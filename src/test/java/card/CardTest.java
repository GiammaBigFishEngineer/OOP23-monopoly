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
import app.player.apii.BankAccount;
import app.player.apii.Player;

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
    private final class TestLazyPlayer implements Player {

        private int position;
        private final BankAccount bankAccount = new BankAccount() {

                private int balance;

                @Override
                public int getBalance() {
                    return this.balance;
                }

                @Override
                public void payPlayer(final Player player, final int amount) {
                }

                @Override
                public void receivePayment(final int amount) {
                    this.balance = this.balance + amount;
                }

                @Override
                public boolean isPaymentAllowed(final int amount) {
                    return true;
                }
        };

        @Override
        public int getCurrentPosition() {
            return this.position;
        }

        @Override
        public String getName() {
            return "test";
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public void buyBox(final Buyable box) {
        }

        @Override
        public void buildHouse(final Buildable box) {
        }

        @Override
        public int getNumberStationOwned() {
            return 0;
        }

        @Override
        public BankAccount getBankAccount() {
            return this.bankAccount;
        }

        @Override
        public List<Buyable> getBuildableOwned() {
            return List.of();
        }

        @Override
        public void sellBuyable(final Buyable box) {
        }

        @Override
        public int getHouseBuilt(final Buildable built) {
            return 0;
        }

        @Override
        public void setPosition(final int position) {
            this.position = position;
        }

    }

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
        final Buildable buildable = this.factory.createProperty(ID_TEST_GO, null, 0, 0, 0);
        assertEquals(buildable.getHousePrice(), 0);
        assertEquals(buildable.getId(), 0);
        assertEquals(buildable.getName(), null);
        assertEquals(buildable.getPrice(), 0);
    }

    /**
     * test normal property.
     */
    @Test
    void testProperty() {
        final Buildable buildable = this.factory.createProperty(ID_TEST, "prova", AMOUNT_TEST_PRICE, AMOUNT_TEST_PRICE, 0);
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
        final Buildable buildable = this.factory.createProperty(ID_TEST, "prova", AMOUNT_TEST_PRICE, AMOUNT_TEST_PRICE, 0);
        assertFalse(buildable.isOwned());
    }

    /**
     * test normal station.
     */
    @Test
    void testStation() {
        final Buyable station = this.factory.createStation(ID_TEST, "North", AMOUNT_TEST_PRICE, AMOUNT_TEST_PRICE);
        assertEquals(station.getId(), ID_TEST);
        assertEquals(station.getName(), "North");
        assertEquals(station.getPrice(), AMOUNT_TEST_PRICE);
    }

    /**
     * test the first card "GO".
     */
    @Test
    void testStaticCardGo() {
        final Unbuyable staticCard = this.factory.createStaticCard(ID_TEST_GO, "Go", "giveMoneyPlayer", AMOUNT_TEST_MONEY);
        final var newPlayer = new TestLazyPlayer();
        staticCard.makeAction(newPlayer);
        assertEquals(AMOUNT_TEST_MONEY, newPlayer.getBankAccount().getBalance()); 
    }

    /**
     * test the card "Prison".
     */
    @Test
    void testStaticCardPrison() {
        final Unbuyable staticCard = this.factory.createStaticCard(ID_TEST_PRISON, "prison", "movePlayer", ID_TEST_PRISON);
        final var newPlayer = new TestLazyPlayer();
        staticCard.makeAction(newPlayer);
        assertEquals(staticCard.getId(), newPlayer.getCurrentPosition()); 
    }

    /**
     * test the inizialitation of table.
     * the list is not sorted by ascending id, therefore I check that each id is 
     * contained between 0 and 24.
     */
    @Test
    void testInitializer() throws IOException {
        final List<Card> list = this.factory.cardsInitializer();
        final int finalSize = 24;
        for (int i = 0; i < finalSize; i++) {
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
