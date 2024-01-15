package app.card.utils;

import app.player.apii.Player;

public final class StaticActions {

    private StaticActions(){ };

    /**
     * @param player who have to go to prison
     */
    public static void goToPrison(final Player player) {
        /* salta un turno e va in prigione */
        player.setPosition(10);
    }

    /**
     * @param player who have to get 200 money
     */
    public static void finishRound(final Player player) {
        /* ritira 200 euro */
        player.getBankAccount().receivePayment(200);
    }

}
