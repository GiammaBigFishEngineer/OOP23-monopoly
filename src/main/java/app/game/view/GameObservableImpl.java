package app.game.view;

import app.game.apii.GameObservable;

import java.util.Optional;

import javax.swing.JPanel;

/**
 * 
 */

public class GameObservableImpl extends JPanel implements GameObservable {

    private static final long serialVersionUID = 1L;

    private transient GameObserverImpl obs;

    /**
    * 
    */

    @Override
    public void registerObserver(final GameObserverImpl obs) {
        this.obs = obs;
    }

    /**
    * 
    */

    @Override
    public boolean updateObserver(final Optional<Object> obj, final String code) {

        return (obs != null ? this.obs.update(obj, code) : false);
    }

}
