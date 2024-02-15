package app.player.view;

import javax.swing.JOptionPane;

import app.player.apii.BailLogic;
import app.player.apii.Player;
import app.player.impl.BailLogicImpl;
import app.game.view.GameView;

/**
 * Graphical implementation of the bail for prison.
 */ 
public final class BailView {

    private final BailLogic logic = new BailLogicImpl();
    private boolean bailResult;

    /**
     * Method which gives a player the possibility to choose 
     * between paying an amount of money to go out of jail or not.
     * @param player
     * @param gameV the frame on which the menuBail is displayed.
     * @return true if bail was successfully payed, otherwise false.
     */
    public boolean showMenuBail(final Player player, final GameView gameV) {
        final String message = "Would you like to pay " + BailLogicImpl.DEFAULT_PAYMENT 
            + "$ to go out of prison? You have " + player.getBankAccount().getBalance() + "$ on your BankAccount.";
        final int choice = JOptionPane.showConfirmDialog(gameV, message, "YOU ARE IN PRISON!", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            if (logic.hasPayed(player)) {
                JOptionPane.showMessageDialog(gameV, "You have payed the bail, OUT OF PRISON!",
                "FREE TO GO", JOptionPane.INFORMATION_MESSAGE);
                bailResult = true;
            } else {
                JOptionPane.showMessageDialog(gameV, "Not enough money for paying the bail. TRY YOUR LUCK!",
                "STILL IN PRISON", JOptionPane.ERROR_MESSAGE);
                bailResult = false;
            }
        } else {
            JOptionPane.showMessageDialog(gameV, "You have decided not to pay the bail. TRY YOUR LUCK!",
            "STILL IN PRISON", JOptionPane.ERROR_MESSAGE);
            bailResult = false;
        }
        return bailResult;
    }
}
