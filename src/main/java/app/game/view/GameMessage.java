package app.game.view;

import javax.swing.JOptionPane;

public class GameMessage {

    public void exitPrison(GameView gamePanel) {
        JOptionPane.showMessageDialog(gamePanel, "You rolled the same numbers, you are free to go !");
    }

    public void remainPrison(GameView gamePanel) {
        JOptionPane.showMessageDialog(gamePanel, "You didn' t roll the same numbers ,you are'nt free to go !");
    }

    public void rollDice(Integer diceValue, GameView gamePanel) {
        JOptionPane.showMessageDialog(gamePanel, "You roll " + diceValue + "!");

    }

    public void saveGame(GameView gamePanel) {
        JOptionPane.showMessageDialog(gamePanel, "You saved the game correctly!");
    }

}
