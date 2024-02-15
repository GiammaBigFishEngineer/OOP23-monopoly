package app.game.view;

import javax.swing.*;

import app.game.controller.GameControllerImpl;
import app.player.apii.Player;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.*;

/**
 * ButtonsView.
 */
public class ButtonPanelView extends JPanel {

    private GameControllerImpl logic;

    private Map<BtnCodeEnum, Boolean> btnCodeList = new HashMap<>();
    private Map<BtnCodeEnum, JButton> btnList = new HashMap<>();

    private JButton rollDice;
    private JButton buyPropriety;
    private JButton sellPropriety;
    private JButton buyHouse;
    private JButton endTurn;
    private JButton saveGame;

    private GameObserverImpl observer;

    public ButtonPanelView(List<String> playersNames, GameObserverImpl obs) throws IOException {

        this.logic = new GameControllerImpl(playersNames);
        this.observer = obs;

        this.setLayout(new GridLayout(2, 3));
        this.setBackground(Color.lightGray);

        /*
         * RollDice button
         */

        rollDice = new JButton("Roll Dice");
        this.add(rollDice);
        btnList.put(BtnCodeEnum.rollDice, rollDice);

        rollDice.addActionListener(e -> {

            Player currentPlayer = logic.getCurrentPlayer();

            if (logic.rollDice(true)) {

                int diceValue = logic.getDiceValue();

                observer.update(diceValue, currentPlayer, "rollDice");
                observer.update(-1, currentPlayer, "YouLoseMessage");
                this.newTurn();

            } else {

                changeButtonVisibility();

                currentPlayer = logic.getCurrentPlayer();
                int diceValue = logic.getDiceValue();

                observer.update(diceValue, currentPlayer, "rollDice");
                observer.update(-1, currentPlayer, "refreshPlayerPanel");
                observer.update(-1, currentPlayer, "refreshPlayerPosition");
            }

            rollDice.setEnabled(false);
        });

        /*
         * buyPropriety button
         */

        buyPropriety = new JButton("Buy Propriety");
        this.add(buyPropriety);
        btnList.put(BtnCodeEnum.buyPropriety, buyPropriety);

        buyPropriety.addActionListener(e -> {

            Player currentPlayer = logic.getCurrentPlayer();

            if (!logic.buyPropriety()) {
                obs.update(-1, currentPlayer, "NoBuyMessage");
            }
            currentPlayer = logic.getCurrentPlayer();
            observer.update(-1, currentPlayer, "refreshPlayerPanel");

            buyPropriety.setEnabled(false);

        });

        /*
         * SellPropriety button
         */

        sellPropriety = new JButton("Sell Propriety");
        this.add(sellPropriety);
        btnList.put(BtnCodeEnum.sellPropriety, sellPropriety);

        sellPropriety.addActionListener(e -> {

            Player currentPlayer = logic.getCurrentPlayer();

            logic.sellPropriety();
            currentPlayer = logic.getCurrentPlayer();
            observer.update(-1, currentPlayer, "refreshPlayerPanel");

            sellPropriety.setEnabled(false);

        });

        /*
         * BuyHouse button
         */

        buyHouse = new JButton("Buy House");
        this.add(buyHouse);
        btnList.put(BtnCodeEnum.buyHouse, buyHouse);

        buyHouse.addActionListener(e -> {

            Player currentPlayer = logic.getCurrentPlayer();

            if (!logic.buildHouse()) {
                observer.update(-1, currentPlayer, "NoBuyMessage");
            }
            currentPlayer = logic.getCurrentPlayer();
            observer.update(-1, currentPlayer, "refreshPlayerPanel");
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

        });

        this.newTurn();

    }

    public void newTurn() {

        logic.newTurn();

        Player currentPlayer = logic.getCurrentPlayer();

        currentPlayer = logic.getCurrentPlayer();

        observer.update(-1, currentPlayer, "refreshPlayerPanel");

        if (logic.isCurrentPlayerInJail()) {

            if (observer.update(-1, currentPlayer, "bail")) {

                logic.enableSingleButton(BtnCodeEnum.rollDice);

            } else {
                logic.tryLuckyBail();
                if (logic.isCurrentPlayerInJail()) {
                    observer.update(-1, currentPlayer, "stillInPrison");
                } else {
                    observer.update(-1, currentPlayer, "freeToGo");
                    logic.enableSingleButton(BtnCodeEnum.rollDice);
                }
            }

        } else {
            logic.enableSingleButton(BtnCodeEnum.rollDice);
        }

        changeButtonVisibility();
    }

    public void changeButtonVisibility() {

        this.btnCodeList.putAll(logic.getBtnStatus());

        for (var entry : btnCodeList.entrySet()) {

            var code = entry.getKey();
            var bool = entry.getValue();

            btnList.get(code).setEnabled(bool);

        }
    }

    public GameControllerImpl getLogic() {
        return this.logic;
    }

}
