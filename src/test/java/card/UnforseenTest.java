package card;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import app.card.api.Buildable;
import app.card.api.Buyable;
import app.card.impl.Unforseen;
import app.player.api.BankAccount;
import app.player.api.Player;

public class UnforseenTest {

    private class TestLazyPlayer implements Player{

        private int position = 0;
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
        public void setPosition(int position){
            this.position = position;
        }
        
    }

    @Test
    public void testU1Action(){
        var player = new TestLazyPlayer();
        Unforseen.U1.getCard().makeAction(player);
        assertEquals(player.getBankAccount().getBalance(), 100);
    }

    @Test
    public void testU2Action(){
        var player = new TestLazyPlayer();
        Unforseen.U2.getCard().makeAction(player);
        assertEquals(player.getCurrentPosition(), 15);
    }
}
