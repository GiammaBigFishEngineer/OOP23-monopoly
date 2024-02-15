package app.game.apii;

import app.player.apii.Player;

public class GameObservableImpl implements GameObservable {

    private GameObserver obs;

    @Override
    public void registerObserver(GameObserver obs) {
        this.obs = obs;
    }

    @Override
    public boolean updateObserver(Integer diceValue, Player currentPlayer, String string) {
        return this.obs.update(diceValue, currentPlayer, string);
    }

}
