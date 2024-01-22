package card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import app.card.api.Buildable;
import app.card.api.Buyable;
import app.card.api.CardFactory;
import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.player.api.BankAccount;
import app.player.api.Player;

/**
 * Test for all cards.
 */
public class CardTest {

    private final class TestLazyPlayer implements Player {

        private int position = 0;
        private BankAccount bankAccount = new BankAccount() {

                private int balance = 0;

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
                    System.out.println(this.getBalance());
                }

                @Override
                public Boolean isPaymentAllowed() {
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
            return null;
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

    private CardFactory factory;
    private final int idTest = 5;
    private final int idTestGoToPrison = 18;
    private final int idTestGo = 0;
    private final int amountTestMoney = 200;
    private final int amountTestPrice = 10;

    /**
    * inizitalize factory.
    */
    @BeforeEach
    public void init() {
        this.factory = new CardFactoryImpl();
    }

    /**
    * test empty property.
    */
    @Test
    public void testEmptyProperty() {
        final Buildable buildable = this.factory.createProperty(idTestGo, null, 0, 0, 0);
        assertEquals(buildable.getHousePrice(), 0);
        assertEquals(buildable.getId(), 0);
        assertEquals(buildable.getName(), null);
        assertEquals(buildable.getPrice(), 0);
    }

    /**
    * test normal property.
    */
    @Test
    public void testProperty() {
        final Buildable buildable = this.factory.createProperty(idTest, "prova", amountTestPrice, amountTestPrice, 0);
        assertEquals(buildable.getHousePrice(), amountTestPrice);
        assertEquals(buildable.getId(), idTest);
        assertEquals(buildable.getName(), "prova");
        assertEquals(buildable.getPrice(), 10);
    }

    /**
    * test normal property with no owner.
    */
    @Test
    public void testPropertyEmptyOwner() {
        final Buildable buildable = this.factory.createProperty(idTest, "prova", amountTestPrice, amountTestPrice, 0);
        assertFalse(buildable.isOwned());
    }

    /**
    * test normal station.
    */
    @Test
    public void testStation() {
        final Buyable station = this.factory.createStation(idTest, "North", amountTestPrice, amountTestPrice);
        assertEquals(station.getId(), idTest);
        assertEquals(station.getName(), "North");
        assertEquals(station.getPrice(), amountTestPrice);
    }

    /**
    * test the first card "GO".
    */
    @Test
    public void testStaticCardGo() {
        final Unbuyable staticCard = this.factory.createStaticCard(idTestGo, "Go", "giveMoneyPlayer", amountTestMoney);
        final var newPlayer = new TestLazyPlayer();
        staticCard.makeAction(newPlayer);
        assertEquals(amountTestMoney, newPlayer.getBankAccount().getBalance()); 
    }

    /**
    * test the card "Prison".
    */
    @Test
    public void testStaticCardPrison() {
        final Unbuyable staticCard = this.factory.createStaticCard(idTestGoToPrison, "prison", "movePlayer", idTestGoToPrison);
        final var newPlayer = new TestLazyPlayer();
        staticCard.makeAction(newPlayer);
        assertEquals(staticCard.getId(), newPlayer.getCurrentPosition()); 
    }

    /**
    * test the inizialitation of table.
    */
    @Test
    public void testInitializer() throws IOException {
        final var list = this.factory.cardsInitializer();
        for (int i = 0; i < list.size(); i++) {
            /* controllo che ogni indice sia giusto e crescente */
            assertEquals(list.get(i).getId(), i);
        }
    }
}
