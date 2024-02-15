package app.game.controller;

import java.util.*;

import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.game.apii.GameController;

import app.game.utils.Dice;

import app.player.apii.Player;

import app.game.view.BtnCodeEnum;

public class GameControllerImpl implements GameController {
    private List<Player> players;
    private List<Player> defeated;
    private Player currentPlayer;
    private int currentPlayerIndex = -1;

    private List<Card> cards;
    private Card currentCard;
    private int currentCardIndex;

    private Dice currentDice;
    private boolean doubleDice;
    private int res1;
    private int res2;
    private int totalResult;

    private Map<BtnCodeEnum, Boolean> btnList;

    public GameControllerImpl(List<Player> playersList, List<Card> cardList) {

        players = new ArrayList<>();
        this.players.addAll(playersList);

        cards = new ArrayList<>();
        this.cards.addAll(cardList);

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

    public Boolean isCurrentPlayerInJail() {
        return currentPlayer.isInJail();
    }

    // prima di iniziare il turno se il player è in prigione può decidere se pagare
    // o provare ad uscire con un doppio dado

    public void tryLuckyBail() {

        // se non paga la cauzione prova a tirare il dado

        rollDice(false);

        if (doubleDice) {

            // inizia il turno del player corrente
            System.out.println("You get the same result");
            currentPlayer.setInJail(false);
            enableSingleButton(BtnCodeEnum.rollDice);

        } else {

            // finisce qui il suo turno
            System.out.println("You didn't get the same result");
            enableSingleButton(BtnCodeEnum.endTurn);

        }

    }

    @Override
    public boolean rollDice(Boolean b) {

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
            btnList.put(BtnCodeEnum.rollDice, false);
            startTurn();

        }

        return isDefeated();

    }

    public void startTurn() {

        var position = currentPlayer.getCurrentPosition();

        var finalPosition = position + totalResult;

        if (finalPosition >= cards.size()) {

            var new_position = finalPosition - cards.size();
            currentPlayer.setPosition(new_position);

        } else {
            currentPlayer.setPosition(finalPosition);
        }

        currentCardIndex = currentPlayer.getCurrentPosition();

        currentCard = cards.get(currentCardIndex);

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
            System.out.println("GameOver");
            this.endGame();
        }
    }

    public boolean isOver() {
        return players.size() == 1;
    }

    public void endGame() {

    }

    public boolean isDefeated() {
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

}
