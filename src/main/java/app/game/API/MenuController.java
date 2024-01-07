package app.game.API;

import java.util.*;

import app.player.API.Player;

public interface MenuController {
    Boolean startGame();
    void quitGame();
    void insertPlayers(List<Player> stringPlayer);
}
