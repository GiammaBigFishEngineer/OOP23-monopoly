package app.card.utils;

import java.util.Random;

import app.card.impl.Unforseen;
import app.player.apii.NotEnoughMoneyException;
import app.player.apii.Player;

/**
 * Utility class for make the static action to be called on players.
 */
public final class StaticActions {

    private StaticActions() { }

    /**
     * @param player who has to get money
     * @param money is the amount to give to player
     */
    public static void giveMoneyPlayer(final Player player, final int money) {
        if (player != null) {
            player.getBankAccount().receivePayment(money);
        }
    }

    /**
     * @param player who have to pay money
     * @param money is the amount player has to pay
     * @throws NotEnoughMoneyException 
     */
    public static void playerPay(final Player player, final int money) throws NotEnoughMoneyException {
        if (player != null) {
            player.getBankAccount().payPlayer(null, money);
        }
    }

    /**
     * @param player who have to move
     * @param position is the new position of player
     */
    public static void movePlayer(final Player player, final int position) {
        if (player != null) {
            player.setPosition(position);
        }
    }

    /**
     * @param player who have to get Unforseen
     * @return the unforseen extracted after it does the action on player
     */
    public static Unforseen unforseen(final Player player) {
        final int unforseenSize = 14;
        final var extraction = new Random().nextInt(unforseenSize);
        final var myUnforseen = Unforseen.valueOf((String) "U" + extraction);
        myUnforseen.getCard().makeAction(player);
        return myUnforseen;
    }

}
