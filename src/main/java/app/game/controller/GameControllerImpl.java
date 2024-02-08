package app.game.controller;

import java.util.LinkedList;
import java.util.List;

import app.card.apii.Card;
import app.game.utils.Dice;
import app.player.apii.Player;
import app.player.impl.PlayerImpl;

public class GameControllerImpl {
    private List<PlayerImpl> players = new LinkedList<>();
    private PlayerImpl currentPlayer;
    private int currentPlayerIndex = -1;

    private Card currentCard;
    private Dice currentDice;

    GameControllerImpl(List<String> playersName) {
        for (String string : playersName) {
            players.add(new PlayerImpl());
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

    }

    public void printState() {

        for (PlayerImpl player : players) {
            System.out.println("Player position : ");
            // System.out.println(String.valueOf(player.getCurrentPosition()));
        }

    }
}
