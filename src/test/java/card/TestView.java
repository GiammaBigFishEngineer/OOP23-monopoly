package card;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.card.view.TableView;
import app.card.view.UnforseenView;

/**
 * Test the entire view of table.
 */
public final class TestView {

    private TestView() { }
    /**
    * @param args
    * @throws java.io.IOException
    */
    @Test
    public static void main(final String[] args) throws java.io.IOException {
        final int size = 250;
        final int side = 7;
        final int buildable = 7;
        final Unbuyable cardUnforseen = new CardFactoryImpl().createStaticCard(buildable, "UnforseenTest", "unforseen", 0);
        final var table = new TableView(side);
        final var unforseenAllert = new UnforseenView(cardUnforseen.makeAction(null).get());
        final var frame = new JFrame();
        frame.setSize(size * side, side * side);
        frame.add(unforseenAllert);
        frame.getContentPane().add(table);
        frame.setVisible(true);
    }
}
