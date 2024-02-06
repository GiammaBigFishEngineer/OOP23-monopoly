package app.game.controller;

import java.util.LinkedList;
import java.util.List;

import app.card.API.Card;
import app.game.utils.Dice;
import app.player.API.Player;

public class GameControllerImpl {
    private List<Player> players = new LinkedList<>();
    private Player currentPlayer;
    private Card currentCard;
    private Dice currentDice;

    public void run() {

    }

    public void turn() {

    }

    public void printState() {

        for (Player player : players) {
            System.out.println("Player position : ");
            System.out.println(String.valueOf(player.getCurrentPosition()));
        }

    }
}
