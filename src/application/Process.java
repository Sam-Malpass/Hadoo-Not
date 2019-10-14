/**
 * Process
 * @author Sam Malpass
 * @version 0.0.1
 * @since 0.0.1
 */
package application;

import mapReduce.Node;
import mapReduce.Tuple;
import java.util.ArrayList;

public class Process {

    /**
     * blockSize holds the number of data entries to be sent to a single mapper node
     */
    private int blockSize;

    /**
     * jobName is the name of the class file to use as the Job
     */
    private String jobName;

    /**
     * mapperNodes holds a list of all the Nodes that map
     */
    private ArrayList<Node> mapperNodes = new ArrayList<>();

    /**
     * reducerNodes holds a list of all the Nodes that reduce
     */
    private ArrayList<Node> reducerNodes = new ArrayList<>();

    /**
     * shuffledOutput holds a sorted list of all map Node outputs
     */
    private ArrayList<Tuple> shuffledOutput = new ArrayList<>();

    /**
     * finalOutput holds the results from the reduce Nodes
     */
    private ArrayList<Tuple> finalOutput = new ArrayList<>();

    /**
     * Constructor with arguments
     * <p>
     *     Sets up the object using the passed arguments
     * </p>
     * @param size is the number to use for blockSize
     * @param jobName is the name of the class file to load
     */
    public Process(int size, String jobName) {
        this.blockSize = size;
        this.jobName = jobName;
    }

    /**
     * Function split()
     * <p>
     *     Splits the list of input data entries into chunks of blockSize
     * </p>
     * @param input is the data to be split
     * @return the chunks
     */
    private ArrayList<ArrayList<Object>> split(ArrayList<Object> input) {
        ArrayList<ArrayList<Object>> chunks = new ArrayList<>();
        for(int i = 0; i < input.size(); i += blockSize) {
            ArrayList<Object> chunk = new ArrayList<>(input.subList(i, Math.min(input.size(), i + blockSize)));
            chunks.add(chunk);
        }
        return chunks;
    }
}
