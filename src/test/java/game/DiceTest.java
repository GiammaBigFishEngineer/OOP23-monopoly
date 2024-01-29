package game;

import app.game.utils.Dice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for rolling two dice.
 */
class DiceTest {

    private Dice dice;
    private static final int MIN_NUMBER = 1;
    private static final int MIN_RESULT = 2;
    private static final int MAX_NUMBER = 6;
    private static final int MAX_RESULT = 12;

    /**
     * The initialization: a new istance of the dice class is created.
     */
    @BeforeEach
    void init() {
        this.dice = new Dice();
    }

    /**
     * Test the result of the first die.
     */
    @Test
    void testResultDie1() {
        dice.roll();
        final int die1Result = dice.getDie1Result();

        assertTrue(die1Result >= MIN_NUMBER && die1Result <= MAX_NUMBER);
    }

    /**
     * Test the result of the second die.
     */
    @Test
    void testResultDie2() {
        dice.roll();
        final int die2Result = dice.getDie2Result();

        assertTrue(die2Result >= MIN_NUMBER && die2Result <= MAX_NUMBER);
    }

    /**
     * Test the result of the two dice, therefore the result of the roll.
     */
    @Test
    void testDiceResult() {
        dice.roll();
        final int totalResult = dice.getDiceResult();

        assertTrue(totalResult >= MIN_RESULT && totalResult <= MAX_RESULT);
    }

    /**
     * Test the correctness of all values in a dice roll.
     */
    @Test
    void testRollAllResultWithCorrectValues() {
        dice.roll();
        final int die1Result = dice.getDie1Result();
        final int die2Result = dice.getDie2Result();
        final int totalResult = dice.getDiceResult();

        assertTrue(die1Result >= MIN_NUMBER && die1Result <= MAX_NUMBER);
        assertTrue(die2Result >= MIN_NUMBER && die2Result <= MAX_NUMBER);
        assertTrue(totalResult >= MIN_RESULT && totalResult <= MAX_RESULT);
    }

    /**
     * Test the correctness of the result of the dice roll several times.
     * The correctness of this result will be important to advance the player on the table.
     */
    @Test
    void testCorrectResultMultipleTimes() {

        for (int i = 0; i < 1000; i++) {
            dice.roll();
            final int totalResult = dice.getDiceResult();

            assertTrue(totalResult >= MIN_RESULT && totalResult <= MAX_RESULT);
        }
    }

    /**
     * Test the consistency of the dice roll.
     * This ensures that the total result obtained from rolling two dice is consistent
     * with the sum of the individual results obtained from each die.
     */
    @Test
    void testRollConsistency() {
        dice.roll();
        final int totalResult = dice.getDiceResult();
        final int sum = dice.getDie1Result() + dice.getDie2Result();

        assertEquals(sum, totalResult);
    }
}
