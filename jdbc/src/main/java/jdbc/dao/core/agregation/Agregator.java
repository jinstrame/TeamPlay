package jdbc.dao.core.agregation;


import java.util.List;

/**
 * Agregates data from different AgregatorQueues in one data structure.
 * @param <T>
 */
public interface Agregator<T>{
    /**
     * get List of next <code>count</code> elements
     * @param count count of elements to load
     */
    List<T> getNext(int count);

    /**
     *
     * @return maximum elements to get from this Agregator
     */
    int size();


    /**
     *
     * @return int Count of already recieved elements.
     */
    int readed();
}
