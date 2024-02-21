package app.game.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Comparator;

import app.card.api.Card;
import app.card.api.CardAdapter;
import app.card.api.StaticActionStrategy.TriggeredEvent;
import app.card.impl.CardFactoryImpl;
import app.game.api.GameController;
import app.game.api.SaveController;
import app.game.utils.BtnCodeEnum;
import app.game.utils.BtnCodeState;
import app.game.utils.Dice;

import app.player.api.Player;
import app.player.impl.PlayerImpl;

import java.awt.Window;

/**
 * this class represent the implementation of the game logic.
 */

public final class GameControllerImpl implements GameController {

    private static final int GO_TO_JAIL_ID = 18;
    private static final int GO_ID = 0;
    private static final int INITIAL_AMOUNT = 500;
    private static final int UNFORSEEN1_ID = 2;
    private static final int UNFORSEEN2_ID = 9;
    private static final int UNFORSEEN3_ID = 15;

    private static final int BAIL = 100;

    private final List<Player> players;
    private final List<Player> defeated;
    private Player currentPlayer;
    private int currentPlayerIndex = -1;

    private final List<String> playersName;

    private final List<Card> tableList;
    private final List<Card> cardsList;
    private Card currentCard;
    private int currentCardIndex;

    private boolean landedOnUnforseen;
    private String unforseenMessage;
    private boolean landedOnOwned;
    private String ownerName;

    private final Dice currentDice;
    private boolean doubleDice;

    private int totalResult;

    private final Map<BtnCodeEnum, BtnCodeState> btnList;

    private final SaveController saveLogic;

    /**
     * 
     * @param names is a list that contains all the player names
     * @throws IOException
     */

