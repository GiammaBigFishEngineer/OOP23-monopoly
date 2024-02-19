package app.game.view;

import javax.swing.JButton;

import app.card.apii.Card;
import app.game.apii.GameController;
import app.game.controller.GameControllerImpl;
import app.game.utils.BtnCodeEnum;
import app.game.utils.Dice;
import app.game.utils.ObserverCodeEnum;
import app.player.apii.Player;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

/**
 * This class represent the buttons panel view.
 */
public final class ButtonPanelView extends ViewObservableImpl {

    private static final long serialVersionUID = 1L;

    private final transient GameController gameLogic;

    private final Map<BtnCodeEnum, Boolean> btnCodeList = new HashMap<>();
    private final Map<BtnCodeEnum, JButton> btnList = new HashMap<>();

    /**
     * Constructor
     * 
     * @param playersNames
     * @param obs
     * @throws IOException
     */

    public ButtonPanelView(final List<String> playersNames, final ViewObserverImpl obs) throws IOException {

        final JButton rollDice;
        final JButton buyPropriety;
        final JButton sellPropriety;
        final JButton buyHouse;
        final JButton endTurn;
        final JButton saveGame;
        final JButton pickUnforseen;
        final JButton quitGame;

        this.gameLogic = new GameControllerImpl(playersNames);
        this.registerObserver(obs);

        this.setLayout(new GridLayout(2, 3));
        this.setBackground(Color.lightGray);

        /*
         * In each action listeners the corresponding method is called and the views are
         * updated accordingly
         */

        /*
         * rollDice button
         */

        rollDice = new JButton("Lancia i Dadi!");
        this.add(rollDice);
        btnList.put(BtnCodeEnum.ROLL_DICE, rollDice);

        rollDice.addActionListener(e -> {

            gameLogic.rollDice(true);

            final Dice dice = gameLogic.getDice();

            updateObserver(Optional.of(dice), ObserverCodeEnum.ROLL_DICE);

            refreshPanelView();
            refreshPositionView();

            this.updateMidTurnView();

        });

        /*
         * buyPropriety button
         */

        buyPropriety = new JButton("Compra Proprieta'");
        this.add(buyPropriety);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, buyPropriety);

        buyPropriety.addActionListener(e -> {

            if (!gameLogic.buyPropriety()) {
                updateObserver(Optional.empty(), ObserverCodeEnum.NO_BUY);
            }

            refreshPanelView();

            changeButtonVisibility();

        });

        /*
         * sellPropriety button
         */

