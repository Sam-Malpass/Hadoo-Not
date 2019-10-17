/**
 * MapNode
 * @author Sam Malpass
 * @version 0.0.4
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
     * Function run()
     * <p>
     *     Sets the input and runs the thread
     * </p>
     * @param input is the input to operate on
     */
    public void run(ArrayList<Object> input) {
        this.setInput(input);
        run();
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
        getTask().map(input);
    }
}
