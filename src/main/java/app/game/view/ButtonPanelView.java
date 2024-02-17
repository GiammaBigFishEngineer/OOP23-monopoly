package app.game.view;

import javax.swing.JButton;

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

    private GameController gameLogic;

    private final Map<BtnCodeEnum, Boolean> btnCodeList = new HashMap<>();
    private final Map<BtnCodeEnum, JButton> btnList = new HashMap<>();

    private final JButton rollDice;
    private final JButton buyPropriety;
    private final JButton sellPropriety;
    private final JButton buyHouse;
    private final JButton endTurn;
    private final JButton saveGame;

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
        btnList.put(BtnCodeEnum.rollDice, rollDice);

        rollDice.addActionListener(e -> {

            gameLogic.rollDice(true);

            final Dice dice = gameLogic.getDice();

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
        btnList.put(BtnCodeEnum.sellPropriety, sellPropriety);

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
        btnList.put(BtnCodeEnum.buyHouse, buyHouse);

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

        if (gameLogic.isCurrentPlayerInJail()) {

            final Player currentPlayer = gameLogic.getCurrentPlayer();

            if (updateObserver(Optional.of(currentPlayer), "bail")) {

                gameLogic.enableSingleButton(BtnCodeEnum.rollDice);

            } else {

                gameLogic.tryLuckyBail();

                if (gameLogic.isCurrentPlayerInJail()) {
                    updateObserver(Optional.empty(), "NotDoubleDice");
                } else {
                    updateObserver(Optional.empty(), "DoubleDice");
                    gameLogic.enableSingleButton(BtnCodeEnum.rollDice);
                }
            }

        } else {
            gameLogic.enableSingleButton(BtnCodeEnum.rollDice);
        }

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

    public void turnViewControl() {

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
     * this method returns gameLogic
     */

    public GameController getLogic() {
        return this.gameLogic;
    }

}
