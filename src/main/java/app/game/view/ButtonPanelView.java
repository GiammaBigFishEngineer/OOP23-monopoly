package app.game.view;

import javax.swing.*;

import app.game.apii.GameController;
import app.game.controller.GameControllerImpl;
import app.game.utils.Dice;
import app.player.apii.Player;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.*;

/**
 * ButtonsView.
 */
public class ButtonPanelView extends GameObservableImpl {

    final private GameController logic;

    final private Map<BtnCodeEnum, Boolean> btnCodeList = new HashMap<>();
    final private Map<BtnCodeEnum, JButton> btnList = new HashMap<>();

    final private JButton rollDice;
    final private JButton buyPropriety;
    final private JButton sellPropriety;
    final private JButton buyHouse;
    final private JButton endTurn;
    final private JButton saveGame;

    public ButtonPanelView(final List<String> playersNames, final GameObserverImpl obs) throws IOException {

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

            logic.rollDice(true);

            final Dice dice = logic.getDice();

            updateObserver(Optional.of(dice), "RollDice");

            refreshPanelView();
            refreshPositionView();

            this.turnViewControl();

            rollDice.setEnabled(false);

        });

        /*
         * buyPropriety button
         */

        buyPropriety = new JButton("Buy Propriety");
        this.add(buyPropriety);
        btnList.put(BtnCodeEnum.buyPropriety, buyPropriety);

        buyPropriety.addActionListener(e -> {

            if (!logic.buyPropriety()) {
                updateObserver(Optional.empty(), "NoBuy");
            }

            refreshPanelView();

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

            refreshPanelView();

            sellPropriety.setEnabled(false);

        });

        /*
         * BuyHouse button
         */

        buyHouse = new JButton("Buy House");
        this.add(buyHouse);
        btnList.put(BtnCodeEnum.buyHouse, buyHouse);

        buyHouse.addActionListener(e -> {

            if (!logic.buildHouse()) {
                updateObserver(Optional.empty(), "NoBuild");
            }

            refreshPanelView();
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
            refreshPanelView();

        });

        /*
         * SaveGame button
         */

        saveGame = new JButton("Save Game");
        this.add(saveGame);

        saveGame.addActionListener(e -> {

            updateObserver(Optional.empty(), "Save");

            saveGame.setEnabled(false);

        });

        this.newTurn();

    }

    public void newTurn() {

        logic.newTurn();

        if (logic.isCurrentPlayerInJail()) {

            final Player currentPlayer = logic.getCurrentPlayer();

            if (updateObserver(Optional.of(currentPlayer), "bail")) {

                logic.enableSingleButton(BtnCodeEnum.rollDice);

            } else {

                logic.tryLuckyBail();

                if (logic.isCurrentPlayerInJail()) {
                    updateObserver(Optional.empty(), "NotDoubleDice");
                } else {
                    updateObserver(Optional.empty(), "DoubleDice");
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

        for (final var entry : btnCodeList.entrySet()) {

            final var code = entry.getKey();
            final var bool = entry.getValue();

            btnList.get(code).setEnabled(bool);

        }
    }

    public void turnViewControl() {

        if (logic.isCurrentPlayerOnUnforseen()) {
            updateObserver(Optional.of(logic.getUnforseenMessage()), "UnbuyableAction");
        }

        if (logic.isCurrentPlayerOnOwnedPropriety()) {
            updateObserver(Optional.of(logic.getOwner()), "Fees");
        }

        if (logic.isCurrentPlayerDefeated()) {

            final String currentPlayerName = logic.getCurrentPlayer().getName();

            updateObserver(Optional.of(currentPlayerName), "Eliminate");

            if (logic.isOver()) {

                updateObserver(Optional.of(currentPlayerName), "Win");

                logic.endGame();

            } else {

                this.newTurn();

            }

        }

        refreshPanelView();
        refreshPositionView();

        changeButtonVisibility();
    }

    public void initializeView() {

        final int nPlayers = logic.getPlayerList().size();
        for (int i = 0; i < nPlayers; i++) {
            logic.newTurn();
            refreshPositionView();
        }
        refreshPanelView();
    }

    public void refreshPanelView() {
        final Player currentPlayer = logic.getCurrentPlayer();
        updateObserver(Optional.of(currentPlayer), "refreshPlayerPanel");
    }

    public void refreshPositionView() {
        final Player currentPlayer = logic.getCurrentPlayer();
        updateObserver(Optional.of(currentPlayer), "refreshPlayerPosition");
    }

    public GameController getLogic() {
        return this.logic;
    }

}
