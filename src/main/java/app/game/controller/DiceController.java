package app.game.controller;

import app.game.utils.Dice;

/**
 * The controller class for managing the rolling of dice in a game.
 * This class connects the view with the dice model.
 * Contains methods for starting and controlling the dice rolling process.
 */
public class DiceController {
    private final Dice dice;

    /**
     * Constructor DiceController with the specified dice model.
     * 
     * @param dice the dice model to be controlled.
     */
    public DiceController(final Dice dice) {
        this.dice = dice;
    }

    /**
     * Start rolling the dice by calling the rollDice method in dice model.
     */
    public void rollDiceAction() {
        dice.rollDice();
    }
}
