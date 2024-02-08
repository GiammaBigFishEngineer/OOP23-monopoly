package app.card.apii;

/**
 * Observer interface rappresent the obj to be observed.
 * @param <T> is the generic type of obresver.
 */
@FunctionalInterface
public interface Observer<T> {

    /**
     * make changes on object.
     */
    void update();
}
