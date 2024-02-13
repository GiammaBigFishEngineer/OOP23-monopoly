package app.game.apii;

import app.player.apii.Player;

public interface Observer {

    public boolean update(Player currentPlayer, String str);

}
