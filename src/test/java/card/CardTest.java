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

public class CardTest {

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

    private CardFactory factory;

	@BeforeEach
	public void init() {
		this.factory = new CardFactoryImpl();
	}

    @Test
    public void testEmptyProperty(){
        Buildable buildable = this.factory.createProperty(0, null, 0, 0, 0);
        assertEquals(buildable.getHousePrice(), 0);
        assertEquals(buildable.getId(), 0);
        assertEquals(buildable.getName(), null);
        assertEquals(buildable.getPrice(), 0);
    }

    @Test
    public void testProperty(){
        Buildable buildable = this.factory.createProperty(5, "prova", 10, 10, 0);
        assertEquals(buildable.getHousePrice(), 10);
        assertEquals(buildable.getId(), 5);
        assertEquals(buildable.getName(), "prova");
        assertEquals(buildable.getPrice(), 10);
    }

    @Test
    public void testPropertyEmptyOwner(){
        Buildable buildable = this.factory.createProperty(5, "prova", 10, 10, 0);
        assertFalse(buildable.isOwned());
    }

    @Test
    public void testStation(){
        Buyable station = this.factory.createStation(5, "North", 10, 10);
        assertEquals(station.getId(), 5);
        assertEquals(station.getName(), "North");
        assertEquals(station.getPrice(), 10);
    }

    @Test
    public void testStaticCardGo(){
        Unbuyable staticCard = this.factory.createStaticCard(0, "Go", "giveMoneyPlayer",200);
        var newPlayer = new TestLazyPlayer();
        staticCard.makeAction(newPlayer);
        assertEquals(200, newPlayer.getBankAccount().getBalance()); 
    }

    @Test
    public void testStaticCardPrison(){
        Unbuyable staticCard = this.factory.createStaticCard(10, "prison", "movePlayer",10);
        var newPlayer = new TestLazyPlayer();
        staticCard.makeAction(newPlayer);
        assertEquals(staticCard.getId(), newPlayer.getCurrentPosition()); 
    }

    @Test
    public void testInitializer() throws IOException{
        var list = this.factory.cardsInitializer();
        for(int i=0; i<list.size(); i++){
            /* controllo che ogni indice sia giusto e crescente */
            assertEquals(list.get(i).getId(), i);
        }
    }
}
