package app.card.apii;

/**
 * An observable of a generic parameter T.
 * Observervable is an abstract class for be extended with implementation.
 * @param <T> is the generic type of observable
 */
public interface Observable<T> {

    /**
     * Add observer to a List or Set.
     * @param observer
     */
    void addObserver(Observer<T> observer);

    /**
     * Delete ad oberver from List or Set.
     * @param observer
     */
    void deleteObserver(Observer<T> observer);

    /**
     * Notify all observers for update.
     */
    void notifyObservers();
}
