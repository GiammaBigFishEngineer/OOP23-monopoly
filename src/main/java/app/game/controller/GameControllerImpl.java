package app.game.controller;

import java.util.LinkedList;
import java.util.List;

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

    private Card currentCard;
    private Dice currentDice = new Dice();

    private Observer observer;

    public GameControllerImpl(List<String> playersName) {
        for (String string : playersName) {
            players.add(new PlayerImpl(string, 0, null, 0));
        }
    }

    /*
     * run method will be called at the start of the game and at the end of every
     * turn
     */

    public void run() {

        this.nextPlayer();
        this.turn();

    }

    public void nextPlayer() {

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);

    }

    public void turn() {

        if (currentPlayer.isInJail()) {
            observer.update();
        }

    }

    public void printState() {

        for (Player player : players) {
            System.out.println("Player position : ");
            System.out.println(String.valueOf(player.getCurrentPosition()));
        }

    }

    @Override
    public void rollDice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rollDice'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endTurn'");
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

    public void registerObserver(Observer obs) {
        this.observer = obs;
    }
}
