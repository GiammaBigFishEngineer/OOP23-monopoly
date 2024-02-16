package app.game.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.card.apii.StaticActionStrategy.TriggeredEvent;
import app.card.impl.CardFactoryImpl;
import app.card.impl.Unforseen;
import app.game.apii.GameController;

import app.game.utils.Dice;

import app.player.apii.Player;
import app.player.impl.PlayerImpl;
import app.game.view.BtnCodeEnum;

public class GameControllerImpl implements GameController {
    private List<Player> players;
    private List<Player> defeated;
    private Player currentPlayer;
    private int currentPlayerIndex = -1;

    private List<String> playersName;

    private List<Card> tableList;
    private List<Card> cardsList;
    private Card currentCard;
    private int currentCardIndex;
    private boolean landedOnUnforseen;
    private String unforseenMessage;

    private Dice currentDice;
    private boolean doubleDice;
    private int res1;
    private int res2;
    private int totalResult;

    private Map<BtnCodeEnum, Boolean> btnList;

    public GameControllerImpl(List<String> names) throws IOException {

        this.tableList = new CardFactoryImpl().cardsInitializer();
        this.cardsList = tableList.stream()
                .sorted(Comparator.comparingInt(Card::getCardId))
                .collect(Collectors.toList());

        this.playersName = new ArrayList<>();
        playersName.addAll(names);

        this.players = new ArrayList<>();
        this.initializePlayer();

        System.out.println(players);

        currentDice = new Dice();

        defeated = new ArrayList<>();

        btnList = new HashMap<>();
        btnList.put(BtnCodeEnum.buyHouse, false);
        btnList.put(BtnCodeEnum.buyPropriety, false);
        btnList.put(BtnCodeEnum.endTurn, false);
        btnList.put(BtnCodeEnum.rollDice, false);
        btnList.put(BtnCodeEnum.sellPropriety, false);
    }

    /*
     * run method will be called at the start of the game and at the end of every
     * turn
     */

    public void newTurn() {

        this.disableAllBtn();
        this.nextPlayer();
        this.landedOnUnforseen = false;

    }

