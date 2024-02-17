package app.card.view;

import app.game.controller.DiceController;
import app.game.utils.Dice;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * View of dice in the game.
 */
public class DiceView extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int DICE_SIZE = 175;
    private static final int FONT_SIZE = 24;
    private static final int TOP_PADDING = 50;
    private static final int GENERAL_INSETS = 50;
    private static final int RIGHT_INSETS = 10;
    private static final int LEFT_INSETS = 10;
    private static final int DOT_SIZE = 20;
    private static final int SPACING = 40;
    private static final int ONE_DOT = 1;
    private static final int TWO_DOT = 2;
    private static final int THREE_DOT = 3;
    private static final int FOUR_DOT = 4;
    private static final int FIVE_DOT = 5;
    private static final int SIX_DOT = 6;
    private static final int MIN_WIDTH = 750;
    private static final int MIN_HEIGHT = 750;

    private final transient Dice dice;
    private final transient DiceController diceController;
    private final JLabel resultLabel;
    private final DicePanel dicePanel1;
    private final DicePanel dicePanel2;

    /**
     * Constructor of DiceView.
     * 
     * @param dice the model
     * @param diceController the controller
     */
    public DiceView(final Dice dice, final DiceController diceController) {
        this.dice = dice;
        this.diceController = diceController;
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        resultLabel = new JLabel("Risultato:  ");
        final Font resultFont = new Font("Verdana", Font.PLAIN, FONT_SIZE);
        resultLabel.setFont(resultFont);

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBorder(BorderFactory.createEmptyBorder(TOP_PADDING, 0, 0, 0));
        topPanel.add(resultLabel);
        this.add(topPanel, BorderLayout.NORTH);

        dicePanel1 = new DicePanel();
        dicePanel2 = new DicePanel();
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(GENERAL_INSETS, GENERAL_INSETS, GENERAL_INSETS, RIGHT_INSETS);
        centerPanel.add(dicePanel1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(GENERAL_INSETS, LEFT_INSETS, GENERAL_INSETS, GENERAL_INSETS);
        centerPanel.add(dicePanel2, gbc);
        this.add(centerPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * Updates the view with the results of the latest dice roll.
     * Sets the result of the first die in the firs die panel, and
     * the result of the second die in the second die panel.
     * Then displays the total result printed.
     */
    public void updateView() {
        diceController.rollDice();
        dicePanel1.setResult(dice.getDie1Result());
        dicePanel2.setResult(dice.getDie2Result());
        resultLabel.setText("Risultato: " + dice.getDiceResult());
    }

    /**
     * @return the JPanel that's rappresent a Die
     */
    private static class DicePanel extends JPanel {

        private static final long serialVersionUID = 1L;
        private int result;

        DicePanel() {
            this.result = 1;
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(DICE_SIZE, DICE_SIZE));
        }

        public void setResult(final int result) {
            this.result = result;
            repaint();
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);

            final int centerX = getWidth() / 2;
            final int centerY = getHeight() / 2;

            switch (result) {
                case ONE_DOT:
                    drawDot(g, centerX, centerY);
                    break;
                case TWO_DOT:
                    drawDot(g, centerX - SPACING, centerY - SPACING);
                    drawDot(g, centerX + SPACING, centerY + SPACING);
                    break;
                case THREE_DOT:
                    drawDot(g, centerX, centerY);
                    drawDot(g, centerX - SPACING, centerY - SPACING);
                    drawDot(g, centerX + SPACING, centerY + SPACING);
                    break;
                case FOUR_DOT:
                    drawDot(g, centerX - SPACING, centerY - SPACING);
                    drawDot(g, centerX + SPACING, centerY + SPACING);
                    drawDot(g, centerX - SPACING, centerY + SPACING);
                    drawDot(g, centerX + SPACING, centerY - SPACING);
                    break;
                case FIVE_DOT:
                    drawDot(g, centerX, centerY);
                    drawDot(g, centerX - SPACING, centerY - SPACING);
                    drawDot(g, centerX + SPACING, centerY + SPACING);
                    drawDot(g, centerX - SPACING, centerY + SPACING);
                    drawDot(g, centerX + SPACING, centerY - SPACING);
                    break;
                case SIX_DOT:
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
    }
}
