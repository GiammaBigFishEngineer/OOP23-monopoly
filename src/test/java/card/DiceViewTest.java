package card;

import app.card.view.DiceView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Test for dice gui.
 */
final class DiceTestView {

    private static final String TITLE = "Lancio dei due dadi";

    private DiceTestView() { }

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(final String[] args) throws java.io.IOException {
        SwingUtilities.invokeLater(() -> {
            final int size = 500;
            final var diceView = new DiceView(size);
            final var frame = new JFrame();
            frame.setSize(size, size);
            frame.setContentPane(diceView);
            frame.setTitle(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
