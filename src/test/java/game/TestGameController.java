package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.game.apii.GameController;
import app.game.controller.GameControllerImpl;
import app.game.view.BtnCodeEnum;
import app.player.apii.Player;

import java.io.IOException;
import java.util.List;

import java.util.Map;
import java.util.HashMap;

class TestGameController {

    private GameController logic;

    private static final int VISERBA_ID = 4;
    private static final int VISERBA_PRICE = 100;
    private static final int GO_PAYMENT = 100;
    private static final int GO_ID = 0;
    private static final int VISERBA_TO_GO_DISTANCE = 20;
    private static final int STAZIONE_NORD_ID = 16;
    private static final int GO_TO_STAZIONE_NORD_DISTANCE = 40;
    private static final int TABLE_LENGHT = 24;
    private static final int TABLE_LENGHT_UP = 25;
    private static final int START_BALANCE = 500;
    private static final int LOW_BALANCE = -495;
    private static final int JAIL_ID = 6;
    private static final int GO_TO_JAIL_ID = 18;
    private static final int THIS = 0;
    private static final int START_PLAYER_SIZE = 2;
    private static final int START_DEFEAT_SIZE = 0;
    private static final int FINAL_PLAYER_SIZE = 1;
    private static final int FINAL_DEFEAT_SIZE = 1;

    @BeforeEach
    void start() throws IOException {
        this.logic = new GameControllerImpl(List.of("mario", "luigi"));
    }

    @Test

    void testPlayer() {

        logic.newTurn();
        assertEquals("mario", logic.getCurrentPlayer().getName());
        logic.newTurn();
        assertEquals("luigi", logic.getCurrentPlayer().getName());
        logic.newTurn();
        assertEquals("mario", logic.getCurrentPlayer().getName());

    }

    @Test

    void sortedListTest() {
        final List<Card> sortedList = logic.getCardList();
        var i = 0;

        for (final Card card : sortedList) {
            assertEquals(i, card.getCardId());
            i++;
        }
    }

    @Test

    void testDice() {

        logic.newTurn();

        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();

        assertEquals(VISERBA_ID, logic.getCurrentPlayer().getCurrentPosition());
        assertEquals("Viserba", logic.getCurrentCard().getName());

        logic.setDiceValue(VISERBA_TO_GO_DISTANCE);
        logic.startTurn();

        assertEquals(GO_ID, logic.getCurrentPlayer().getCurrentPosition());
        assertEquals("GO", logic.getCurrentCard().getName());

        logic.newTurn();

        logic.setDiceValue(GO_TO_STAZIONE_NORD_DISTANCE);
        logic.startTurn();

        assertEquals(STAZIONE_NORD_ID, logic.getCurrentPlayer().getCurrentPosition());
        assertEquals("Stazione Nord", logic.getCurrentCard().getName());

    }

    @Test

    void unforseenTest() {

        logic.newTurn();

        logic.setDiceValue(TABLE_LENGHT);
        logic.startTurn();

        assertEquals(START_BALANCE + GO_PAYMENT, logic.getCurrentPlayer().getBankAccount().getBalance());

        logic.newTurn();

        logic.setDiceValue(TABLE_LENGHT_UP);
        logic.startTurn();

        assertEquals(START_BALANCE + GO_PAYMENT, logic.getCurrentPlayer().getBankAccount().getBalance());

        logic.newTurn();
        logic.setDiceValue(GO_TO_JAIL_ID);
        logic.startTurn();

        assertEquals(JAIL_ID, logic.getCurrentPlayer().getCurrentPosition());
        // assertTrue(logic.isCurrentPlayerInJail());

    }

    @Test

    void testEnabledButtons() {

        Map<BtnCodeEnum, Boolean> btnList;

        logic.newTurn();

        btnList = new HashMap<>();
        btnList.put(BtnCodeEnum.BUY_HOUSE, false);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, false);
        btnList.put(BtnCodeEnum.END_TURN, false);
        btnList.put(BtnCodeEnum.ROLL_DICE, false);
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, false);

        assertEquals(btnList, logic.getBtnStatus());

        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();

        btnList.put(BtnCodeEnum.BUY_HOUSE, false);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, true);
        btnList.put(BtnCodeEnum.END_TURN, true);
        btnList.put(BtnCodeEnum.ROLL_DICE, false);
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, false);

        assertEquals(btnList, logic.getBtnStatus());

        logic.buyPropriety();

        logic.newTurn();
        logic.newTurn();
        logic.setDiceValue(THIS);
        logic.startTurn();

        btnList.put(BtnCodeEnum.BUY_HOUSE, true);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, false);
        btnList.put(BtnCodeEnum.END_TURN, true);
        btnList.put(BtnCodeEnum.ROLL_DICE, false);
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, true);

        assertEquals(btnList, logic.getBtnStatus());

    }

    @Test

    void buyProprietyTest() {

        logic.newTurn();

        assertEquals(START_BALANCE, logic.getCurrentPlayer().getBankAccount().getBalance());

        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();
        logic.buyPropriety();

        final Player owner = CardAdapter.buildableAdapter(logic.getCurrentCard()).getOwner();

        assertEquals(owner, logic.getCurrentPlayer());

        assertEquals(START_BALANCE - VISERBA_PRICE, logic.getCurrentPlayer().getBankAccount().getBalance());

    }

    @Test
    void buildHouseTest() {

        logic.newTurn();

        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();

        final int cardPrice = CardAdapter.buildableAdapter(logic.getCurrentCard()).getPrice();

        final int housePrice = CardAdapter.buildableAdapter(logic.getCurrentCard()).getHousePrice();

        final int total = cardPrice + housePrice;

        logic.buyPropriety();

        logic.newTurn();
        logic.newTurn();

        logic.buildHouse();

        assertEquals(START_BALANCE - total, logic.getCurrentPlayer().getBankAccount().getBalance());

    }

    @Test

    void payFeesTest() {
        logic.newTurn();
        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();
        logic.buyPropriety();

        final Player owner = CardAdapter.buyableAdapter(logic.getCurrentCard()).getOwner();

        logic.newTurn();
        assertEquals(START_BALANCE, logic.getCurrentPlayer().getBankAccount().getBalance());

        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();

        final int fees = CardAdapter.buyableAdapter(logic.getCurrentCard()).getTransitFees();

        assertEquals(START_BALANCE - fees, logic.getCurrentPlayer().getBankAccount().getBalance());
        assertEquals(START_BALANCE - VISERBA_PRICE + fees, owner.getBankAccount().getBalance());

    }

    @Test

    void defeatTest() {

        assertEquals(START_PLAYER_SIZE, logic.getPlayerList().size());
        assertEquals(START_DEFEAT_SIZE, logic.getDefeatedList().size());

        logic.newTurn();
        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();
        logic.buyPropriety();

        logic.newTurn();
        logic.getCurrentPlayer().receivePayment(LOW_BALANCE);
        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();

        assertEquals(FINAL_PLAYER_SIZE, logic.getPlayerList().size());
        assertEquals(FINAL_DEFEAT_SIZE, logic.getDefeatedList().size());
    }

    @Test

    void endGameTest() {
        this.defeatTest();
        assertTrue(logic.isOver());
    }

}
