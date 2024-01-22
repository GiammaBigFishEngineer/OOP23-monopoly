package app.player.impl;

import app.player.api.BankAccount;
import app.player.api.NotEnoughMoneyException;
import app.player.api.Player;

public class BankAccountImpl implements BankAccount {

    /**
     * amount of money each player has in his BankAccount
     */
    private int balance; 
    
    /**
     * @param balance
     */
    public BankAccountImpl(final int balance){
        this.balance = balance; 
    }

    /**
     * constructor with 0-argument
     */
    public BankAccountImpl(){
        this(0);
    }

    @Override
    public int getBalance() {
        return this.balance; 
    }

    @Override
    public void payPlayer(Player player, final int amount) throws NotEnoughMoneyException {
        if (!isPaymentAllowed(amount)) {
            throw new NotEnoughMoneyException();
        }  
        this.balance -= amount; 
        if (player != null ) {
            player.getBankAccount().receivePayment(amount);
        }
        // if (player == null) pago la banca, quindi non accade nulla          
    }

    @Override
    public void receivePayment(final int amount) {
        this.balance += amount;     
    }

    @Override
    public boolean isPaymentAllowed(final int amount) {
        return this.balance >= amount;    
    }
}
