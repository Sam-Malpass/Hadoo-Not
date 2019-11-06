/**
 * Node
 * @author Sam Malpass
 * @version 0.0.9
 * @since 0.0.4
 */
package application.nodes;

import mapReduce.Job;

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
    private Object input;

    /**
     * output holds the data output by the node
     */
    private Object output;

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

    /**
     * Function setup()
     * <p>
     *     Sets the task to passed Job object, which should be user defined
     * </p>
     * @param passedTask is the task to be run
     */
    public static void setup(Job passedTask) {
        task = passedTask;
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
     *     Executes the nodes task
     * </p>
     */
    @Override
    public abstract void run();

    /**
     * Function getInput()
     * <p>
     *     Return the input data
     * </p>
     * @return the input data
     */
    public Object getInput() {
        return input;
    }

    /**
     * Function getOutput()
     * <p>
     *     Return the output data
     * </p>
     * @return the output data
     */
    public Object getOutput() {
        return output;
    }

    /**
     * Function getTask()
     * <p>
     *     Returns the task
     * </p>
     * @return the task
     */
    public Job getTask(){
        return task;
    }

    /**
     * Function getThread()
     * <p>
     *     Return the thread
     * </p>
     * @return the thread
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * Function getThreadID()
     * <p>
     *     Return the threadID
     * </p>
     * @return the threadID
     */
    public String getThreadID() {
        return threadID;
    }

    /**
     * Function setInput()
     * <p>
     *     Sets the input to the passed data
     * </p>
     * @param input is the input for the application
     */
    public void setInput(Object input) {
        this.input = input;
    }

    /**
     * Function setOutput()
     * <p>
     *     Sets the output to the passed object
     * </p>
     * @param output is the new output value
     */
    public void setOutput(Object output) {
        this.output = output;
    }
}
