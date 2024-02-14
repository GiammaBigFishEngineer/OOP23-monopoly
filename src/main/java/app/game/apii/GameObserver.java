package app.game.apii;

import app.player.apii.Player;

public interface GameObserver {

    public boolean update(Integer dice, Player currentPlayer, String str);

}
