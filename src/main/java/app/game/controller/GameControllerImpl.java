package app.game.controller;

import java.io.IOException;
import java.util.*;

import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.card.impl.CardFactoryImpl;
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

    private Dice currentDice;
    private boolean doubleDice;
    private int res1;
    private int res2;
    private int totalResult;

    private Map<BtnCodeEnum, Boolean> btnList;

    public GameControllerImpl(List<String> names) throws IOException {

        this.tableList = new CardFactoryImpl().cardsInitializer();
        this.cardsList = tableList.stream()
                .sorted(Comparator.comparingInt(tableList::get(1))
                .toList();

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

        } else {
            currentPlayer.setPosition(finalPosition);
        }

        currentCardIndex = currentPlayer.getCurrentPosition();

        currentCard = cardsList.get(currentCardIndex);

        System.out.println("Player :" + currentPlayer.getName());
        System.out.println("Position :" + currentPlayer.getCurrentPosition());
        System.out.println("Balance :" + currentPlayer.getBankAccount().getBalance());
        System.out.println("Carta :" + currentCard.getName());
        if (currentCard.isBuildable()) {
            System.out.println("Casella , prezzo :" + CardAdapter.buildableAdapter(currentCard).getPrice());
            System.out.println("Casella, tassa :" + CardAdapter.buildableAdapter(currentCard).getTransitFees());
            System.out.println("Casella, prezzo casa :" + CardAdapter.buildableAdapter(currentCard).getHousePrice());
        }
        if (currentCard.isBuyable() && !currentCard.isBuildable()) {
            System.out.println("Stazione , prezzo :" + CardAdapter.buyableAdapter(currentCard).getPrice());
            System.out.println("Stazione , tassa :" + CardAdapter.buyableAdapter(currentCard).getTransitFees());
        }

        System.out.println("-----------------------------");

        handleCard();

    }

    // serve per attivare/disattivare certi bottoni in base alla carta su cui si è
    // finiti

    public void handleCard() {

        if (currentCard.isUnbuyable()) {

            CardAdapter.unbuyableAdapter(currentCard).makeAction(currentPlayer);

        } else if (currentCard.isBuildable()) {

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

        } else if (currentCard.isBuyable() && !currentCard.isBuildable()) {

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

        enableSingleButton(BtnCodeEnum.endTurn);

    }

    @Override
    public boolean buyPropriety() {

        disableSingleButton(BtnCodeEnum.buyPropriety);

        if (!currentPlayer.buyBox(CardAdapter.buyableAdapter(currentCard))) {
            System.out.println("you can't afford it");
            return false;
        }

        return true;

    }

    @Override
    public boolean buildHouse() {

        disableSingleButton(BtnCodeEnum.buyHouse);

        if (!currentPlayer.buildHouse(CardAdapter.buildableAdapter(currentCard))) {
            System.out.println("you can't afford it");
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

        if (!currentPlayer.getBankAccount().payPlayer(owner, fees)) {
            this.defeatPlayer();
        }

    }

    public void defeatPlayer() {

        defeated.add(currentPlayer);
        players.remove(currentPlayer);

        newIndex();

        if (isOver()) {
            this.endGame();
        }
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

    public void initializePlayer() {
        var id = 1;
        for (String name : playersName) {

            this.players.add(new PlayerImpl(name, id, cardsList, 500));
            id++;

        }
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

    public void setDiceValue(int value) {
        this.totalResult = value;
    }

    @Override
    public Card getCurrentCard() {
        return currentCard;
    }

    @Override
    public List<Player> getDefeatedList() {
        return this.defeated;
    }

    public Boolean isCurrentPlayerInJail() {
        return currentPlayer.isInJail();
    }

}
