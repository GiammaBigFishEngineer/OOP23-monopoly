package app.game.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import app.game.apii.MenuController;
import app.game.apii.SaveController;
import app.game.controller.SaveControllerImpl;
import app.game.model.MenuControllerImpl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class represent the view of the game menu.
 */
public final class MenuView extends JFrame {

    private static final long serialVersionUID = 12L;
    private static final Logger LOGGER = Logger.getLogger(MenuView.class.getName());

    private static final int PROPORTION = 2;
    private static final int TITLE_FONT = 100;
    private static final int SUBTITLE_FONT = 30;

    private static final int VERTICAL_SPACE = 40;

    private final transient MenuController menuController;
    private final transient SaveController saveLogic;

    /**
     * Constructor.
     */
    public MenuView() {
        super("GameMenu");

        final JPanel buttonPanel;
        final JButton startButton;
        final JButton quitButton;
        final JButton optionButton;
        final JLabel titleLabel;
        final JLabel subtitleLabel;

        final Dimension screen;
        final int screenWidth;
        final int screenHeight;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);

        menuController = new MenuControllerImpl();
        saveLogic = new SaveControllerImpl();

        /*
         * Button Panel, it will contains all the buttons
         */
        buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.setBackground(Color.white);

        final Font boldFont = new Font("Arial", Font.BOLD, 25);

        /*
         * Start Button
         */

        startButton = new JButton("Avvia Partita");
        startButton.setFont(boldFont);

        startButton.addActionListener(e -> {

            final SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3, 2, 5, 1);
            final JSpinner numPlayerSpinner = new JSpinner(spinnerModel);

            final int choice = JOptionPane.showOptionDialog(this,
                    numPlayerSpinner,
                    "Seleziona numero giocatori",
                    JOptionPane.OK_OPTION, JOptionPane.NO_OPTION,
                    null,
                    null,
                    null);

            if (choice == JOptionPane.OK_OPTION) {

                final int numPlayers = (int) numPlayerSpinner.getValue();

                final List<String> playerNames = new ArrayList<>();

                for (int i = 1; i <= numPlayers; i++) {
                    final String playerName = JOptionPane.showInputDialog("Inserisci nome giocatore " + i + " :");
                    playerNames.add(playerName);
                }

                if (menuController.startGame(playerNames)) {
                    JOptionPane.showMessageDialog(this, "Caricamento Completato");

                    try {
                        new GameView(playerNames);
                        dispose();
                    } catch (IOException e1) {

                        LOGGER.severe("Errore : " + e1.getMessage());
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Errore nel Caricamento");
                }

            }

        });

        buttonPanel.add(startButton);

        /*
         * Option Button
         */

        optionButton = new JButton("Visualizza Salvataggi");
        optionButton.setFont(boldFont);

        optionButton.addActionListener(e -> {
            saveLogic.viewSavedGames();
        });

        buttonPanel.add(optionButton);

        /*
         * Quit Button
         */

        quitButton = new JButton("Esci");
        quitButton.setFont(boldFont);

        quitButton.addActionListener(e -> {
            menuController.quitGame();
        });

        buttonPanel.add(quitButton);

        /*
         * Menu Layout
         */

        setLayout(new GridBagLayout());

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(VERTICAL_SPACE, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 2;

        this.add(buttonPanel, gbc);

        titleLabel = new JLabel("<html> MONOPOLY <html>");
        titleLabel.setFont(new Font("", Font.BOLD, TITLE_FONT));
        titleLabel.setForeground(Color.red);

        gbc.gridx = 0;
        gbc.gridy = 0;

        this.add(titleLabel, gbc);

        subtitleLabel = new JLabel("<html> 'Edizione Rimini' <html>");
        subtitleLabel.setFont(new Font("", Font.BOLD, SUBTITLE_FONT));
        subtitleLabel.setForeground(Color.red);

        gbc.gridx = 0;
        gbc.gridy = 1;

        this.add(subtitleLabel, gbc);

        /*
         * Setting the Menu dimension
         */

        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = (int) screen.getHeight();
        screenWidth = (int) screen.getWidth();

        setSize(screen);

        setMinimumSize(new Dimension(screenWidth / PROPORTION, screenHeight / PROPORTION));

        this.setVisible(true);
    }
}
