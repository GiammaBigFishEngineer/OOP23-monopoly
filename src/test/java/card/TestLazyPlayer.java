package card;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;
import app.player.apii.BankAccount;
import app.player.apii.Player;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * this is a LazyPlayer only for test.
 */
final class TestLazyPlayer implements Player {

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

        @Override
        public void setBalance(int balance) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setBalance'");
        }

        @Override
        public boolean hasBalanceChanged() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasBalanceChanged'");
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
    public void setPosition(final int position) {
        this.position = position;
    }

    @Override
    public void buyBox(final Buyable box) {
    }

    @Override
    public void buildHouse(final Buildable box) {
    }

    @Override
    public void sellBuyable(final Buyable box) {
    }

    @Override
    public int getHouseBuilt(final Buildable built) {
        return 0;
    }

    @Override
    public boolean hasPositionChanged() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasPositionChanged'");
    }

    @Override
    public int getID() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getID'");
    }

    @Override
    public Map<Card, Optional<Integer>> getMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMap'");
    }

    @Override
    public boolean isInJail() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isInJail'");
    }

    @Override
    public void setInJail(boolean isInJail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInJail'");
    }

    @Override
    public List<Buyable> getBuyableOwned() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBuyableOwned'");
    }

}
