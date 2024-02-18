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
        JOptionPane.showMessageDialog(null, "Hai ottenuto lo stesso valore, sei libero di andare!");
    }

    /**
    * 
    */
    public void remainPrison() {
        JOptionPane.showMessageDialog(null, "Non hai ottenuto lo stesso valore, rimani in prigione!");
    }

    /**
    * 
    */
    public void saveGame() {
        JOptionPane.showMessageDialog(null, "Gioco salvato correttamente!");
    }

    /**
     * @param name
     */
    public void eliminatePlayer(final String name) {
        JOptionPane.showMessageDialog(null, name + ", non hai più soldi e vieni eliminato!");
    }

    /**
     * @param name
     */
    public void winnerPlayer(final String name) {
        JOptionPane.showMessageDialog(null, "Congratulazioni " + name + ", hai vinto !!");
    }

    /**
    * 
    */
    public void noBuyPropriety() {
        JOptionPane.showMessageDialog(null, "Non ti puoi permettere questa proprietà!");
    }

    /**
    * 
    */
    public void noBuilHouse() {
        JOptionPane.showMessageDialog(null, "Non puoi costruire una casa!");
    }

    /**
     * @param name
     */
    public void fees(final String name) {
        JOptionPane.showMessageDialog(null, "Sei atterrato su una proprietà di " + name + ", devi pagare!");
    }

}
