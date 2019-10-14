/**
 * Node
 * @author Sam Malpass
 * @version 0.0.0
 * @since 0.0.0
 */
package mapReduce;

import java.util.ArrayList;

public class Node implements Runnable {

    /**
     * thread holds a Thread object
     */
    private Thread thread;

    /**
     * threadID holds the ID of the thread
     */
    private String threadID;

    /**
     * mode holds the Job part to execute
     * <p>
     *     false is for reduce part
     *     true is for map part
     * </p>
     */
    private boolean mode;

    /**
     * input holds the data to be passed to the mapper
     */
    private ArrayList<Object> input;

    /**
     * mapperOutput holds the output from the mapper
     */
    private ArrayList<Tuple> mapperOutput = new ArrayList<>();

    /**
     * key holds the input key for the reducer
     */
    private Object key;

    /**
     * reducerOutput holds the output for the reducer
     */
    private Tuple reducerOutput;

    /**
     * task holds the Job to be run
     */
    private static Job task;

    /**
     * Constructor with arguments
     * <p>
     *     Takes the mode and the ID and prepares the Node for execution of a Job part
     * </p>
     * @param mode gives the type of node
     * @param id is the ID for the node
     */
    public Node(boolean mode, String id) {
        this.threadID = id;
        this.mode = mode;
    }

    /**
     * Function start()
     * <p>
     *     Starts the thread
     * </p>
     */
    public void start() {
        if(thread == null) {
            thread = new Thread(this, threadID);
            thread.start();
        }
    }

    /**
     * Function run()
     * <p>
     *     Executes the map or reduce task based on the mode the Node is in
     * </p>
     */
    @Override
    public void run() {
        if(this.mode) {
            mapperOutput = task.map(input);
        }
        else {
            reducerOutput = task.reduce(key, mapperOutput);
        }
    }
}
