package app.game.view;

import app.game.apii.GameObservable;

import java.util.Optional;

import javax.swing.JPanel;

public class GameObservableImpl extends JPanel implements GameObservable {

    private GameObserverImpl obs;

    @Override
    public boolean updateObserver(Optional<Object> obj, String code) {
        return this.obs.update(obj, code);
    }

    @Override
    public void registerObserver(GameObserverImpl obs) {
        this.obs = obs;
    }

}
