package app.player.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import app.card.api.Card;
import app.player.api.Player;
import app.player.api.PlayerPanelLogic;
import app.player.impl.PlayerPanelLogicImpl;

/**
 * Class which graphically reports the principal pieces 
 * of information of the player who is currently playing.
 */
public final class PlayerPanelView extends JPanel {

    private static final String TITLE = "PLAYER'S REPORT";
    private static final long serialVersionUID = 1L;
    private static final int N_ROWS = 6;
    private static final int N_COLS = 2; 
    private static final int PROPORTION = 2; 
    private static final int FONT_SIZE = 20;

    private final PlayerPanelLogic logic;

    private final JLabel playerName = new JLabel(); 
    private final JLabel playerID = new JLabel(); 
    private final JLabel playerMoney = new JLabel(); 
    private final JLabel playerBoxes = new JLabel(); 
    private final JLabel playerHouses = new JLabel(); 
    private final JLabel playerStations = new JLabel();
    /**
     * Constructor.
     * @param player
     * @param currentBox
     */
    public PlayerPanelView(final Player player, final Card currentBox) {
        super();
        final Font font = new Font("Arial", Font.BOLD, FONT_SIZE);
        final Font titleFont = new Font("Arial", Font.BOLD, FONT_SIZE * 2);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        final TitledBorder border = new TitledBorder(BorderFactory.createLineBorder(Color.BLACK), TITLE,
        0, 0, titleFont, Color.RED);

        this.logic = new PlayerPanelLogicImpl(player, currentBox, this);

        this.setBorder(border);
        this.setLayout(new GridLayout(N_ROWS, N_COLS));
        this.setBackground(Color.LIGHT_GRAY);
        this.setFont(font);
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setSize(sw / PROPORTION, sh / PROPORTION);

        addLabelWithText(new JLabel("Player's name: "), this.playerName);
        addLabelWithText(new JLabel("Player's ID: "), this.playerID);
        addLabelWithText(new JLabel("Money on BankAccount: "), this.playerMoney); 
        addLabelWithText(new JLabel("Houses on this box: "), this.playerHouses);
        addLabelWithText(new JLabel("Owned stations: "), this.playerStations);
        addLabelWithText(new JLabel("Owned boxes: "), this.playerBoxes);
    }

    private void addLabelWithText(final JLabel jLabel, final JLabel text) {
        this.add(jLabel);
        this.add(text);
    }

    /**
     * Getter for PlayerPanelLogic.
     * @return object PlayerPanelLogic
     */
    public PlayerPanelLogic getLogic() {
        return this.logic;
    }

    /**
     * Getter for Player's name.
     * @return JLabel
     */
    public JLabel getPlayerName() {
        return this.playerName;
    }

    /**
     * Getter for Player's ID.
     * @return JLabel
     */
    public JLabel getPlayerID() {
        return this.playerID;
    }

    /**
     * Getter for Player's money.
     * @return JLabel
     */
    public JLabel getPlayerMoney() {
        return this.playerMoney;
    }

    /**
     * Getter for Player's owned boxes. 
     * @return object JLabel associated with Player's owned boxes.
     */
    public JLabel getPlayerBoxes() {
        return this.playerBoxes;
    }

    /**
     * Getter for Player's houses built on the current box.
     * @return JLabel
     */
    public JLabel getPlayerHouses() {
        return this.playerHouses;
    }

    /**
     * Getter for Player's owned stations.
     * @return JLabel
     */
    public JLabel getPlayerStations() {
        return this.playerStations;
    }
}
