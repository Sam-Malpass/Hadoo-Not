/**
 * Tuple
 * @author Sam Malpass
 * @version 0.0.0
 * @since 0.0.0
 */
package mapReduce;

public class Tuple {
    /**
     * key holds the key part of the Tuple
     */
    private Object key;
    /**
     * value holds the value part of the tuple
     */
    private Object value;

    /**
     * Constructor with no arguments
     * <p>
     *     Sets up a null object and should not be used
     * </p>
     */
    public Tuple() {
        key = null;
        value = null;
    }

    /**
     * Constructor with arguments
     * <p>
     *     Sets up the Tuple object using the first argument as the key and the second argument as the value
     * </p>
     * @param k is the key to be used
     * @param v is the value to be used
     */
    public Tuple(Object k, Object v) {
        this.key = k;
        this.value = v;
    }
}
