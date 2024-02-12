package app.game.apii;

import java.util.List;

/**
 * MenuController.
 */
public interface MenuController {
    /**
     * @return boolean
     */
    Boolean startGame();
    /**
     * 
     */
    void quitGame();
    /**
     * @param stringPlayer
     */
    void insertPlayers(List<String> stringPlayer);
}