        sellPropriety = new JButton("Vendi Proprieta'");
        this.add(sellPropriety);
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, sellPropriety);

        sellPropriety.addActionListener(e -> {

            gameLogic.sellPropriety();

            refreshPanelView();

            changeButtonVisibility();

        });

        /*
         * saveGame button
         */

        saveGame = new JButton("Salva Gioco");
        this.add(saveGame);

        saveGame.addActionListener(e -> {

            gameLogic.saveGame();

            updateObserver(Optional.empty(), ObserverCodeEnum.SAVE);

            saveGame.setEnabled(false);

        });

        /*
         * endTurn button
         */

        endTurn = new JButton("Fine Turno");
        this.add(endTurn);
        btnList.put(BtnCodeEnum.END_TURN, endTurn);

        endTurn.addActionListener(e -> {

            this.newTurn();

        });

        /*
         * pickUnforseen button
         */

        pickUnforseen = new JButton("Pesca Imprevisto");
        this.add(pickUnforseen);
        btnList.put(BtnCodeEnum.UNFORSEEN, pickUnforseen);

        pickUnforseen.addActionListener(e -> {

            gameLogic.pickUnforseen();
            updateObserver(Optional.of(gameLogic.getUnforseenMessage()), ObserverCodeEnum.UNBUYABLE_ACTION);

            refreshPanelView();
            refreshPositionView();

            changeButtonVisibility();

        });

        /*
         * buyHouse button
         */

        buyHouse = new JButton("Costruisci Casa");
        this.add(buyHouse);
        btnList.put(BtnCodeEnum.BUY_HOUSE, buyHouse);

        buyHouse.addActionListener(e -> {

            if (!gameLogic.buildHouse()) {
                updateObserver(Optional.empty(), ObserverCodeEnum.NO_BUILD);
            }

            refreshPanelView();

            changeButtonVisibility();

        });

        /*
         * quitGame button
         */

        quitGame = new JButton("Esci Dal Gioco");
        this.add(quitGame);

        quitGame.addActionListener(e -> {
            if (updateObserver(Optional.empty(), ObserverCodeEnum.QUIT)) {
                gameLogic.quitGame();

            }
        });

    }

    /**
     * This method is called to start the game and at the end of every turn
     * to move on to the next turn
     */

    public void newTurn() {
        gameLogic.newTurn();
        refreshPanelView();
        this.updateStartTurnView();
    }

    /**
     * This methods is used to update the view at the start of every turn.
     * It cheks whether the player ended up in jail he previous turn and if so,
     * displays the relevant pop-ups
     */

    public void updateStartTurnView() {

        if (gameLogic.isCurrentPlayerInJail()) {

            final Player currentPlayer = gameLogic.getCurrentPlayer();

            if (updateObserver(Optional.of(currentPlayer), ObserverCodeEnum.BAIL)) {

                gameLogic.enableSingleButton(BtnCodeEnum.ROLL_DICE);
                gameLogic.hasPayedBail();

            } else {

                gameLogic.tryLuckyBail();

                if (gameLogic.isCurrentPlayerInJail()) {
                    updateObserver(Optional.empty(), ObserverCodeEnum.NOT_DOUBLE_DICE);
                } else {
                    updateObserver(Optional.empty(), ObserverCodeEnum.DOUBLE_DICE);

                }
            }

        } else {
            gameLogic.enableSingleButton(BtnCodeEnum.ROLL_DICE);
        }

        refreshPanelView();
        changeButtonVisibility();
    }

    /**
     * This methods is used to update the view after the player lands on a square.
     * It checks if the player has landed on an unforseen or on a propriety already
     * owned and displays the relevant pop-ups
     */

    public void updateMidTurnView() {

        if (gameLogic.isCurrentPlayerOnUnforseen()) {
            updateObserver(Optional.of(gameLogic.getUnforseenMessage()), ObserverCodeEnum.UNBUYABLE_ACTION);
        }

        if (gameLogic.isCurrentPlayerOnOwnedPropriety()) {
            updateObserver(Optional.of(gameLogic.getOwner()), ObserverCodeEnum.FEES);
        }

        if (gameLogic.isCurrentPlayerDefeated()) {

            final String currentPlayerName = gameLogic.getCurrentPlayer().getName();

            updateObserver(Optional.of(currentPlayerName), ObserverCodeEnum.ELIMINATE);

            if (gameLogic.isOver()) {

                updateObserver(Optional.of(currentPlayerName), ObserverCodeEnum.WIN);

                gameLogic.quitGame();

            } else {

                this.newTurn();

            }

        }

        refreshPanelView();
        refreshPositionView();

        changeButtonVisibility();
    }

    /**
     * This method is used to update the state of the buttons based on how their
     * corresponding codes in the logic have been updated
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
     * This method is called at the start of the game to initialize the players
     * position on the start box and to refresh te player panel with the first
     * player
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
     * This method is used to refresh the player panel when the player data has been
     * modified
     */

    public void refreshPanelView() {
        final Player currentPlayer = gameLogic.getCurrentPlayer();
        updateObserver(Optional.of(currentPlayer), ObserverCodeEnum.REFRESH_PLAYER_PANEL);
    }

    /**
     * This method is used to refresh the table panel when the player position has
     * been changed
     */

    public void refreshPositionView() {
        final Player currentPlayer = gameLogic.getCurrentPlayer();
        updateObserver(Optional.of(currentPlayer), ObserverCodeEnum.REFRESH_PLAYER_POSITION);
    }

    /**
     * @return card list
     */

    public List<Card> getLogicCardList() {
        return this.gameLogic.getTableList();
    }

}
