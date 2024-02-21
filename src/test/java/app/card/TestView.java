package app.card;

import java.util.List;

import javax.swing.JFrame;

import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.card.view.TableView;
import app.card.view.UnforseenView;
import app.player.impl.PlayerImpl;

/**
 * Test the entire view of table.
 */
final class TestView {

    private TestView() {
    }

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void run(final String[] args) throws java.io.IOException {
        final int size = 250;
        final int side = 7;
        final int buildable = 7;
        final var factory = new CardFactoryImpl();
        final Unbuyable cardUnforseen = factory.createStaticCard(factory.createCard(buildable, "UnforseenTest"),
                "unforseen", 0);
        final var table = new TableView(new CardFactoryImpl().cardsInitializer(), side);
        final var unforseenAllert = new UnforseenView(cardUnforseen.makeAction(
                new PlayerImpl("pl", 0, List.of(), 0))
                .getMessage());
        final var frame = new JFrame();
        frame.setSize(size * side, side * side);
        frame.add(unforseenAllert);
        frame.getContentPane().add(table);
        table.redrawPlayer("#FF0000", buildable);
        table.redrawPlayer("#000", buildable);
        table.redrawPlayer("#ff000", buildable);
        table.removePlayer("#000", buildable);
        frame.setVisible(true);
    }
}
