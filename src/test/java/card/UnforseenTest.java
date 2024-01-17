package card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import app.card.api.Buildable;
import app.card.api.Buyable;
import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.card.impl.Unforseen;
import app.card.utils.StaticActions;
import app.player.api.BankAccount;
import app.player.api.Player;

public class UnforseenTest {

    private class TestLazyPlayer implements Player {

        private int position = -1;
        private BankAccount bankAccount = new BankAccount() {
                
                private int balance = 0;

                @Override
                public int getBalance() {
                    return this.balance;
                }

                @Override
                public void payPlayer(Player player, int amount) {
                    
                }

                @Override
                public void receivePayment(int amount) {
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
        public void buyBox(Buyable box) {
        }
    
        @Override
        public void buildHouse(Buildable box) {
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
        public void sellBuyable(Buyable box) {
        }
    
        @Override
        public int getHouseBuilt(Buildable built) {
            return 0;
        }

        @Override
        public void setPosition(int position) {
            this.position = position;
        }
        
    }

    @Test
    public void testU1Action() {
        var player = new TestLazyPlayer();
        Unforseen.U0.getCard().makeAction(player);
        assertEquals(player.getBankAccount().getBalance(), 100);
    }

    @Test
    public void testU2Action() {
        var player = new TestLazyPlayer();
        Unforseen.U1.getCard().makeAction(player);
        assertEquals(player.getCurrentPosition(), 15);
    }

    private boolean checkChanges(TestLazyPlayer player) {
        return player.getCurrentPosition() != 0 || player.getBankAccount().getBalance() != -1;
    }

    @Test
    public void testUseUnforseen() throws IOException {
        var player = new TestLazyPlayer();
        var list = new CardFactoryImpl().cardsInitializer();
        Unbuyable card = (Unbuyable)list.get(2);
        card.makeAction(player);
        assertTrue(checkChanges(player));
    } 

    @Test
    public void testExtractUnforseen() {
        /* eseguo il test per più volte in modo da aumentare la probabilità di trovare errori
         * in quanto unforseen() usa una generazione randomica.
         */
        for(int i=0; i<100; i++) {
            var player = new TestLazyPlayer();
            Unforseen unforseen = StaticActions.unforseen(player);
            unforseen.getCard().makeAction(player);
            assertTrue(checkChanges(player));
        }
        
    }
}
