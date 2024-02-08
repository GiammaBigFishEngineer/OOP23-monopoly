package card;

import javax.swing.JFrame;

import app.card.apii.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.card.view.TableView;
import app.card.view.UnforseenView;

/**
 * Test the entire view of table.
 */
final class TestView {

    private TestView() { }

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void run(final String[] args) throws java.io.IOException {
        final int size = 250;
        final int side = 7;
        final int buildable = 7;
        final var factory = new CardFactoryImpl();
        final Unbuyable cardUnforseen = factory.createStaticCard(factory.createCard(buildable, "UnforseenTest"), "unforseen", 0);
        final var table = new TableView(side);
        final var unforseenAllert = new UnforseenView(cardUnforseen.makeAction(null).get());
        final var frame = new JFrame();
        frame.setSize(size * side, side * side);
        frame.add(unforseenAllert);
        frame.getContentPane().add(table);
        table.redrawPlayer("#52ff", buildable);
        table.redrawPlayer("#000", buildable);
        table.redrawPlayer("#ff000", buildable);
        table.removePlayer("#000", buildable);
        frame.setVisible(true);
    }
}
