package app.game.view;

import javax.swing.JOptionPane;

public class GameMessage {

    public void exitPrison(GameView gamePanel) {
        JOptionPane.showMessageDialog(gamePanel, "You rolled the same numbers, you are free to go !");
    }

    public void remainPrison(GameView gamePanel) {
        JOptionPane.showMessageDialog(gamePanel, "You didn' t roll the same numbers ,you are'nt free to go !");
    }

    public void rollDice(GameView gamePanel, Integer diceValue) {
        JOptionPane.showMessageDialog(gamePanel, "You roll " + diceValue + "!");

    }

    public void saveGame(GameView gamePanel) {
        JOptionPane.showMessageDialog(gamePanel, "You saved the game correctly!");
    }

    public void eliminatePlayer(GameView gamePanel, String name) {
        JOptionPane.showMessageDialog(gamePanel, name + ", you have been eliminated!");
    }

    public void winnerPlayer(GameView gamePanel, String name) {
        JOptionPane.showMessageDialog(gamePanel, name + ", you are the winner!");
    }

    public void noBuyPropriety(GameView gamePanel) {
        JOptionPane.showMessageDialog(gamePanel, "You can't afford this propriety!");
    }

    public void noBuilHouse(GameView gamePanel) {
        JOptionPane.showMessageDialog(gamePanel, "You can't build a house!");
    }

}
