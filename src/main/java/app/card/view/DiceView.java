package app.card.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceView extends JFrame {

    private static final int DICE_SIZE = 100;
    private static final int TIMER_DELAY = 100;

    private JLabel resultLabel;
    private DicePanel dicePanel1;
    private DicePanel dicePanel2;

    public DiceView(final int size) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        final JButton rollButton = new JButton("Lancia i dadi");
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(rollButton);
        panel.add(topPanel, BorderLayout.NORTH);

        resultLabel = new JLabel("Risultato: ");
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(resultLabel);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        dicePanel1 = new DicePanel();
        dicePanel2 = new DicePanel();
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(dicePanel1);
        centerPanel.add(dicePanel2);
        panel.add(centerPanel, BorderLayout.CENTER);

        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roll();
            }
        });

        this.setContentPane(panel);
        this.setSize(size, size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void roll() {
        dicePanel1.startAnimation();
        dicePanel2.startAnimation();

        //int resultDice1 = dicePanel1.getResult();
        //int resultDice2 = dicePanel2.getResult();
        int res = getDiceResult();

        resultLabel.setText("Risultato: " + res);
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
            this.timer = new Timer(TIMER_DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rollAnimation();
                }
            });
            
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(DICE_SIZE, DICE_SIZE));
        }

        private void startAnimation() {
            result = randomNumber.nextInt(6) + 1;
            if (result < 1 || result > 6) {
                throw new IllegalStateException("Risultato non valido.");
            }
            timer.start();
        }

        private void rollAnimation() {
            // to do
        }

        protected int getResult() {
            return result;
        }
    }
}