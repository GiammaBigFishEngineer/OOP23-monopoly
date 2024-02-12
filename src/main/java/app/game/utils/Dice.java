package app.game.utils;

import java.util.Random;

/**
 * This class represents the logic for rolling two dice.
 * Dice are rolled by generating numbers between 1 and 6 for each die
 * and calculating the total result of the roll.
 */
public class Dice {

    private static final int MAX_NUMBER = 6;
    private final Random randomNumber;

    private int die1Result;
    private int die2Result;
    private int totalResult;

    /**
     * Constructor of a new dice istance.
     * It initializes a new random object for generating random numbers
     */
    public Dice() {
        this.randomNumber = new Random();
    }

    /**
     * Simulates rolling the dice by generating random numbers for
     * each die and calculating the total result.
     */
    public void rollDice() {
        die1Result = randomNumber.nextInt(MAX_NUMBER) + 1;
        die2Result = randomNumber.nextInt(MAX_NUMBER) + 1;
        totalResult = die1Result + die2Result;
    }

    /**
     * Return the resul obtained from the first die.
     * 
     * @return the result of the first die
     */
    public int getDie1Result() {
        return die1Result;
    }

    /**
     * Return the resul obtained from the second die.
     * 
     * @return the result of the second die
     */
    public int getDie2Result() {
        return die2Result;
    }

    /**
     * Return the total and final resul obtained of the dice roll.
     * 
     * @return the total result of the dice roll
     */
    public int getDiceResult() {
        return totalResult;
    }
}
