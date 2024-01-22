package app.game.view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    private JPanel buttonPanel;
    private JButton startButton;
    private JButton quitButton;
    private JButton optionButton;
    private JLabel titleLabel;

    final Dimension screen;
    final int screenWidth;
    final int screenHeight;
    private final int PROPORTION = 2;

    public MenuView() {
        super("GameMenu");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
        this.setResizable(false);

        buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.setBackground(Color.white);

        Font boldFont = new Font("Arial", Font.BOLD, 25);

        startButton = new JButton("Start");
        startButton.setFont(boldFont);

        buttonPanel.add(startButton);

        quitButton = new JButton("Quit");
        quitButton.setFont(boldFont);

        buttonPanel.add(quitButton);

        optionButton = new JButton("Options");
        optionButton.setFont(boldFont);

        buttonPanel.add(optionButton);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;

        this.add(buttonPanel, gbc);

        titleLabel = new JLabel("MONOPOLY");
        titleLabel.setFont(new Font("", Font.BOLD, 100));
        titleLabel.setForeground(Color.red);

        gbc.gridx = 0;
        gbc.gridy = 0;

        this.add(titleLabel, gbc);

        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = (int) screen.getHeight();
        screenWidth = (int) screen.getWidth();

        setSize(screenWidth / PROPORTION, screenHeight / PROPORTION);

        this.setVisible(true);
    }
}
