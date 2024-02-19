package app.game.utils;

/**
 * This enum contains all the codes that each correspond to a button in the
 * view.
 * In fact it will be used in the game controller to enable or disable certain
 * buttons without having a direct reference.
 */
public enum BtnCodeEnum {

    /**
     * The code that represent the Roll Dice button
     */
    ROLL_DICE,

    /**
     * The code that represent Buy Propriety button
     */
    BUY_PROPRIETY,

    /**
     * The code that represent Sell Propriety button
     */
    SELL_PROPRIETY,

    /**
     * The code that represent Buy House button
     */
    BUY_HOUSE,

    /**
     * The code that represent End Turn button
     */
    END_TURN,

    /**
     * The code that represent Unforseen button
     */
    UNFORSEEN;

}
