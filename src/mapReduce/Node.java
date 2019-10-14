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
     * task holds the Job to be run
     */
    private static Job task;

    public Node(boolean mode, String id) {

    }
    @Override
    public void run() {

    }
}
