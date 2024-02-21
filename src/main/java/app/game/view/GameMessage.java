package app.game.view;

import javax.swing.JOptionPane;

/**
 * This class contains all the game pop-ups.
 */

public final class GameMessage {

    /**
     * This pop up is called when the player leaves the jail.
     */
    public void exitPrison() {
        JOptionPane.showMessageDialog(null, "Hai ottenuto lo stesso valore, sei libero di andare!");
    }

    /**
     * This pop up is called when the player tries to leave the jail by getting the.
     * same value on two dice rolls, but doesn't get a success
     */
    public void remainPrison() {
        JOptionPane.showMessageDialog(null, "Non hai ottenuto lo stesso valore, rimani in prigione!");
    }

    /**
     * This pop up is called when the player tries to leave the jail by getting the.
     * same value on two dice rolls, and gets a success
     */
    public void saveGame() {
        JOptionPane.showMessageDialog(null, "Gioco salvato correttamente!");
    }

    /**
     * This pop up is called when the player is eliminated.
     * 
     * @param name the player name
     */
    public void eliminatePlayer(final String name) {
        JOptionPane.showMessageDialog(null, name + ", non hai piu' soldi e vieni eliminato!");
    }

    /**
     * This pop up is called when the player win the game.
     * 
     * @param name the player name
     */
    public void winnerPlayer(final String name) {
        JOptionPane.showMessageDialog(null, "Congratulazioni " + name + ", hai vinto !!");
    }

    /**
     * This pop up is called when the player can't afford a propriety.
     */
    public void noBuyPropriety() {
        JOptionPane.showMessageDialog(null, "Non ti puoi permettere questa proprieta'!");
    }

    /**
     * This pop up is called when the player can't afford an house or reached the.
     * maximum number of houses built
     */
    public void noBuilHouse() {
        JOptionPane.showMessageDialog(null, "Non puoi costruire una casa!");
    }

    /**
     * This pop up is called when the player hash landed on someone else propriety.
     * 
     * @param name is the owner name
     */
    public void fees(final String name) {
        JOptionPane.showMessageDialog(null, "Sei atterrato su una proprieta' di " + name + ", devi pagare!");
    }

    /**
     * This pop up is called when exit game button is pressed.
     * 
     * @return true if anyone confirm to quit, false otherwise
     */
    public boolean quitGame() {
        final int res = JOptionPane.showConfirmDialog(null, "Confermi di voler uscire?", "exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return res == JOptionPane.YES_OPTION;
    }

}
