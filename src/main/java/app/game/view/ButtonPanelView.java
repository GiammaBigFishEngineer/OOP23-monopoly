package app.game.view;

import javax.swing.JButton;

import app.card.apii.Card;
import app.game.apii.GameController;
import app.game.controller.GameControllerImpl;
import app.game.utils.Dice;
import app.player.apii.Player;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

/**
 * ButtonsView.
 */
public final class ButtonPanelView extends GameObservableImpl {

    private final GameController gameLogic;

    private final Map<BtnCodeEnum, Boolean> btnCodeList = new HashMap<>();
    private final Map<BtnCodeEnum, JButton> btnList = new HashMap<>();

    private final JButton rollDice;
    private final JButton buyPropriety;
    private final JButton sellPropriety;
    private final JButton buyHouse;
    private final JButton endTurn;
    private final JButton saveGame;

    /**
     * 
     * @param playersNames
     * @param obs
     * @throws IOException
     */

    public ButtonPanelView(final List<String> playersNames, final GameObserverImpl obs) throws IOException {

        this.gameLogic = new GameControllerImpl(playersNames);
        this.registerObserver(obs);

        this.setLayout(new GridLayout(2, 3));
        this.setBackground(Color.lightGray);

        /*
         * RollDice button
         */

        rollDice = new JButton("Roll Dice");
        this.add(rollDice);
        btnList.put(BtnCodeEnum.ROLL_DICE, rollDice);

        rollDice.addActionListener(e -> {

            gameLogic.rollDice(true);

            final Dice dice = gameLogic.getDice();

            updateObserver(Optional.of(dice), "RollDice");

            refreshPanelView();
            refreshPositionView();

            this.updateMidTurnView();

            rollDice.setEnabled(false);

        });

        /*
         * buyPropriety button
         */

        buyPropriety = new JButton("Buy Propriety");
        this.add(buyPropriety);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, buyPropriety);

        buyPropriety.addActionListener(e -> {

            if (!gameLogic.buyPropriety()) {
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
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, sellPropriety);

        sellPropriety.addActionListener(e -> {

            gameLogic.sellPropriety();

            refreshPanelView();

            sellPropriety.setEnabled(false);

        });

        /*
         * BuyHouse button
         */

        buyHouse = new JButton("Buy House");
        this.add(buyHouse);
        btnList.put(BtnCodeEnum.BUY_HOUSE, buyHouse);

        buyHouse.addActionListener(e -> {

            if (!gameLogic.buildHouse()) {
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
        btnList.put(BtnCodeEnum.END_TURN, endTurn);

        endTurn.addActionListener(e -> {

            this.newTurn();

        });

        /*
         * SaveGame button
         */

        saveGame = new JButton("Save Game");
        this.add(saveGame);

        saveGame.addActionListener(e -> {

            gameLogic.saveGame();

            updateObserver(Optional.empty(), "Save");

            saveGame.setEnabled(false);

        });

    }

    /**
     * 
     */

    public void newTurn() {
        gameLogic.newTurn();
        this.updateStartTurnView();
    }

    /**
     * 
     */

    public void updateStartTurnView() {

        if (gameLogic.isCurrentPlayerInJail()) {

            final Player currentPlayer = gameLogic.getCurrentPlayer();

            if (updateObserver(Optional.of(currentPlayer), "bail")) {

                gameLogic.enableSingleButton(BtnCodeEnum.ROLL_DICE);

            } else {

                gameLogic.tryLuckyBail();

                if (gameLogic.isCurrentPlayerInJail()) {
                    updateObserver(Optional.empty(), "NotDoubleDice");
                } else {
                    updateObserver(Optional.empty(), "DoubleDice");
                    gameLogic.enableSingleButton(BtnCodeEnum.ROLL_DICE);
                }
            }

        } else {
            gameLogic.enableSingleButton(BtnCodeEnum.ROLL_DICE);
        }

        refreshPanelView();

        changeButtonVisibility();
    }

    /**
     * 
     */

    public void updateMidTurnView() {

        if (gameLogic.isCurrentPlayerOnUnforseen()) {
            updateObserver(Optional.of(gameLogic.getUnforseenMessage()), "UnbuyableAction");
        }

        if (gameLogic.isCurrentPlayerOnOwnedPropriety()) {
            updateObserver(Optional.of(gameLogic.getOwner()), "Fees");
        }

        if (gameLogic.isCurrentPlayerDefeated()) {

            final String currentPlayerName = gameLogic.getCurrentPlayer().getName();

            updateObserver(Optional.of(currentPlayerName), "Eliminate");

            if (gameLogic.isOver()) {

                updateObserver(Optional.of(currentPlayerName), "Win");

                gameLogic.endGame();

            } else {

                this.newTurn();

            }

        }

        refreshPanelView();
        refreshPositionView();

        changeButtonVisibility();
    }

    /**
     * 
     */

    public void changeButtonVisibility() {

        this.btnCodeList.putAll(gameLogic.getBtnStatus());

        for (final var entry : btnCodeList.entrySet()) {

            final var code = entry.getKey();
            final var bool = entry.getValue();

            btnList.get(code).setEnabled(bool);

        }
    }

    /**
     * 
     */

    public void initializeView() {

        final int nPlayers = gameLogic.getPlayerList().size();
        for (int i = 0; i < nPlayers; i++) {
            gameLogic.newTurn();
            refreshPositionView();
        }
        refreshPanelView();
    }

    /**
     * 
     */

    public void refreshPanelView() {
        final Player currentPlayer = gameLogic.getCurrentPlayer();
        updateObserver(Optional.of(currentPlayer), "refreshPlayerPanel");
    }

    /**
     * 
     */

    public void refreshPositionView() {
        final Player currentPlayer = gameLogic.getCurrentPlayer();
        updateObserver(Optional.of(currentPlayer), "refreshPlayerPosition");
    }

    /**
     * @returns GameController
     */

    public List<Card> getLogicCardList() {
        return this.gameLogic.getTableList();
    }

}