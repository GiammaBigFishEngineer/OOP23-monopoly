package card;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.card.apii.Observer;
import app.card.view.BoxPanelView;
import app.card.view.TableView;
import app.player.apii.Player;

/**
 * Test for ObserverImpl extended by TableView
 */
public class ObserverTableTest {

    private TableView table;
    private final static int SIZE = 7;

    @BeforeEach
    public void init() throws IOException{
        this.table = new TableView(SIZE);
    }

    @Test
    public void testDeleteObserver() {
        final int newPosition = 22;
        Observer<Player> myObs = () -> this.table.redrawPlayer("#fff", newPosition);
        this.table.addObserver(myObs);
        this.table.deleteObserver(myObs);
        this.table.notifyObservers();
        BoxPanelView positionNotified = new BoxPanelView();
        for (var i: this.table.getCells().keySet()) {
            if (this.table.getCells().get(i).getIndex() == newPosition) {
                positionNotified = this.table.getCells().get(i);
                break;
            }
        }
        assertTrue(positionNotified.getPlayersComponents().length == 0);
        assertTrue(positionNotified.getPlayerList().size() == 0);
    }

    @Test
    public void testNotifyAll(){
        final int newPosition = 10;
        this.table.addObserver(() -> table.redrawPlayer("#0000", newPosition));
        this.table.addObserver(() -> table.redrawPlayer("#ffff", newPosition));
        this.table.notifyObservers();
        BoxPanelView positionNotified = new BoxPanelView();
        for (var i: this.table.getCells().keySet()) {
            if (this.table.getCells().get(i).getIndex() == newPosition) {
                positionNotified = this.table.getCells().get(i);
                break;
            }
        }
        assertTrue(positionNotified.getPlayersComponents().length > 0);
        assertTrue(positionNotified.getPlayerList().size() > 0);
    }
}
