package card;

import app.card.view.DiceView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Test for dice gui with dice rolling.
 */
final class DiceViewTest {

    private static final String TITLE = "Lancio dei due dadi";

    private DiceViewTest() { }

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(final String[] args) throws java.io.IOException {
        SwingUtilities.invokeLater(() -> {
            final var diceView = new DiceView();
            final var frame = new JFrame();

            diceView.rollAction();

            frame.setContentPane(diceView);
            frame.setTitle(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
