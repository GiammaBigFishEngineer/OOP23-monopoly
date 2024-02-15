package app.game.apii;

import app.game.view.BtnCodeEnum;
import java.util.Map;

public interface ButtonController {

    /**
     * Roll dice.
     */
    void rollDice();

    /**
     * @param currentPlayer that want to buy the propriety.
     */
    void buyPropriety();

    /**
     * @param currentPlayer that want to build an house.
     */
    void buildHouse();

    /**
     * @param currentPlayer that want to sell the propriety.
     */
    void sellPropriety();

    void saveGame();

    void endTurn();

    Map<BtnCodeEnum, Boolean> getBtnCodeList();

    void nextTurn();

}
