package app.card.view;

import java.util.List;

import app.card.apii.Observable;
import app.card.apii.Observer;
import javax.swing.JPanel;

import java.util.ArrayList;

/**
 * An observable of a generic parameter T.
 * Observervable is an abstract class for be extended with implementation.
 * @param <T> is a generic class to be Observable
 */
public abstract class ObservableImpl<T> extends JPanel implements Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();
    private static final long serialVersionUID = 2298666777798069846L;

    /**
     * add an observer to list.
     */
    @Override
    public void addObserver(final Observer<T> observer) {
        this.observers.add(observer);
    }

    /**
     * delete an observer to list.
     */
    @Override
    public void deleteObserver(final Observer<T> observer) {
        this.observers.remove(observer);
    }

    /**
     * notify all observer.
     */
    @Override
    public void notifyObservers() {
        for (final Observer<T> observer : observers) {
            observer.update();
        }
    }
}

