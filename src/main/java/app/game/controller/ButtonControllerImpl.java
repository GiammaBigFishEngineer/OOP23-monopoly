package app.game.controller;

import app.game.view.BtnCodeEnum;
import app.player.apii.Player;
import app.card.apii.Card;
import app.game.apii.ButtonController;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ButtonControllerImpl extends GameObservableImpl implements ButtonController {

    private List<Card> cardList;
    private List<Player> playerList;
    private GameControllerImpl logic;
    private Map<BtnCodeEnum, Boolean> btnCodeList;

    private Player currentPlayer;

    public ButtonControllerImpl(List<Card> cardList, List<Player> playerList) {

        this.cardList = new ArrayList<>();
        this.cardList.addAll(cardList);

        this.playerList = new ArrayList<>();
        this.playerList.addAll(playerList);

        btnCodeList = new HashMap<>();

        this.logic = new GameControllerImpl(playerList, cardList);

    }

    @Override
    public void rollDice() {

        /*
         * Return true if player got defeated
         */

        if (logic.rollDice(true)) {

            int diceValue = logic.getDiceValue();

            updateObserver(diceValue, currentPlayer, "rollDice");
            updateObserver(-1, currentPlayer, "YouLoseMessage");
            this.nextTurn();

        } else {
            btnCodeList.putAll(logic.getBtnStatus());

            currentPlayer = logic.getCurrentPlayer();
            int diceValue = logic.getDiceValue();

            updateObserver(diceValue, currentPlayer, "rollDice");
            updateObserver(-1, currentPlayer, "refreshPlayerPanel");
            updateObserver(-1, currentPlayer, "refreshPlayerPosition");
        }

    }

    @Override
    public void buyPropriety() {

        if (!logic.buyPropriety()) {
            updateObserver(-1, currentPlayer, "NoBuyMessage");
        }
        currentPlayer = logic.getCurrentPlayer();
        updateObserver(-1, currentPlayer, "refreshPlayerPanel");

    }

    @Override
    public void buildHouse() {

        if (!logic.buildHouse()) {
            updateObserver(-1, currentPlayer, "NoBuyMessage");
        }
        currentPlayer = logic.getCurrentPlayer();
        updateObserver(-1, currentPlayer, "refreshPlayerPanel");

    }

    @Override
    public void sellPropriety() {

        logic.sellPropriety();
        currentPlayer = logic.getCurrentPlayer();
        updateObserver(-1, currentPlayer, "refreshPlayerPanel");

    }

    @Override
    public void saveGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveGame'");
    }

    @Override
    public void endTurn() {
        this.nextTurn();
    }

    public void nextTurn() {

        logic.newTurn();

        currentPlayer = logic.getCurrentPlayer();

        updateObserver(-1, currentPlayer, "refreshPlayerPanel");

        if (logic.isCurrentPlayerInJail()) {

            if (updateObserver(-1, currentPlayer, "bail")) {

                logic.enableSingleButton(BtnCodeEnum.rollDice);

            } else {
                logic.tryLuckyBail();
            }

        } else {
            logic.enableSingleButton(BtnCodeEnum.rollDice);
        }

        btnCodeList.putAll(logic.getBtnStatus());

    }

    @Override
    public Map<BtnCodeEnum, Boolean> getBtnCodeList() {
        return this.btnCodeList;
    }

}
