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
            public boolean payPlayer(final Player player, final int amount) {
                return true;
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
            public void setBalance(final int balance) {
                this.balance = balance;
            }

            @Override
            public boolean hasBalanceChanged() {
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
    public int getNumberStationOwned() {
        return 0;
    }

    @Override
    public BankAccount getBankAccount() {
        return this.bankAccount;
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
    public int getID() {
        return 0;
    }

    @Override
    public List<Buyable> getBuyableOwned() {
        return List.of();
    }

    @Override
    public boolean hasPositionChanged() {
        return true;
    }

    @Override
    public List<Buildable> getBuildableOwned() {
        return List.of();
    }

    @Override
    public Optional<Integer> getHouseBuilt(Buildable built) {
        return Optional.empty();
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

}