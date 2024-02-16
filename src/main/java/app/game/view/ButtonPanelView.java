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
public class ButtonPanelView extends GameObservableImpl {

    private GameControllerImpl logic;

    private Map<BtnCodeEnum, Boolean> btnCodeList = new HashMap<>();
    private Map<BtnCodeEnum, JButton> btnList = new HashMap<>();

    private JButton rollDice;
    private JButton buyPropriety;
    private JButton sellPropriety;
    private JButton buyHouse;
    private JButton endTurn;
    private JButton saveGame;

    public ButtonPanelView(List<String> playersNames, GameObserverImpl obs) throws IOException {

        this.logic = new GameControllerImpl(playersNames);
        this.registerObserver(obs);

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

            int diceValue = logic.rollDice(true);

            updateObserver(diceValue, Optional.empty(), "RollDice");

            if (logic.isCurrentPlayerDefeated()) {
                updateObserver(-1, Optional.of(currentPlayer), "Eliminate");

                if (logic.isOver()) {

                    updateObserver(-1, Optional.of(currentPlayer), "Win");

                    logic.endGame();

                } else {

                    this.newTurn();

                }

            } else {

                refreshPanelView(currentPlayer);
                refreshPositionView(currentPlayer);
                changeButtonVisibility();
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
                updateObserver(-1, Optional.empty(), "NoBuy");
            }

            refreshPanelView(currentPlayer);

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

            refreshPanelView(currentPlayer);

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
                updateObserver(-1, Optional.empty(), "NoBuild");
            }

            refreshPanelView(currentPlayer);
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

            updateObserver(-1, Optional.empty(), "Save");

            saveGame.setEnabled(false);

        });

        this.newTurn();

    }

    public void newTurn() {

        logic.newTurn();

        Player currentPlayer = logic.getCurrentPlayer();

        refreshPanelView(currentPlayer);

        if (logic.isCurrentPlayerInJail()) {

            if (updateObserver(-1, Optional.of(currentPlayer), "bail")) {

                logic.enableSingleButton(BtnCodeEnum.rollDice);

            } else {

                logic.tryLuckyBail();

                if (logic.isCurrentPlayerInJail()) {
                    updateObserver(-1, Optional.empty(), "NotDoubleDice");
                } else {
                    updateObserver(-1, Optional.empty(), "DoubleDice");
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

    public void refreshPanelView(Player currentPlayer) {
        updateObserver(-1, Optional.of(currentPlayer), "refreshPlayerPanel");
    }

    public void refreshPositionView(Player currentPlayer) {
        updateObserver(-1, Optional.of(currentPlayer), "refreshPlayerPosition");
    }

    public GameControllerImpl getLogic() {
        return this.logic;
    }

}
