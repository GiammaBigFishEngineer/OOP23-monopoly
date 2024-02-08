package app.player.impl;

import app.player.apii.BailLogic;
import app.player.apii.Player;

/**
 * Class which implements the methods used for
 * managing the mechanism of bail for jail.
 */
public final class BailLogicImpl implements BailLogic {

    /**
     * Constant vlaue of a default payment.
     */
    public static final int DEFAULT_PAYMENT = 100;

    /**
     * {@inheritDoc}
     */
    public boolean hasPayed(final Player player) {
        if (!(player.getBankAccount().isPaymentAllowed(DEFAULT_PAYMENT))) {
            notPayed(player);
            return false;
        } else {
            player.getBankAccount().payPlayer(null, DEFAULT_PAYMENT);
            player.setInJail(false);
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void notPayed(final Player player) {
        player.setInJail(true);
    }
}
