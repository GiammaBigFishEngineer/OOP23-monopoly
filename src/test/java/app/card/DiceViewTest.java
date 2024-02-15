package app.card;

import app.card.view.DiceView;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.SwingUtilities;

/**
 * Test for dice gui.
 */
final class DiceViewTest {

    private static final String TITLE = "Lancio dei due dadi";
    private static final int MIN_DIM = 600;

    private DiceViewTest() { }

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void run(final String[] args) throws java.io.IOException {
        SwingUtilities.invokeLater(() -> {
            final var diceView = new DiceView();
            final var frame = new JFrame();

            frame.setMinimumSize(new Dimension(MIN_DIM, MIN_DIM));
            frame.setContentPane(diceView);
            frame.setTitle(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
