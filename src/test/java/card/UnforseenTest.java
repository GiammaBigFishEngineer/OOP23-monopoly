package card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.card.impl.Unforseen;
import app.player.apii.BankAccount;
import app.player.apii.Player;

/**
 * Test all unforseens.
 */
class UnforseenTest {

    private final class TestLazyPlayer implements Player {

        private int position = -1;
        private final BankAccount bankAccount = new BankAccount() {

                private int balance = -1;

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
    * Test first unforseen.
    */
    @Test
    void testU1Action() {
        final var player = new TestLazyPlayer();
        final int actual = 100;
        Unforseen.U0.getCard().makeAction(player);
        assertEquals(player.getBankAccount().getBalance(), actual - 1);
    }

    /**
    * Test second unforseen.
    */
    @Test
    void testU2Action() {
        final int actual = 15;
        final var player = new TestLazyPlayer();
        Unforseen.U1.getCard().makeAction(player);
        assertEquals(player.getCurrentPosition(), actual);
    }

    /**
    * @param player who's got action
    * @return true if changes are checked
    */
    private boolean checkChanges(final TestLazyPlayer player) {
        /* position and balance start from -1, i check the changes */
        return player.getCurrentPosition() != 1 || player.getBankAccount().getBalance() != -1;
    }

    /**
    * test the first unforseen in table.
    * @throws IOException
    */
    @Test
    void testUseUnforseen() throws IOException {
        final var player = new TestLazyPlayer();
        final var list = new CardFactoryImpl().cardsInitializer();
        final Unbuyable card = (Unbuyable) list.get(2);
        card.makeAction(player);
        assertTrue(checkChanges(player));
    } 

    /**
    * test the extraction of one unforseen.
    * @throws IOException
    */
    @Test
    void testExtractUnforseen() {
        /* eseguo il test per più volte in modo da aumentare la probabilità di trovare errori
         * in quanto unforseen() usa una generazione randomica.
         */
        for (int i = 0; i < 100; i++) {
            final var player = new TestLazyPlayer();
            final var factory = new CardFactoryImpl();
            factory.createStaticCard(factory.createCard(i, "prova"),
                "unforseen",
                0)
                .makeAction(player);
            assertTrue(checkChanges(player));
        }
    }
}
