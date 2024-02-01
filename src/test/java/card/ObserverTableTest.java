package card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.card.apii.Observer;
import app.card.view.BoxPanelView;
import app.card.view.TableView;
import app.player.apii.Player;

/**
 * Test for ObserverImpl extended by TableView.
 */
class ObserverTableTest {

    private TableView table;
    private static final int SIZE = 7;

    /**
     * Render Table.
     * @throws IOException
     */
    @BeforeEach
    void init() throws IOException {
        this.table = new TableView(SIZE);
    }

    /**
     * Test method deleteObserver and addObserver.
     */
    @Test
    void testDeleteObserver() {
        final int newPosition = 22;
        final Observer<Player> myObs = () -> this.table.redrawPlayer("#fff", newPosition);
        this.table.addObserver(myObs);
        this.table.deleteObserver(myObs);
        this.table.notifyObservers();
        BoxPanelView positionNotified = new BoxPanelView();
        for (final var i: this.table.getCells().keySet()) {
            if (this.table.getCells().get(i).getIndex() == newPosition) {
                positionNotified = this.table.getCells().get(i);
                break;
            }
        }
        assertEquals(positionNotified.getPlayersComponents().length, 0);
        assertEquals(positionNotified.getPlayerList().size(), 0);
    }

    /**
     * Test method notifyAll.
     */
    @Test
    void testNotifyAll() {
        final int newPosition = 10;
        this.table.addObserver(() -> table.redrawPlayer("#0000", newPosition));
        this.table.addObserver(() -> table.redrawPlayer("#ffff", newPosition));
        this.table.notifyObservers();
        BoxPanelView positionNotified = new BoxPanelView();
        for (final var i: this.table.getCells().keySet()) {
            if (this.table.getCells().get(i).getIndex() == newPosition) {
                positionNotified = this.table.getCells().get(i);
                break;
            }
        }
        assertTrue(positionNotified.getPlayersComponents().length > 0);
        assertTrue(positionNotified.getPlayerList().size() > 0);
    }
}
