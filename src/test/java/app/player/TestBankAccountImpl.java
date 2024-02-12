package app.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.player.impl.PlayerImpl;

import java.util.List;

/**
 * Simple test for {@link app.player.impl.BankAccountImpl} class.
 */
class TestBankAccountImpl {
    private static final int DEFAULT_PAYMENT = 100;
    private static final int DEFAULT_ID = 1; 

    private PlayerImpl player1;
    private PlayerImpl player2;
    /**
     * Configuration step: this is performed before each test.
     */
    @BeforeEach
    void init() {
        this.player1 = new PlayerImpl("First Player", DEFAULT_ID, List.of(), DEFAULT_PAYMENT * 2);
        this.player2 = new PlayerImpl("Second Player", DEFAULT_ID * 2, List.of(), DEFAULT_PAYMENT * 4);
    }

    /**
     * Check that the initialization of the BankAccount is created with the correct values.
     */
    @Test
    void testGetBalance() {
        Assertions.assertEquals(DEFAULT_PAYMENT * 2, this.player1.getBankAccount().getBalance()); 
        Assertions.assertEquals(DEFAULT_PAYMENT * 4, this.player2.getBankAccount().getBalance());
    }

    /**
     * Check that the payment to another player is performed correctly. 
     */
    @Test 
    void testPayPlayer() {
        final int expectedPlayerPayed = this.player2.getBankAccount().getBalance() - DEFAULT_PAYMENT;
        this.player2.getBankAccount().payPlayer(player1, DEFAULT_PAYMENT);
        Assertions.assertEquals(3 * DEFAULT_PAYMENT, this.player1.getBankAccount().getBalance());
        Assertions.assertEquals(expectedPlayerPayed, this.player2.getBankAccount().getBalance()); 
    }

    /**
     * Check that the correct amount of money is received by the player.
     */
    @Test 
    void testReceivePayment() {
        final int expected = 3 * DEFAULT_PAYMENT; 
        this.player1.getBankAccount().receivePayment(DEFAULT_PAYMENT); 
        Assertions.assertEquals(expected, this.player1.getBankAccount().getBalance());
    }

    /**
     * Check if a payment is allowed. If not, it throws an exception.
    */ 
    @Test
    void testIsPaymentAllowed() {
        final int amountToSpend = 10 * DEFAULT_PAYMENT; 
        final boolean actualFirstResult = this.player1.getBankAccount().isPaymentAllowed(amountToSpend);
        Assertions.assertFalse(actualFirstResult);
        this.player1.getBankAccount().receivePayment(amountToSpend);
        final boolean actualSecondResult = this.player1.getBankAccount().isPaymentAllowed(amountToSpend);
        Assertions.assertTrue(actualSecondResult);
    }
}
