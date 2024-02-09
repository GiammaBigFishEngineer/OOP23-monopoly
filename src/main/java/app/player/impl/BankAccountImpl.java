package app.player.impl;

import app.player.apii.BankAccount;
import app.player.apii.Player;

/**
 * Class which implements a player's BankAccount.
 */
public class BankAccountImpl implements BankAccount {

    /**
     * amount of money each player has in his BankAccount.
     */
    private int balance;
    private boolean balanceChanged;
    private static final int STARTING_BALANCE = 1500;

    /**
     * Constructor with 0-argument.
     */
    public BankAccountImpl() {
        this.balance = STARTING_BALANCE;
    }

    /**
     * Constructor that requires balance.
     * 
     * @param balance the initial balance
     */
    public BankAccountImpl(final int balance) {
        this.balance = balance;
    }

    /**
     * @return amount of money owned by the player
     */
    @Override
    public int getBalance() {
        return this.balance; 
    }

    /**
     * @param balance the new balance to be set
     */
    @Override
    public void setBalance(final int balance) {
        this.balance = balance;
        balanceChanged = true;
    }

    /**
     * @param player the player to be paid
     * @param amount the amount of money to be paid
     */
    @Override
    public void payPlayer(final Player player, final int amount) {
        // to do
    }

    /**
     * @param amount the amount of money received
     */
    @Override
    public void receivePayment(final int amount) {
        this.balance += amount;
        balanceChanged = true;
    }

    /**
     * @param amount the amount to be checked
     * @return {@code true} if payment is allowed, false otherwise
     */
    @Override
    public boolean isPaymentAllowed(final int amount) {
        return false; // to do
    }
    /**
     * @return true if there are changes in balance, false otherwise
     */
    @Override
    public boolean hasBalanceChanged() {
        return balanceChanged;
    }
}
