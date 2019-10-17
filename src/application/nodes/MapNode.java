/**
 * MapNode
 * @author Sam Malpass
 * @version 0.0.5
 * @since 0.0.4
 */
package application.nodes;

import java.util.ArrayList;

public class MapNode extends Node {

    /**
     * Constructor with arguments
     * <p>
     * Takes the mode and the ID and prepares the Node for execution of a Job part
     * </p>
     * @param id is the ID for the node
     */
    public MapNode(String id) {
        super(id);
    }

    /**
     * Function start()
     * <p>
     *     Sets the input and runs the thread
     * </p>
     * @param input is the input to operate on
     */
    public void start(ArrayList<Object> input) {
        this.setInput(input);
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
        ArrayList<Object> input = (ArrayList<Object>) getInput();
        setOutput(getTask().map(input));
    }
}
