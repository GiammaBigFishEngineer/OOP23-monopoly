package app.card.view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import app.card.impl.Unforseen;

/**
 * The view as popup for the unforseen extraction.
 */
public class UnforseenView extends JPanel {

    private static final long serialVersionUID = 4L;

    /**
     * @param card is the unforseen extracted
     */
    public UnforseenView(final Unforseen card) {
        JOptionPane.showMessageDialog(null, card.getDescription());
    }
}
