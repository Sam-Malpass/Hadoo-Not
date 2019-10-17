/**
 * ReduceNode
 * @author Sam Malpass
 * @version 0.0.4
 * @since 0.0.4
 */
package application.nodes;

import mapReduce.Tuple;

import java.util.ArrayList;

public class ReduceNode extends Node {

    /**
     * key holds the key the reducer will reduce by
     */
    private Object key;

    /**
     * Constructor with arguments
     * <p>
     * Takes the mode and the ID and prepares the Node for execution of a Job part
     * </p>
     *
     * @param id is the ID for the node
     */
    public ReduceNode(String id) {
        super(id);
    }

    /**
     * Function start()
     * <p>
     *     Sets the input to the passed data and runs the operation
     * </p>
     * @param key is the key to reduce by
     * @param input is the data to work on
     */
    public void start(Object key, ArrayList<Tuple> input) {
        setInput(input);
        this.key = key;
        super.start();
    }

    /**
     * Function run()
     * <p>
     *     Runs the thread
     * </p>
     */
    @Override
    public void run() {
        ArrayList<Tuple> input = (ArrayList<Tuple>) getInput();
        setOutput(getTask().reduce(key, input));
    }
}
