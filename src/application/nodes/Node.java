/**
 * Node
 * @author Sam Malpass
 * @version 0.0.4
 * @since 0.0.4
 */
package application.nodes;

import mapReduce.Job;

import java.util.ArrayList;

public abstract class Node implements Runnable {

    /**
     * thread holds a Thread object
     */
    private Thread thread;

    /**
     * threadID holds the ID of the thread
     */
    private String threadID;

    /**
     * input holds the data to be passed to the node
     */
    private ArrayList<Object> input;

    /**
     * output holds the data output by the node
     */
    private ArrayList<Object> output;

    /**
     * task holds the Job to be run
     */
    private static Job task;

    /**
     * Constructor with arguments
     * <p>
     *     Takes the mode and the ID and prepares the Node for execution of a Job part
     * </p>
     * @param id is the ID for the node
     */
    public Node(String id) {
        this.threadID = id;
    }
}
