package app.game.view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    private JPanel buttonPanel;
    private JButton startButton;
    private JButton quitButton;
    private JButton optionButton;
    private JLabel titleLabel;

    public MenuView() {
        super("GameMenu");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.getContentPane().setBackground(Color.green);
        this.setResizable(false);

        buttonPanel = new JPanel(new GridLayout(3, 1));

        startButton = new JButton("Start");
        buttonPanel.add(startButton);

        quitButton = new JButton("Quit");
        buttonPanel.add(quitButton);

        optionButton = new JButton("Options");
        buttonPanel.add(optionButton);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;

        this.add(buttonPanel);

        this.setVisible(true);
    }
}
