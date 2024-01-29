package card;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.player.apii.AlreadyBuyedBoxException;
import app.player.apii.BankAccount;
import app.player.apii.NotEnoughMoneyException;
import app.player.apii.Player;
import java.util.List;

/**
 * this is a LazyPlayer only for test.
 */
public final class TestLazyPlayer implements Player {

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
    public void buyBox(Buyable box) throws AlreadyBuyedBoxException, NotEnoughMoneyException {
    }

    @Override
    public void buildHouse(Buildable box) throws IllegalArgumentException {
    }

    @Override
    public void sellBuyable(Buyable box) {
    }

    @Override
    public int getHouseBuilt(Buildable built) {
        return 0;
    }

}
