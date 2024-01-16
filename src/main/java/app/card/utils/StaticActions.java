package app.card.utils;

import app.player.api.Player;

public final class StaticActions {

    private StaticActions(){ };

    /**
     * @param player who has to get money
     * @param money is the amount to give to player
     */
    public static void giveMoneyPlayer(final Player player, final int money) {
        /* ritira 200 euro */
        player.getBankAccount().receivePayment(money);
    }

    /**
     * @param player who have to pay money
     * @param money is the amount player has to pay
     */
    public static void playerPay(final Player player, final int money) {
        /* ritira 200 euro */
        player.getBankAccount().payPlayer(null, money);
    }

    /**
     * @param player who have to move
     * @param position is the new position of player
     */
    public static void movePlayer(final Player player, final int position) {
        /* ritira 200 euro */
        player.setPosition(position);
    }

}
