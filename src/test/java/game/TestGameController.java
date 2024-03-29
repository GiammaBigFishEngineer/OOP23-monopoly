package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.card.api.Card;
import app.game.api.GameController;
import app.game.controller.GameControllerImpl;
import app.game.utils.BtnCodeEnum;
import app.game.utils.BtnCodeState;
import app.player.api.Player;

import java.io.IOException;
import java.util.List;

import java.util.Map;
import java.util.HashMap;

class TestGameController {

    private GameController logic;

    private static final int VISERBA_ID = 4;
    private static final int VISERBA_PRICE = 100;
    private static final int GO_PAYMENT = 50;
    private static final int GO_ID = 0;
    private static final int VISERBA_TO_GO_DISTANCE = 20;
    private static final int VISERBA_TO_UNFORSEEN = 5;
    private static final int STAZIONE_NORD_ID = 16;
    private static final int GO_TO_STAZIONE_NORD_DISTANCE = 40;
    private static final int TABLE_LENGHT = 24;
    private static final int TABLE_LENGHT_UP = 25;
    private static final int START_BALANCE = 500;
    private static final int GRAND_HOTEL_ID = 23;
    private static final int VIA_BALBLA = 17;
    private static final int BALBLA_TO_AUGUSTO = 2;
    private static final int AUGUSTO_TO_GRAND_HOTEL = 4;
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
        assertTrue(logic.isCurrentPlayerInJail());

    }

    @Test

    void testEnabledButtons() {

        Map<BtnCodeEnum, BtnCodeState> btnList;

        logic.newTurn();

        btnList = new HashMap<>();
        btnList.put(BtnCodeEnum.BUY_HOUSE, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.END_TURN, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.ROLL_DICE, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.UNFORSEEN, BtnCodeState.DISABLED);

        assertEquals(btnList, logic.getBtnStatus());

        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();

        btnList.put(BtnCodeEnum.BUY_HOUSE, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, BtnCodeState.ENABLED);
        btnList.put(BtnCodeEnum.END_TURN, BtnCodeState.ENABLED);
        btnList.put(BtnCodeEnum.ROLL_DICE, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.UNFORSEEN, BtnCodeState.DISABLED);

        assertEquals(btnList, logic.getBtnStatus());

        logic.buyPropriety();

        logic.setDiceValue(THIS);
        logic.startTurn();

        btnList.put(BtnCodeEnum.BUY_HOUSE, BtnCodeState.ENABLED);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.END_TURN, BtnCodeState.ENABLED);
        btnList.put(BtnCodeEnum.ROLL_DICE, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, BtnCodeState.ENABLED);
        btnList.put(BtnCodeEnum.UNFORSEEN, BtnCodeState.DISABLED);

        assertEquals(btnList, logic.getBtnStatus());

        logic.setDiceValue(VISERBA_TO_UNFORSEEN);
        logic.startTurn();

        btnList.put(BtnCodeEnum.BUY_HOUSE, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.END_TURN, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.ROLL_DICE, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.UNFORSEEN, BtnCodeState.ENABLED);

        assertEquals(btnList, logic.getBtnStatus());

    }

    @Test

    void buyProprietyTest() {

        logic.newTurn();

        assertEquals(START_BALANCE, logic.getCurrentPlayer().getBankAccount().getBalance());

        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();
        logic.buyPropriety();

        final Player owner = logic.getCurrentCard().asBuildable().getOwner();

        assertEquals(owner.getName(), logic.getCurrentPlayer().getName());

        assertEquals(START_BALANCE - VISERBA_PRICE, logic.getCurrentPlayer().getBankAccount().getBalance());

    }

    @Test
    void buildHouseTest() {

        logic.newTurn();

        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();

        final int cardPrice = logic.getCurrentCard().asBuildable().getPrice();

        final int housePrice = logic.getCurrentCard().asBuildable().getHousePrice();

        final int total = cardPrice + housePrice;

        logic.buyPropriety();

        logic.buildHouse();

        assertEquals(START_BALANCE - total, logic.getCurrentPlayer().getBankAccount().getBalance());

    }

    @Test

    void payFeesTest() {
        logic.newTurn();
        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();
        logic.buyPropriety();

        final Player owner = logic.getCurrentCard().asBuyable().getOwner();

        logic.newTurn();
        assertEquals(START_BALANCE, logic.getCurrentPlayer().getBankAccount().getBalance());

        logic.setDiceValue(VISERBA_ID);
        logic.startTurn();

        final int fees = logic.getCurrentCard().asBuyable().getTransitFees();

        assertEquals(START_BALANCE - fees, logic.getCurrentPlayer().getBankAccount().getBalance());
        assertEquals(START_BALANCE - VISERBA_PRICE + fees, owner.getBankAccount().getBalance());

    }

    @Test

    void defeatTest() {

        assertEquals(START_PLAYER_SIZE, logic.getPlayerList().size());
        assertEquals(START_DEFEAT_SIZE, logic.getDefeatedList().size());

        logic.newTurn();
        logic.setDiceValue(GRAND_HOTEL_ID);
        logic.startTurn();
        logic.buyPropriety();

        logic.newTurn();
        logic.setDiceValue(VIA_BALBLA);
        logic.startTurn();
        logic.buyPropriety();

        logic.setDiceValue(BALBLA_TO_AUGUSTO);
        logic.startTurn();
        logic.buyPropriety();

        logic.setDiceValue(AUGUSTO_TO_GRAND_HOTEL);
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
