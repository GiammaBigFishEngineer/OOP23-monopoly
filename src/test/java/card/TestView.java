package card;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.card.view.TableView;
import app.card.view.UnforseenView;

public class TestView {
    @Test
    public static void main(String[] args) throws java.io.IOException {
        Unbuyable cardUnforseen = new CardFactoryImpl().createStaticCard(2, "UnforseenTest", "unforseen", 0);
        var table = new TableView(7);
        var unforseenAllert = new UnforseenView(cardUnforseen.makeAction(null).get());
        var frame = new JFrame();
        frame.setSize(250*7, 250*7);
        frame.add(unforseenAllert);
        frame.getContentPane().add(table);
        frame.setVisible(true);
    }
}
