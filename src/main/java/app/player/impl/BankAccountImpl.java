package app.player.impl;

import app.player.api.BankAccount;
import app.player.api.Player;

/**
 * Class which implements a player's BankAccount.
 */
public final class BankAccountImpl implements BankAccount {
    /**
     * amount of money each player has in his BankAccount.
     */
    private int balance;
    private boolean balanceChanged;
    /**
     * Constructor which requires a balance.
     * @param balance
     */
    public BankAccountImpl(final int balance) {
        this.balance = balance; 
    }

    /**
     * Constructor with 0-argument.
     */
    public BankAccountImpl() {
        this(0);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getBalance() {
        return this.balance; 
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setBalance(final int balance) {
        this.balance = balance;
        this.balanceChanged = true; 
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasBalanceChanged() {
        return balanceChanged;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void payPlayer(final Player player, final int amount) {
        if (!isPaymentAllowed(amount)) {
            return;
        }
        this.balance -= amount; 
        if (player != null) {
            player.getBankAccount().receivePayment(amount);
        } // if (player == null) pago la banca, quindi non accade nulla
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void receivePayment(final int amount) {
        this.balance += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPaymentAllowed(final int amount) {
        return this.balance >= amount;
    }
}
