package app.game.controller;

import java.util.*;

import javax.swing.*;

import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.game.apii.GameController;
import app.game.apii.Observer;
import app.game.utils.Dice;
import app.game.view.ButtonPanelView;
import app.player.apii.Player;
import app.player.impl.PlayerImpl;
import app.game.view.BtnCodeEnum;

public class GameControllerImpl implements GameController {
    private List<Player> players;
    private Player currentPlayer;
    private int currentPlayerIndex = -1;

    private List<Card> cards = new LinkedList<>();
    private Card currentCard;
    private int currentCardIndex;

    private Dice currentDice = new Dice();
    private boolean doubleDice;
    private int res1;
    private int res2;
    private int totalResult;

    private Map<BtnCodeEnum, Boolean> btnList = new HashMap<>();

    private Observer observer;

    public GameControllerImpl(List<Player> playersList) {
        this.players = playersList;
    }

    /*
     * run method will be called at the start of the game and at the end of every
     * turn
     */

    public Map<BtnCodeEnum, Boolean> newTurn() {

        this.disableAllBtn();
        this.nextPlayer();
        this.checkPlayerState();

        return btnList;

    }

    // prima di iniziare il turno se il player è in prigione può decidere se pagare
    // o provare ad uscire con un doppio dado

    public void checkPlayerState() {

        if (currentPlayer.isInJail()) {

            boolean result = observer.update(currentPlayer);

            if (result) {

                // ha pagato ed è uscito di prigione
                enableSingleButton(BtnCodeEnum.rollDice);

            } else {

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
                    endTurn();

                }
            }

        } else {
            // inizia il turno normalmente (il turno inizia semplicemente rendendo
            // clickabile solo il bottone dei dadi)
            enableSingleButton(BtnCodeEnum.rollDice);
        }

    }

    @Override
    public void rollDice(Boolean b) {

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
            startTurn();
        }

    }

    public void startTurn() {

        currentPlayer.setPosition(currentPlayer.getCurrentPosition() + totalResult);

        currentCardIndex = currentPlayer.getCurrentPosition();

        currentCard = cards.get(currentCardIndex);

        handleCard();

    }

    public void nextPlayer() {

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);

    }

    // serve per attivare/disattivare certi bottoni in base alla carta su cui si è
    // finiti

    public void handleCard() {

        // carte statiche

        if (currentCard.isUnbuyable()) {

            CardAdapter.unbuyableAdapter(currentCard).makeAction(currentPlayer);

            // attivi endTurnBtn e sellHouseBtn

        }

        // proprietà

        if (currentCard.isBuildable()) {

            Player owner = CardAdapter.buildableAdapter(currentCard).getOwner();

            if (currentPlayer.equals(owner)) {
                // puoi costruire casa
            } else {

                // devi pagare la tassa
            }

        }

        // stazione

        if (currentCard.isBuyable() && !currentCard.isBuildable()) {

            Player owner = CardAdapter.buildableAdapter(currentCard).getOwner();

            if (!currentPlayer.equals(owner)) {
                // devi pagare la tassa
            }

        }

    }

    @Override
    public void buyPropriety(Player currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buyPropriety'");
    }

    @Override
    public void buildHouse(Player currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildHouse'");
    }

    @Override
    public void sellPropriety(Player currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sellPropriety'");
    }

    @Override
    public void endTurn() {
        newTurn();
    }

    @Override
    public void payFees(Player currentPlayer, Card box) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'payFees'");
    }

    @Override
    public void checkBox(Player currentPlayer, Card box) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkBox'");
    }

    @Override
    public Card currentBox(Player currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'currentBox'");
    }

    public void enableSingleButton(BtnCodeEnum code) {

        btnList.put(code, false);

    }

    public void disableAllBtn() {

        for (var entry : btnList.entrySet()) {
            entry.setValue(false);
        }

    }

    public void registerObserver(Observer obs) {
        this.observer = obs;
    }
}
