package app.player.apii;

/**
 * Interface which models a player's BankAccount.
 */
public interface BankAccount {
    /**
     * @return amount of money owned by the player
     */
    int getBalance();
    /**
     * @param player if null, payment for the Bank (and not for another player)
     * @param amount money which has to be given to another player
     */
    void payPlayer(Player player, int amount); 
    /**
     * @param amount money received by someone else
     */
    void receivePayment(int amount);
    /**
     * @param amount 
     * @return true if payment is allowed, otherwise false 
     */
    boolean isPaymentAllowed(int amount); 
}
