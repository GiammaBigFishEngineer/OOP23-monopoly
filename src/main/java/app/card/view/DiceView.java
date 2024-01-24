package app.card.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceView extends JFrame {

    private static final int DICE_SIZE = 175;
    private static final int TIMER_DELAY = 100;
    private static final int FONT_SIZE = 16;
    private static final int TOP_PADDING = 10;
    private static final int BOTTOM_PADDING = 10;
    private static final int DOT_SIZE = 20;
    private static final int SPACING = 40;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 6;

    private JLabel resultLabel;
    private DicePanel dicePanel1;
    private DicePanel dicePanel2;

    public DiceView(final int size) {
        this.setTitle("Lancio dei 2 dadi");

        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        final JButton rollButton = new JButton("Lancia i dadi");
        final Font buttonFont = new Font("Verdana", Font.BOLD, FONT_SIZE);
        rollButton.setFont(buttonFont);

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBorder(BorderFactory.createEmptyBorder(TOP_PADDING, 0, 0, 0));
        topPanel.add(rollButton);
        panel.add(topPanel, BorderLayout.NORTH);

        dicePanel1 = new DicePanel();
        dicePanel2 = new DicePanel();
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 10);
        centerPanel.add(dicePanel1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        centerPanel.add(dicePanel2, gbc);
        panel.add(centerPanel, BorderLayout.CENTER);

        resultLabel = new JLabel("Risultato: ___");
        final Font resultFont = new Font("Verdana", Font.PLAIN, FONT_SIZE);
        resultLabel.setFont(resultFont);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, BOTTOM_PADDING, 0));
        bottomPanel.add(resultLabel);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                roll();
            }
        });

        this.setContentPane(panel);
        this.setSize(size, size);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void roll() {
        dicePanel1.numberGeneration();
        dicePanel2.numberGeneration();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int res = getDiceResult();
                resultLabel.setText("Risultato: " + res);
            }
        });
    }

    public int getDiceResult() {
        return dicePanel1.getResult() + dicePanel2.getResult();
    }

    private class DicePanel extends JPanel {
        private int result;
        private Timer timer;
        private final Random randomNumber = new Random();

        public DicePanel() {
            this.result = 1; 
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(DICE_SIZE, DICE_SIZE));

            this.timer = new Timer(TIMER_DELAY, new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    rollAnimation();
                }
            });
        }

        private void numberGeneration() {
            result = randomNumber.nextInt(MAX_NUMBER) + 1;
            if (result < MIN_NUMBER || result > MAX_NUMBER) {
                throw new IllegalStateException("Risultato non valido.");
            }
            timer.start();
        }

        private void rollAnimation() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);

            final int centerX = getWidth() / 2;
            final int centerY = getHeight() / 2;

            switch (result) {
                case 1:
                    drawDot(g, centerX, centerY);
                    break;
                case 2:
                    drawDot(g, centerX - SPACING, centerY - SPACING);
                    drawDot(g, centerX + SPACING, centerY + SPACING);
                    break;
                case 3:
                    drawDot(g, centerX, centerY);
                    drawDot(g, centerX - SPACING, centerY - SPACING);
                    drawDot(g, centerX + SPACING, centerY + SPACING);
                    break;
                case 4:
                    drawDot(g, centerX - SPACING, centerY - SPACING);
                    drawDot(g, centerX + SPACING, centerY + SPACING);
                    drawDot(g, centerX - SPACING, centerY + SPACING);
                    drawDot(g, centerX + SPACING, centerY - SPACING);
                    break;
                case 5:
                    drawDot(g, centerX, centerY);
                    drawDot(g, centerX - SPACING, centerY - SPACING);
                    drawDot(g, centerX + SPACING, centerY + SPACING);
                    drawDot(g, centerX - SPACING, centerY + SPACING);
                    drawDot(g, centerX + SPACING, centerY - SPACING);
                    break;
                case 6:
                    drawDot(g, centerX - SPACING, centerY - SPACING);
                    drawDot(g, centerX + SPACING, centerY + SPACING);
                    drawDot(g, centerX - SPACING, centerY + SPACING);
                    drawDot(g, centerX + SPACING, centerY - SPACING);
                    drawDot(g, centerX - SPACING, centerY);
                    drawDot(g, centerX + SPACING, centerY);
                    break;
                default:
                break;
            }
        }

        private void drawDot(final Graphics g, final int x, final int y) {
            g.setColor(Color.BLACK);
            g.fillOval(x - DOT_SIZE / 2, y - DOT_SIZE / 2, DOT_SIZE, DOT_SIZE);
        }

        private int getResult() {
            return result;
        }
    }
}
