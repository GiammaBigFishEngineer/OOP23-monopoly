package app.game.view;

import javax.swing.JOptionPane;

public class GameMessage {

    public void exitPrison() {
        JOptionPane.showMessageDialog(null, "You rolled the same numbers, you are free to go !");
    }

    public void remainPrison() {
        JOptionPane.showMessageDialog(null, "You didn' t roll the same numbers ,you are'nt free to go !");
    }

    public void rollDice(Integer diceValue) {
        JOptionPane.showMessageDialog(null, "You roll " + diceValue + "!");

    }

    public void saveGame() {
        JOptionPane.showMessageDialog(null, "You saved the game correctly!");
    }

    public void eliminatePlayer(String name) {
        JOptionPane.showMessageDialog(null, name + ", you have been eliminated!");
    }

    public void winnerPlayer(String name) {
        JOptionPane.showMessageDialog(null, name + ", you are the winner!");
    }

    public void noBuyPropriety() {
        JOptionPane.showMessageDialog(null, "You can't afford this propriety!");
    }

    public void noBuilHouse() {
        JOptionPane.showMessageDialog(null, "You can't build a house!");
    }

}
