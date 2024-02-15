package app.game.model;

import app.card.apii.Card;
import app.player.apii.Player;
import app.player.impl.PlayerImpl;

import java.util.List;
import java.util.ArrayList;

public class ListConverter {

    /*
     * Static method, in fact this class is never inithializated
     */

    public static List<Player> convert(List<String> playerNames, List<Card> cardList) {

        List<Player> players = new ArrayList<>();
        int id = 1;

        for (String name : playerNames) {
            players.add(new PlayerImpl(name, id, cardList, 500));
            id++;
        }

        return players;

    }

}
