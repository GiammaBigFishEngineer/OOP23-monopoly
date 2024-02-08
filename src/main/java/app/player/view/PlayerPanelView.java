package app.player.view;

<<<<<<< HEAD
/**
 * PlayerPanelView.
 */
public class PlayerPanelView {
=======
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
>>>>>>> origin/Vandi

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import app.player.api.Player;

/**
 * Class which graphically reports the principal pieces 
 * of information of the player who is currently playing.
 */
public class PlayerPanelView extends JPanel {

    private static final String TITLE = "PLAYER'S REPORT";
    private static final int N_ROWS = 6;
    private static final int N_COLS = 1; 
    private static final int PROPORTION = 2; 
    private static final int FONT_SIZE = 20; 
    
    private Player currentPlayer; 

    JPanel playerPanel = new JPanel(); 
    
    /**
     * Constructor.
     */
    public PlayerPanelView() {   
        final Font font = new Font("Arial", Font.BOLD, FONT_SIZE);
        final Font titleFont = new Font("Arial", Font.BOLD, FONT_SIZE * 2);
        final List<String> properties = new LinkedList<>();
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        final TitledBorder border = new TitledBorder(BorderFactory.createLineBorder(Color.BLACK), TITLE, 0, 0, titleFont, Color.RED);

        playerPanel.setBorder(border);
        playerPanel.setLayout(new GridLayout(N_ROWS, N_COLS));
        playerPanel.setBackground(Color.LIGHT_GRAY);
        playerPanel.setFont(font);
        playerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerPanel.setSize(sw / PROPORTION, sh / PROPORTION);
        
        properties.addAll(Arrays.asList( "Name: ", "Id", "Money", "Houses on the current box: ", "Owned boxes: ", "Owned stations: "));
        for (String property : properties) {
            playerPanel.add(new JLabel(property));
        }
    }
}
