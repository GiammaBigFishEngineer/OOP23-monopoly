package app.game.apii;

import java.util.Optional;

import app.player.apii.Player;

public interface GameObserver {

    public boolean update(Integer dice, Optional<Player> currentPlayer, String str);

}
