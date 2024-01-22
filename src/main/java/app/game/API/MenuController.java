package app.game.api;

import java.util.*;

public interface MenuController {
    Boolean startGame();
    void quitGame();
    void insertPlayers(List<String> stringPlayer);
}
