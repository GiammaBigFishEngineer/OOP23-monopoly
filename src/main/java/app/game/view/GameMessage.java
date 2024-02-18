package app.game.view;

import javax.swing.JOptionPane;

/**
 * 
 */

public final class GameMessage {

    /**
    * 
    */
    public void exitPrison() {
        JOptionPane.showMessageDialog(null, "You rolled the same numbers, you are free to go !");
    }

    /**
    * 
    */
    public void remainPrison() {
        JOptionPane.showMessageDialog(null, "You didn' t roll the same numbers ,you are'nt free to go !");
    }

    /**
     * @param diceValue
     */
    public void rollDice(final Integer diceValue) {
        JOptionPane.showMessageDialog(null, "You roll " + diceValue + "!");

    }

    /**
    * 
    */
    public void saveGame() {
        JOptionPane.showMessageDialog(null, "You saved the game correctly!");
    }

    /**
     * @param name
     */
    public void eliminatePlayer(final String name) {
        JOptionPane.showMessageDialog(null, name + ", you have been eliminated!");
    }

    /**
     * @param name
     */
    public void winnerPlayer(final String name) {
        JOptionPane.showMessageDialog(null, name + ", you are the winner!");
    }

    /**
    * 
    */
    public void noBuyPropriety() {
        JOptionPane.showMessageDialog(null, "You can't afford this propriety!");
    }

    /**
    * 
    */
    public void noBuilHouse() {
        JOptionPane.showMessageDialog(null, "You can't build a house!");
    }

    /**
     * @param name
     */
    public void fees(final String name) {
        JOptionPane.showMessageDialog(null, "You landed on " + name + "'propriety, you must pay!");
    }

}
