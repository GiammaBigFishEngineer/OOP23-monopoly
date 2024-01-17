package app.card.utils;

import java.util.Random;

import app.card.impl.Unforseen;
import app.player.api.Player;

public final class StaticActions {

    private StaticActions(){ };

    /**
     * @param player who has to get money
     * @param money is the amount to give to player
     */
    public static void giveMoneyPlayer(final Player player, final int money) {
        try {
            player.getBankAccount().receivePayment(money);
        } catch(NullPointerException e) {
            System.out.println(e);
        }
    }

    /**
     * @param player who have to pay money
     * @param money is the amount player has to pay
     */
    public static void playerPay(final Player player, final int money) {
        try {
            player.getBankAccount().payPlayer(null, money);
        } catch(NullPointerException e) {
            System.out.println(e);
        }
    }

    /**
     * @param player who have to move
     * @param position is the new position of player
     */
    public static void movePlayer(final Player player, final int position) {
        try {
            player.setPosition(position);
        } catch(NullPointerException e) {
            System.out.println(e);
        }
    }

    /**
     * @param player who have to get Unforseen
     * @return the unforseen extracted after it does the action on player
     */
    public static Unforseen unforseen(final Player player) {
        final int unforseenSize = 14;
        var extraction = new Random().nextInt(unforseenSize);
        var myUnforseen = Unforseen.valueOf((String)"U"+extraction);
        try {
            myUnforseen.getCard().makeAction(player);   
        } catch(NullPointerException e) {
            System.out.println(e);
        }
        return myUnforseen;
    }

}
