package app.player.view;

import javax.swing.JOptionPane;

import app.player.apii.BailLogic;
import app.player.apii.Player;
import app.player.impl.BailLogicImpl;

/**
 * Graphical implementation of the bail for prison.
 */
public final class BailView {

    private final BailLogic logic = new BailLogicImpl();
    private boolean bailResult;

    /**
     * Method which gives a player the possibility to choose
     * between paying an amount of money to go out of jail or not.
     * 
     * @param player
     * @return true if bail was successfully payed, otherwise false.
     */
    public boolean showMenuBail(final Player player) {
        final String message = player.getName() + ", vuoi pagare " + BailLogicImpl.DEFAULT_PAYMENT 
            + " per uscire di prigione? Hai " + player.getBankAccount().getBalance() + " sul tuo conto bancario.";
        final int choice = JOptionPane.showConfirmDialog(null, message, "SEI IN PRIGIONE!", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            if (logic.hasPayed(player)) {
                JOptionPane.showMessageDialog(null, "Hai pagato la cauzione, FUORI DI PRIGIONE!",
                "LIBERO DI ANDARE", JOptionPane.INFORMATION_MESSAGE);
                bailResult = true;
            } else {
                JOptionPane.showMessageDialog(null, "Non hai abbastanza soldi per pagare la cauzione. SFIDA LA SORTE!",
                "ANCORA IN PRIGIONE", JOptionPane.ERROR_MESSAGE);
                bailResult = false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hai deciso di non pagare la cauzione. SFIDA LA SORTE!",
            "ANCORA IN PRIGIONE", JOptionPane.ERROR_MESSAGE);
            bailResult = false;
        }
        return bailResult;
    }
}
