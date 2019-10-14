/**
 * Job
 * @author Sam Malpass
 * @version 0.0.2
 * @since 0.0.0
 */
package mapReduce;

import java.util.ArrayList;

public abstract class Job {
    /**
     * Constructor with no arguments
     * <p>
     *     Sets up the object
     * </p>
     */
    public Job() {}

    /**
     * Function preprocess()
     * <p>
     *     User can define data conversion and cleaning in this step
     * </p>
     * @param input is the input data in the form of a list of lines, each of which represents a data entry
     * @return a list of Objects that accurately represent the data in the system
     */
    public abstract ArrayList<Object> preprocess(ArrayList<String> input);

    /**
     * Function map()
     * <p>
     *     User can define how to perform the map task for given data entries to return a list of Tuples
     * </p>
     * @param input are the data entries to be mapped
     * @return a list of Tuples
     */
    public abstract ArrayList<Tuple> map(ArrayList<Object> input);

    /**
     * Function reduce()
     * <p>
     *     User can define how to perform reduce task for given output from mapper threads to return a singular Tuple
     * </p>
     * @param key is the key to reduce by
     * @param input is the sorted output from the mappers
     * @return the reduced Tuple
     */
    public abstract Tuple reduce(Object key, ArrayList<Tuple> input);

    /**
     * Function format()
     * <p>
     *     User can define how to format the results from the reducer into a singular String which will be written to
     *     a file.
     * </p>
     * @param input are the Tuples from the reducer
     * @return the formatted String for file output
     */
    public abstract String format(ArrayList<Tuple> input);
}
