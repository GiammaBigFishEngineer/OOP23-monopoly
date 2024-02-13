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

    @Override
    public int getCurrentPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentPosition'");
    }

    @Override
    public void setPosition(int position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPosition'");
    }

    @Override
    public boolean hasPositionChanged() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasPositionChanged'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
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
    public BankAccount getBankAccount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBankAccount'");
    }

    @Override
    public List<Buyable> getBuyableOwned() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBuyableOwned'");
    }

    @Override
    public List<Buildable> getBuildableOwned() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBuildableOwned'");
    }

    @Override
    public Optional<Integer> getHouseBuilt(Buildable built) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHouseBuilt'");
    }

    @Override
    public int getNumberStationOwned() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNumberStationOwned'");
    }

    @Override
    public void buyBox(Buyable box) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buyBox'");
    }

    @Override
    public void buildHouse(Buildable box) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildHouse'");
    }

    @Override
    public void sellBuyable(Buyable box) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sellBuyable'");
    }

}