    public void nextPlayer() {

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);

    }

    // prima di iniziare il turno se il player è in prigione può decidere se pagare
    // o provare ad uscire con un doppio dado

    public void tryLuckyBail() {

        // se non paga la cauzione prova a tirare il dado

        rollDice(false);

        if (doubleDice) {

            // inizia il turno del player corrente

            currentPlayer.setInJail(false);
            enableSingleButton(BtnCodeEnum.rollDice);

        } else {

            // finisce qui il suo turno

            enableSingleButton(BtnCodeEnum.endTurn);

        }

    }

    @Override
    public Integer rollDice(Boolean b) {

        currentDice.roll();

        res1 = currentDice.getDie1Result();
        res2 = currentDice.getDie2Result();
        totalResult = currentDice.getDiceResult();

        if (res1 == res2) {
            doubleDice = true;
        } else {
            doubleDice = false;
        }

        if (b) {
            disableSingleButton(BtnCodeEnum.rollDice);
            startTurn();

        }

        return totalResult;

    }

    public void startTurn() {

        int position = currentPlayer.getCurrentPosition();

        int finalPosition = position + totalResult;

        if (finalPosition >= cardsList.size()) {

            var new_position = finalPosition - cardsList.size();
            currentPlayer.setPosition(new_position);
            Unforseen.U0.getCard().makeAction(currentPlayer);

        } else {
            currentPlayer.setPosition(finalPosition);
        }

        currentCardIndex = currentPlayer.getCurrentPosition();

        currentCard = cardsList.get(currentCardIndex);

        handleCard();

    }

    // serve per attivare/disattivare certi bottoni in base alla carta su cui si è
    // finiti

    public void handleCard() {

        if (currentCard.isUnbuyable()) {

            handleUnbuyable();

        } else if (currentCard.isBuildable()) {

            handleBuildable();

        } else if (currentCard.isBuyable() && !currentCard.isBuildable()) {

            handleBuyable();

        }

        enableSingleButton(BtnCodeEnum.endTurn);

    }

    public void handleUnbuyable() {

        if (currentCard.getCardId() != 0) {
            TriggeredEvent e = CardAdapter.unbuyableAdapter(currentCard).makeAction(currentPlayer);
            if (e != null) {
                this.unforseenMessage = e.getMessage();
                this.landedOnUnforseen = true;
            }

            /*
             * if (e.equals(TriggeredEvent.PERFORMED)) {
             * String s = e.getMessage();
             * System.out.println(s);
             * }
             * currentCardIndex = currentPlayer.getCurrentPosition();
             * 
             * currentCard = cardsList.get(currentCardIndex);
             * 
             * handleCard();
             */
        }

    }

    public void handleBuildable() {

        Boolean owned = CardAdapter.buildableAdapter(currentCard).isOwned();

        if (owned) {

            Player owner = CardAdapter.buildableAdapter(currentCard).getOwner();

            if (currentPlayer.equals(owner)) {

                enableSingleButton(BtnCodeEnum.buyHouse);
                enableSingleButton(BtnCodeEnum.sellPropriety);

            } else {
                payFees(owner);
            }

        } else {
            enableSingleButton(BtnCodeEnum.buyPropriety);

        }

    }

    public void handleBuyable() {
        Boolean owned = CardAdapter.buyableAdapter(currentCard).isOwned();

        if (owned) {

            Player owner = CardAdapter.buyableAdapter(currentCard).getOwner();

            if (!currentPlayer.equals(owner)) {
                payFees(owner);
            } else {
                enableSingleButton(BtnCodeEnum.sellPropriety);
            }

        } else {
            enableSingleButton(BtnCodeEnum.buyPropriety);

        }
    }

    @Override
    public boolean buyPropriety() {

        disableSingleButton(BtnCodeEnum.buyPropriety);

        if (!currentPlayer.buyBox(CardAdapter.buyableAdapter(currentCard))) {

            return false;
        }

        return true;

    }

    @Override
    public boolean buildHouse() {

        disableSingleButton(BtnCodeEnum.buyHouse);

        if (!currentPlayer.buildHouse(CardAdapter.buildableAdapter(currentCard))) {

            return false;
        }

        return true;

    }

    @Override
    public void sellPropriety() {

        disableSingleButton(BtnCodeEnum.sellPropriety);
        currentPlayer.sellBuyable(CardAdapter.buyableAdapter(currentCard));
    }

    @Override
    public void payFees(Player owner) {

        int fees = CardAdapter.buyableAdapter(currentCard).getTransitFees();

        if (!currentPlayer.payPlayer(owner, fees)) {
            this.defeatPlayer();
        }

    }

    public void defeatPlayer() {

        defeated.add(currentPlayer);
        players.remove(currentPlayer);

        newIndex();

    }

    public boolean isOver() {
        return players.size() == 1;
    }

    public void endGame() {
        System.out.println("EndGame");
    }

    public boolean isCurrentPlayerDefeated() {
        return defeated.contains(currentPlayer);
    }

    public void newIndex() {
        this.currentPlayerIndex--;
    }

    public void enableSingleButton(BtnCodeEnum code) {

        btnList.put(code, true);

    }

    public void disableSingleButton(BtnCodeEnum code) {

        btnList.put(code, false);

    }

    public void disableAllBtn() {

        for (var entry : btnList.entrySet()) {
            entry.setValue(false);
        }

    }

    public void initializePlayer() {
        var id = 1;
        for (String name : playersName) {

            this.players.add(new PlayerImpl(name, id, cardsList, 500));
            id++;

        }
    }

    public Map<BtnCodeEnum, Boolean> getBtnStatus() {
        return btnList;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public Integer getDiceValue() {
        return totalResult;
    }

    @Override
    public Card getCurrentCard() {
        return currentCard;
    }

    public List<Card> getTableList() {
        return this.tableList;
    }

    public List<Card> getCardList() {
        return this.cardsList;
    }

    @Override
    public List<Player> getPlayerList() {
        return this.players;
    }

    @Override
    public List<Player> getDefeatedList() {
        return this.defeated;
    }

    public Boolean isCurrentPlayerInJail() {
        return currentPlayer.isInJail();
    }

    public void setDiceValue(int value) {
        this.totalResult = value;
    }

    public boolean isCurrentPlayerOnUnforseen() {
        return this.landedOnUnforseen;
    }

    public String getUnforseenMessage() {
        return this.unforseenMessage;
    }

}