    public GameControllerImpl(final List<String> names) throws IOException {

        this.tableList = new CardFactoryImpl().cardsInitializer();

        this.cardsList = tableList.stream()
                .sorted(Comparator.comparingInt(Card::getCardId))
                .collect(Collectors.toList());

        this.playersName = new ArrayList<>();
        playersName.addAll(names);

        this.players = new ArrayList<>();
        this.initializePlayer();

        currentDice = new Dice();

        defeated = new ArrayList<>();

        btnList = new HashMap<>();
        btnList.put(BtnCodeEnum.BUY_HOUSE, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.BUY_PROPRIETY, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.END_TURN, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.ROLL_DICE, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.SELL_PROPRIETY, BtnCodeState.DISABLED);
        btnList.put(BtnCodeEnum.UNFORSEEN, BtnCodeState.DISABLED);

        saveLogic = new SaveControllerImpl();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void newTurn() {

        this.disableAllBtn();

        this.nextPlayer();
        this.landedOnUnforseen = false;
        this.landedOnOwned = false;

    }

    /**
     * this method is called in newTurn and it is used to set the next player in
     * the.
     * players list as the current player
     */

    public void nextPlayer() {

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void tryLuckyBail() {

        rollDice(false);

        if (doubleDice) {

            currentPlayer.setInJail(false);
            enableSingleButton(BtnCodeEnum.ROLL_DICE);

        } else {

            disableAllBtn();

            enableSingleButton(BtnCodeEnum.END_TURN);

        }

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void rollDice(final Boolean b) {

        int res1;
        int res2;

        currentDice.rollDice();

        res1 = currentDice.getDie1Result();
        res2 = currentDice.getDie2Result();
        totalResult = currentDice.getDiceResult();

        if (res1 == res2) {
            doubleDice = true;
        } else {
            doubleDice = false;
        }

        if (b) {
            disableSingleButton(BtnCodeEnum.ROLL_DICE);
            startTurn();

        }

    }

    /**
     * {@inheritDoc}
     */

    @Override

    public void startTurn() {

        final int position = currentPlayer.getCurrentPosition();

        final int finalPosition = position + totalResult;

        if (finalPosition >= cardsList.size()) {

            final int newPosition = finalPosition - (int) cardsList.size();
            currentPlayer.setPosition(newPosition);
            CardAdapter.unbuyableAdapter(this.cardsList.get(GO_ID)).makeAction(currentPlayer);

        } else {
            currentPlayer.setPosition(finalPosition);
        }

        currentCardIndex = currentPlayer.getCurrentPosition();

        currentCard = cardsList.get(currentCardIndex);

        handleCard();

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void handleCard() {

        if (currentCard.isUnbuyable()) {

            handleUnbuyable();

        } else if (currentCard.isBuildable()) {

            handleBuildable();
            enableSingleButton(BtnCodeEnum.END_TURN);

        } else if (currentCard.isBuyable() && !currentCard.isBuildable()) {

            handleBuyable();
            enableSingleButton(BtnCodeEnum.END_TURN);

        }

    }

    /**
     * this method is called if the current player has landed on an unbuyable card.
     * it acts differently based on what sottotype of card the player has landed on
     * (Unforseen or goToJail card)
     */

    public void handleUnbuyable() {

        if (currentCard.getCardId() == UNFORSEEN1_ID || currentCard.getCardId() == UNFORSEEN2_ID
                || currentCard.getCardId() == UNFORSEEN3_ID) {

            disableAllBtn();
            enableSingleButton(BtnCodeEnum.UNFORSEEN);

        } else {
            enableSingleButton(BtnCodeEnum.END_TURN);
        }

        if (currentCard.getCardId() == GO_TO_JAIL_ID) {
            final TriggeredEvent e = CardAdapter.unbuyableAdapter(currentCard).makeAction(currentPlayer);
            this.unforseenMessage = e.getMessage();
            this.landedOnUnforseen = true;
            enableSingleButton(BtnCodeEnum.END_TURN);
        }

    }

    /**
     * this method is called if the current player has landed on a buildable card.
     * It checks whether the card is owned and, if so, who is it
     */

    public void handleBuildable() {

        final Boolean owned = CardAdapter.buildableAdapter(currentCard).isOwned();

        if (owned) {

            final Player owner = CardAdapter.buildableAdapter(currentCard).getOwner();

            if (currentPlayer.equals(owner)) {

                enableSingleButton(BtnCodeEnum.BUY_HOUSE);
                enableSingleButton(BtnCodeEnum.SELL_PROPRIETY);

            } else {
                payFees(owner);
            }

        } else {
            enableSingleButton(BtnCodeEnum.BUY_PROPRIETY);

        }

    }

    /**
     * this method is called if the current player has landed on a buyable card.
     * It checks whether the card is owned and, if so, who is it.
     * Unlike handleBuildable it activates different buttons
     */

    public void handleBuyable() {
        final Boolean owned = CardAdapter.buyableAdapter(currentCard).isOwned();

        if (owned) {

            final Player owner = CardAdapter.buyableAdapter(currentCard).getOwner();

            if (!currentPlayer.equals(owner)) {
                payFees(owner);
            } else {
                enableSingleButton(BtnCodeEnum.SELL_PROPRIETY);
            }

        } else {
            enableSingleButton(BtnCodeEnum.BUY_PROPRIETY);

        }
    }

    /**
     * this method is called if the current player has landed on an unbuyable card
     * and in particular if he is on an unforseen.
     * It acts differently by checking whether the unforseen concerns movement or
     * payment.
     */

    @Override
    public void pickUnforseen() {
        final TriggeredEvent e = CardAdapter.unbuyableAdapter(currentCard).makeAction(currentPlayer);
        this.unforseenMessage = e.getMessage();
        this.landedOnUnforseen = true;

        final var balance = currentPlayer.getBankAccount().getBalance();

        if (currentCardIndex != currentPlayer.getCurrentPosition()) {

            currentCardIndex = currentPlayer.getCurrentPosition();

            currentCard = cardsList.get(currentCardIndex);

            handleCard();
        } else if (balance != currentPlayer.getBankAccount().getBalance()
                && e.equals(TriggeredEvent.UNPERFORMED)) {

            this.defeatPlayer();

        }

        disableSingleButton(BtnCodeEnum.UNFORSEEN);
        enableSingleButton(BtnCodeEnum.END_TURN);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean buyPropriety() {

        disableSingleButton(BtnCodeEnum.BUY_PROPRIETY);

        return currentPlayer.buyBox(CardAdapter.buyableAdapter(currentCard));

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean buildHouse() {

        disableSingleButton(BtnCodeEnum.BUY_HOUSE);

        return currentPlayer.buildHouse(CardAdapter.buildableAdapter(currentCard));

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void sellPropriety() {

        disableSingleButton(BtnCodeEnum.SELL_PROPRIETY);
        currentPlayer.sellBuyable(CardAdapter.buyableAdapter(currentCard));
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void payFees(final Player owner) {
        landedOnOwned = true;
        ownerName = CardAdapter.buyableAdapter(currentCard).getOwner().getName();

        final int fees = CardAdapter.buyableAdapter(currentCard).getTransitFees();

        if (!currentPlayer.payPlayer(owner, fees)) {
            this.defeatPlayer();
        }

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void defeatPlayer() {

        defeated.add(currentPlayer);
        players.remove(currentPlayer);

        final var map = currentPlayer.getMap();

        for (final var entry : map.entrySet()) {
            if (entry.getValue().isPresent()) {
                CardAdapter.buyableAdapter(entry.getKey()).clearOwner();
            }
        }

        newIndex();

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isOver() {
        return players.size() == 1;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isCurrentPlayerDefeated() {
        return defeated.contains(currentPlayer);
    }

    /**
     * this method is used when a player got defeated and is useful for calculating.
     * the next current player
     */

    public void newIndex() {
        this.currentPlayerIndex--;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void enableSingleButton(final BtnCodeEnum code) {

        btnList.put(code, BtnCodeState.ENABLED);

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void disableSingleButton(final BtnCodeEnum code) {

        btnList.put(code, BtnCodeState.DISABLED);

    }

    /**
     * {@inheritDoc}
     */

    @Override

    public void disableAllBtn() {

        for (final var entry : btnList.entrySet()) {
            entry.setValue(BtnCodeState.DISABLED);
        }

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void initializePlayer() {
        var id = 1;
        for (final String name : playersName) {

            this.players.add(new PlayerImpl(name, id, cardsList, INITIAL_AMOUNT));
            id++;

        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Map<BtnCodeEnum, BtnCodeState> getBtnStatus() {

        return new HashMap<>(btnList);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Player getCurrentPlayer() {
        final Player copyPlayer = new PlayerImpl(currentPlayer.getName(),
                currentPlayer.getID(),
                cardsList,
                currentPlayer.getBankAccount().getBalance());

        copyPlayer.setMap(currentPlayer.getMap());
        copyPlayer.setPosition(currentPlayer.getCurrentPosition());
        copyPlayer.setInJail(isCurrentPlayerInJail());
        return copyPlayer;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Dice getDice() {
        return this.currentDice;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Card getCurrentCard() {
        return currentCard;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Card> getTableList() {

        return new ArrayList<>(tableList);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Card> getCardList() {

        return new ArrayList<>(cardsList);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Player> getPlayerList() {

        return new ArrayList<>(players);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Player> getDefeatedList() {
        return new ArrayList<>(defeated);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Boolean isCurrentPlayerInJail() {
        return currentPlayer.isInJail();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDiceValue(final int value) {
        this.totalResult = value;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isCurrentPlayerOnUnforseen() {
        return this.landedOnUnforseen;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getUnforseenMessage() {
        return this.unforseenMessage;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isCurrentPlayerOnOwnedPropriety() {
        return this.landedOnOwned;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getOwner() {
        return this.ownerName;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void saveGame() {
        saveLogic.saveGame(players);
    }

    /**
     * {@inheritDoc}
     */
    @Override

    public void hasPayedBail() {
        currentPlayer.setInJail(false);
        currentPlayer.payPlayer(null, BAIL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void quitGame() {
        final Window[] windows = Window.getWindows();
        for (final Window window : windows) {
            window.dispose();
        }
    }

}
