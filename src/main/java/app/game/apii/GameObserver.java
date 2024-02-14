package app.game.apii;

import app.player.apii.Player;

public interface GameObserver {

    public boolean update(Player currentPlayer, String str);

}
