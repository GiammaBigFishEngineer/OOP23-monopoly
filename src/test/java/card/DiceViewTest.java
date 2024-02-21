package card;

import app.game.controller.DiceController;
import app.game.utils.Dice;
import app.game.view.DiceView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Test for dice gui {@link app.game.view.DiceView} with dice rolling.
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
            final Dice diceModel = new Dice();
            final DiceController diceController = new DiceController(diceModel);
            final DiceView diceView = new DiceView();

            final JFrame frame = new JFrame();
            frame.setContentPane(diceView);
            frame.setTitle(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);

            diceController.rollDiceAction();
            diceView.updateView(diceModel);
        });
    }
}
