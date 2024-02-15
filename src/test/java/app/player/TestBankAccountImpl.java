package app.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.player.apii.BankAccount;
import app.player.impl.BankAccountImpl;

/**
 * Simple test for {@link app.player.impl.BankAccountImpl} class.
 */
class TestBankAccountImpl {
    private static final int DEFAULT_PAYMENT = 100;
    private BankAccount bankAccount1, bankAccount2; 

    /**
     * Configuration step: this is performed before each test.
     */
    @BeforeEach
    void init() {
        this.bankAccount1 = new BankAccountImpl(DEFAULT_PAYMENT);
        this.bankAccount2 = new BankAccountImpl(DEFAULT_PAYMENT * 2);
    }

    /**
     * Check that the initialization of the BankAccount is created with the correct values.
     */
    @Test
    void testGetBalance() {
        Assertions.assertEquals(DEFAULT_PAYMENT, bankAccount1.getBalance());
        Assertions.assertEquals(DEFAULT_PAYMENT * 2, bankAccount2.getBalance());
    }

    /**
     * Check that, if the balance changes, it is notified by the method hasBalanceChanged().
     */
    @Test
    void testHasBalanceChanged() {
        this.bankAccount1.receivePayment(DEFAULT_PAYMENT); // 200
        Assertions.assertEquals(bankAccount2.getBalance(), bankAccount1.getBalance());
        this.bankAccount1.setBalance(DEFAULT_PAYMENT);
        Assertions.assertTrue(bankAccount1.hasBalanceChanged());
    }

    /**
     * Check that the payment to another player is performed correctly. 
     */
    @Test 
    void testPayPlayer() {
        final int expectedPlayerPayed = this.bankAccount2.getBalance() - DEFAULT_PAYMENT;
        this.bankAccount2.payPlayer(null, DEFAULT_PAYMENT);
        Assertions.assertEquals(expectedPlayerPayed, bankAccount2.getBalance());
    }

    /**
     * Check that the correct amount of money is received by the player.
     */
    @Test 
    void testReceivePayment() {
        this.bankAccount1.receivePayment(DEFAULT_PAYMENT);
        Assertions.assertEquals(DEFAULT_PAYMENT * 2, bankAccount1.getBalance());
        this.bankAccount1.payPlayer(null, DEFAULT_PAYMENT * 2);
        Assertions.assertEquals(0, bankAccount1.getBalance());
    }

    /**
     * Check if a payment is allowed.
    */ 
    @Test
    void testIsPaymentAllowed() {
        final int amountToSpend = 100 * DEFAULT_PAYMENT;
        Assertions.assertFalse(bankAccount1.isPaymentAllowed(amountToSpend));
        this.bankAccount1.receivePayment(amountToSpend);
        Assertions.assertTrue(bankAccount1.isPaymentAllowed(amountToSpend));
    }
}
