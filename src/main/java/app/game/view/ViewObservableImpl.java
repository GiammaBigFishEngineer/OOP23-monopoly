package app.game.view;

import app.game.api.ViewObservable;
import app.game.api.ViewObserver;
import app.game.utils.ObserverCodeEnum;

import java.util.Optional;

import javax.swing.JPanel;

/**
 * This class is the implementation of ViewObservable.
 * It will be extended by the button view, so it also extends JPanel
 */

public class ViewObservableImpl extends JPanel implements ViewObservable {

    private static final long serialVersionUID = 13L;

    private transient ViewObserver obs;

    /**
     * {@inheritDoc}
     */

    @Override
    public void registerObserver(final ViewObserver obs) {
        this.obs = obs;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean updateObserver(final Optional<Object> obj, final ObserverCodeEnum code) {

        return obs != null && this.obs.update(obj, code);
    }

}
