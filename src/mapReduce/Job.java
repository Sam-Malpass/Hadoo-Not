/**
 * Job
 * @author Sam Malpass
 * @version 0.0.0
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
}
