package app.game.view;

import javax.swing.*;

import app.game.apii.MenuController;
import app.game.apii.SaveController;
import app.game.controller.SaveControllerImpl;
import app.game.model.MenuControllerImpl;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class MenuView extends JFrame {

    private static final int PROPORTION = 2;

    final private MenuController menuController;
    final private SaveController saveLogic;

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
         * Creating a panel with all the buttons
         */

        buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.setBackground(Color.white);

        final Font boldFont = new Font("Arial", Font.BOLD, 25);

        /*
         * Start Button
         */

        startButton = new JButton("Start");
        startButton.setFont(boldFont);

        startButton.addActionListener(e -> {

            final SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3, 2, 5, 1);
            final JSpinner numPlayerSpinner = new JSpinner(spinnerModel);

            final int choice = JOptionPane.showOptionDialog(this,
                    numPlayerSpinner,
                    "Select player number",
                    JOptionPane.OK_OPTION, JOptionPane.NO_OPTION,
                    null,
                    null,
                    null);

            if (choice == JOptionPane.OK_OPTION) {

                final int numPlayers = (int) numPlayerSpinner.getValue();

                final List<String> playerNames = new ArrayList<>();

                for (int i = 0; i < numPlayers; i++) {
                    final String playerName = JOptionPane.showInputDialog("Insert player Name : ");
                    playerNames.add(playerName);
                }

                if (menuController.startGame(playerNames)) {
                    JOptionPane.showMessageDialog(this, "Game loading success");

                    try {
                        new GameView(playerNames);
                        dispose();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Game loading error");
                }

            }

        });

        buttonPanel.add(startButton);

        /*
         * Options button
         */

        optionButton = new JButton("Saved Games");
        optionButton.setFont(boldFont);

        optionButton.addActionListener(e -> {
            saveLogic.viewSavedGames();
        });

        buttonPanel.add(optionButton);

        /*
         * Quit button
         */

        quitButton = new JButton("Quit");
        quitButton.setFont(boldFont);

        quitButton.addActionListener(e -> {
            menuController.quitGame();
        });

        buttonPanel.add(quitButton);

        /*
         * adding every component to the frame
         */

        setLayout(new GridBagLayout());

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 2;

        this.add(buttonPanel, gbc);

        titleLabel = new JLabel("<html> MONOPOLY <html>");
        titleLabel.setFont(new Font("", Font.BOLD, 100));
        titleLabel.setForeground(Color.red);

        gbc.gridx = 0;
        gbc.gridy = 0;

        this.add(titleLabel, gbc);

        subtitleLabel = new JLabel("<html> 'Rimini Edition' <html>");
        subtitleLabel.setFont(new Font("", Font.BOLD, 30));
        subtitleLabel.setForeground(Color.red);

        gbc.gridx = 0;
        gbc.gridy = 1;

        this.add(subtitleLabel, gbc);

        /*
         * Setting the timension of the frame
         */

        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = (int) screen.getHeight();
        screenWidth = (int) screen.getWidth();

        setSize(screen);

        setMinimumSize(new Dimension(screenWidth / PROPORTION, screenHeight / PROPORTION));

        this.setVisible(true);
    }
}
