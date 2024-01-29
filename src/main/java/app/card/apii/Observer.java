package app.card.apii;

/**
 * Observer interface rappresent the obj to be observed
 */
@FunctionalInterface
public interface Observer<T> {

    /**
     * Notify the change on object
     * @param observable is the obj to update
     */
    void update();
}
