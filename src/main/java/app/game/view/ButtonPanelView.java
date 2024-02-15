package app.game.view;

import javax.swing.*;

import app.game.apii.GameObserver;
import app.game.controller.ButtonControllerImpl;
import app.player.apii.Player;

import app.card.apii.Card;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.*;

/**
 * ButtonsView.
 */
public class ButtonPanelView extends JPanel {

    private ButtonControllerImpl logic;

    private Map<BtnCodeEnum, Boolean> btnCodeList = new HashMap<>();
    private Map<BtnCodeEnum, JButton> btnList = new HashMap<>();

    private JButton rollDice;
    private JButton buyPropriety;
    private JButton sellPropriety;
    private JButton buyHouse;
    private JButton endTurn;
    private JButton saveGame;

    public ButtonPanelView(List<Player> playersList, List<Card> cardList, GameObserver obs) {

        logic = new ButtonControllerImpl(cardList, playersList);
        logic.registerObserver(obs);

        this.setLayout(new GridLayout(2, 3));
        this.setBackground(Color.lightGray);

        /*
         * RollDice button
         */

        rollDice = new JButton("Roll Dice");
        this.add(rollDice);
        btnList.put(BtnCodeEnum.rollDice, rollDice);

        rollDice.addActionListener(e -> {
            logic.rollDice();
            this.btnCodeList.putAll(logic.getBtnCodeList());
            changeButtonVisibility();
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

        });

        /*
         * EndTurn button
         */

        endTurn = new JButton("End Turn");
        this.add(endTurn);
        btnList.put(BtnCodeEnum.endTurn, endTurn);

        endTurn.addActionListener(e -> {

            this.newTurn();

        });

        /*
         * SaveGame button
         */

        saveGame = new JButton("Save Game");
        this.add(saveGame);

        saveGame.addActionListener(e -> {
            logic.saveGame();
        });

        this.newTurn();

    }

    public void newTurn() {
        logic.nextTurn();
        this.btnCodeList.putAll(logic.getBtnCodeList());
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
