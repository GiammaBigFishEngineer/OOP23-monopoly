package app.game.view;

import app.game.apii.GameObservable;
import app.player.apii.Player;

import javax.swing.JPanel;

public class GameObservableImpl extends JPanel implements GameObservable {

    private GameObserverImpl obs;

    @Override
    public boolean updateObserver(Integer diceValue, Player currentPlayer, String string) {
        return this.obs.update(diceValue, currentPlayer, string);
    }

    @Override
    public void registerObserver(GameObserverImpl obs) {
        this.obs = obs;
    }

}
