package app.game.view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * View for saved data of different games.
 */
public class SavedGamesView extends JDialog {

    private static final long serialVersionUID = 1L;
    private static final int FONT_SIZE = 14;
    private static final int BORDER = 10;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    /**
     * Constructor of SavedGamesView.
     * 
     * @param title the title
     * @param dataContent the content to be displayed
     */
    public SavedGamesView(final String title, final String dataContent) {
        this.setTitle(title);

        final JTextArea textArea = new JTextArea(dataContent);
        textArea.setEditable(false);
        final Font textAreaFont = new Font("Verdana", Font.PLAIN, FONT_SIZE);
        textArea.setFont(textAreaFont);
        final JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));

        this.add(scrollPane);
        this.setContentPane(scrollPane);
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
