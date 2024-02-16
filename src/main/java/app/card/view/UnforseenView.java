package app.card.view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The view as popup for the unforseen extraction.
 */
public class UnforseenView extends JPanel {

    private static final long serialVersionUID = 4L;

    /**
     * @param message is the messaged to show
     */
    public UnforseenView(final String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
