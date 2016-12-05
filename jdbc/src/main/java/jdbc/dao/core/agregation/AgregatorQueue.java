package jdbc.dao.core.agregation;

/**
 * Contanis a FIFO data structure.
 * Loads portions of data from database.
 * @param <T> - type of elements in Queue
 */
public interface AgregatorQueue<T>{
    /**
     * Gets next element from database.
     * Returns <code>null</code> if there are no more elements in DB.
     * @return T
     */
    T poll();

    /**
     * Check next element.
     * Returns <code>null</code> if there are no more elements in DB.
     * @return T
     */
    T peek();


    /**
     *
     * @return int Count of already removed elements.
     */
    int readed();
}
