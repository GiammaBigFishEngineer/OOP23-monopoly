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

import app.card.apii.Card;
import app.player.apii.Player;
import app.player.apii.PlayerPanelLogic;
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

    private final transient PlayerPanelLogic logic;

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

    /**
     * This method has been created to avoid doing for each JLabel the operation 
     * of adding to the JPanel the JLabel with the string "Player's info" and
     * the JLabel with the effective values. 
     * @param label
     * @param text
     */
    private void addLabelWithText(final JLabel label, final JLabel text) {
        this.add(label);
        this.add(text);
    }

    /**
     * @param text
     */
    public void setPlayerNameText(final String text) {
        this.playerName.setText(text);
    }

    /**
     * @param text
     */
    public void setPlayerMoneyText(final String text) {
        this.playerMoney.setText(text);
    }

    /**
     * @param text
     */
    public void setPlayerIDText(final String text) {
        this.playerID.setText(text);
    }

    /**
     * @param text
     */
    public void setPlayerBoxesText(final String text) {
        this.playerBoxes.setText(text);
    }
    /**
     * @param text
     */
    public void setPlayerHousesText(final String text) {
        this.playerHouses.setText(text);
    }

    /**
     * @param text
     */
    public void setPlayerStationsText(final String text) {
        this.playerStations.setText(text);
    }

    /**
     * In order to avoid returning a defensive copy of PlayerPanelLogic logic, 
     * the following methods invoke the methods of the logic (which need to be done to update
     * the playerPanelView) directly on the object logic,
     * without doing playerPanelView.getLogic().setPlayer(player, currentBox).
     * @param player
     * @param currentBox
     */
    public void setPlayer(final Player player, final Card currentBox) {
        this.logic.setPlayer(player, currentBox);
    }

    /**
     * @param currentBox
     */
    public void setCurrentBox(final Card currentBox) {
        this.logic.setCurrentBox(currentBox);
    }

    /**
     * Method which updates values on the panel.
     */
    public void refresh() {
        this.logic.refresh();
    }
}
