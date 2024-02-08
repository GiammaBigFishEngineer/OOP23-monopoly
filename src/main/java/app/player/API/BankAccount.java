package app.player.api;

/**
 * Interface which models a player's BankAccount.
 */
public interface BankAccount {
    /**
     * @return amount of money owned by the player
     */
    int getBalance();
    /**
     * This methods sets the balance, considering the change after some operation (e.g. turn of money)
     * @param balance
     */
    void setBalance(int balance);
    /**
     * @return true if there are changes in balance, false otherwise
     */
    boolean hasBalanceChanged(); 
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
