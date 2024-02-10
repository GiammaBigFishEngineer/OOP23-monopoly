package app.game.controller;

import java.util.*;

import javax.swing.*;

import app.card.apii.Card;
import app.game.apii.GameController;
import app.game.utils.Dice;
import app.player.apii.Player;
import app.player.impl.PlayerImpl;
import game.view.Observer;

public class GameControllerImpl implements GameController {
    private List<Player> players = new LinkedList<>();
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

    private List<JButton> btnList = new LinkedList();

    private Observer observer;

    private int prova;

    public GameControllerImpl(List<String> playersName) {
        for (String string : playersName) {
            players.add(new PlayerImpl(string, 0, null, 0));
        }

    }

    /*
     * run method will be called at the start of the game and at the end of every
     * turn
     */

    public void newTurn() {

        this.disableAllBtn();
        this.nextPlayer();
        this.checkPlayerState();

    }

    // prima di iniziare il turno se il player è in prigione può decidere se pagare
    // o provare ad uscire con un doppio dado

    public void checkPlayerState() {

        if (currentPlayer.isInJail()) {
            observer.update();
            if (prova == 0) {

                // paga per uscire e inizia il turno
                int balance = currentPlayer.getBankAccount().getBalance();
                currentPlayer.getBankAccount().setBalance(balance - 50);
                currentPlayer.setInJail(false);
                enableRollDiceBtn();

            } else {

                rollDice(false);

                if (doubleDice) {

                    // inizia il turno del player corrente
                    System.out.println("You get the same result");
                    currentPlayer.setInJail(false);
                    enableRollDiceBtn();

                } else {

                    // finisce qui il suo turno
                    System.out.println("You didn't get the same result");
                    endTurn();

                }
            }

        } else {
            // inizia il turno normalmente (il turno inizia semplicemente rendendo
            // clickabile solo il bottone dei dadi)
            enableRollDiceBtn();
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

    public void enableRollDiceBtn() {

    }

    public void disableAllBtn() {

        for (JButton jButton : btnList) {
            jButton.setEnabled(false);
        }

    }

    public void registerObserver(Observer obs) {
        this.observer = obs;
    }
}
