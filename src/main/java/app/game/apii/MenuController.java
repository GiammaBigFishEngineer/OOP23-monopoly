package app.game.apii;

import java.util.*;

public interface MenuController {
    Boolean startGame();
    void quitGame();
    void insertPlayers(List<String> stringPlayer);
    void savedGames();
}
