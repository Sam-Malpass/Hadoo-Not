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

    /**
     * Function getKey()
     * <p>
     *     Returns the key
     * </p>
     * @return the key
     */
    public Object getKey() {
        return this.key;
    }

    /**
     * Function getValue()
     * <p>
     *     Returns the value
     * </p>
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Function setKey()
     * <p>
     *     Sets the key to the passed argument
     * </p>
     * @param newKey is the new key to use
     */
    public void setKey(Object newKey) {
        this.key = newKey;
    }

    /**
     * Function setValue()
     * <p>
     *     Sets the value to the passed argument
     * </p>
     * @param newVal is the new value to be used
     */
    public void setValue(Object newVal) {
        this.value = newVal;
    }

    /**
     * Function toString()
     * <p>
     *     Returns a formatted string of the Tuple's data
     * </p>
     * @return a formatted string
     */
    public String toString() {
        return "<" + key.toString() + "," + value.toString() + ">";
    }
}
