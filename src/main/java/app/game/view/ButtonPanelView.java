package app.game.view;

import javax.swing.*;

import app.game.apii.GameController;
import app.game.apii.GameObserver;
import app.game.controller.GameControllerImpl;
import app.player.apii.Player;

import app.card.apii.Card;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.*;

/**
 * ButtonsView.
 */
public class ButtonPanelView extends JPanel {

    private GameController logic;

    private Map<BtnCodeEnum, Boolean> btnCodeList = new HashMap<>();
    private Map<BtnCodeEnum, JButton> btnList = new HashMap<>();

    private JButton rollDice;
    private JButton buyPropriety;
    private JButton sellPropriety;
    private JButton buyHouse;
    private JButton endTurn;
    private JButton saveGame;

    private GameObserver obs;

    private Player currentPlayer;

    public ButtonPanelView(List<Player> playersList, List<Card> cardList, GameObserver obs) {

        this.obs = obs;

        logic = new GameControllerImpl(playersList, cardList);

        this.setLayout(new GridLayout(2, 3));
        this.setBackground(Color.lightGray);

        /*
         * RollDice button
         */

        rollDice = new JButton("Roll Dice");
        this.add(rollDice);
        btnList.put(BtnCodeEnum.rollDice, rollDice);

        rollDice.addActionListener(e -> {
            logic.rollDice(true);
            btnCodeList.putAll(logic.getBtnStatus());

            changeButtonVisibility();

            currentPlayer = logic.getCurrentPlayer();
            var diceValue = logic.getDiceValue();
            obs.update(diceValue, currentPlayer, "rollDice");
            obs.update(-1, currentPlayer, "refreshPlayerPosition");
            obs.update(-1, currentPlayer, "refreshPlayerPanel");
        });

        /*
         * buyPropriety button
         */

        buyPropriety = new JButton("Buy Propriety");
        this.add(buyPropriety);
        btnList.put(BtnCodeEnum.buyPropriety, buyPropriety);

        buyPropriety.addActionListener(e -> {

            logic.buyPropriety();

            buyPropriety.setEnabled(false);

            currentPlayer = logic.getCurrentPlayer();
            obs.update(-1, currentPlayer, "refreshPlayerPanel");

        });

        /*
         * SellPropriety button
         */

        sellPropriety = new JButton("Sell Propriety");
        this.add(sellPropriety);
        btnList.put(BtnCodeEnum.sellPropriety, sellPropriety);

        sellPropriety.addActionListener(e -> {

            logic.sellPropriety();
            sellPropriety.setEnabled(false);

            currentPlayer = logic.getCurrentPlayer();
            obs.update(-1, currentPlayer, "refreshPlayerPanel");

        });

        /*
         * BuyHouse button
         */

        buyHouse = new JButton("Buy House");
        this.add(buyHouse);
        btnList.put(BtnCodeEnum.buyHouse, buyHouse);

        buyHouse.addActionListener(e -> {

            logic.buildHouse();
            buyHouse.setEnabled(false);

            currentPlayer = logic.getCurrentPlayer();
            obs.update(-1, currentPlayer, "refreshPlayerPanel");

        });

        /*
         * EndTurn button
         */

        endTurn = new JButton("End Turn");
        this.add(endTurn);
        btnList.put(BtnCodeEnum.endTurn, endTurn);

        endTurn.addActionListener(e -> {

            this.nextTurn();

        });

        /*
         * SaveGame button
         */

        saveGame = new JButton("Save Game");
        this.add(saveGame);

        this.nextTurn();

    }

    public void nextTurn() {

        logic.newTurn();

        currentPlayer = logic.getCurrentPlayer();

        obs.update(-1, currentPlayer, "refreshPlayerPanel");

        if (logic.isCurrentPlayerInJail()) {

            if (obs.update(-1, currentPlayer, "bail")) {

                logic.enableSingleButton(BtnCodeEnum.rollDice);

            } else {
                logic.tryLuckyBail();
            }

        } else {
            logic.enableSingleButton(BtnCodeEnum.rollDice);
        }

        btnCodeList.putAll(logic.getBtnStatus());

        changeButtonVisibility();

    }

    public void changeButtonVisibility() {
        for (var entry : btnCodeList.entrySet()) {

            var code = entry.getKey();
            var bool = entry.getValue();

            btnList.get(code).setEnabled(bool);

        }
    }

}
