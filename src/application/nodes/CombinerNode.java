/**
 * CombinerNode
 * @author Sam Malpass
 * @version 0.0.8
 * @since 0.0.5
 */
package application.nodes;

import mapReduce.Tuple;
import java.util.ArrayList;

public class CombinerNode extends Node {

    /**
     * Constructor with arguments
     * <p>
     * Takes the mode and the ID and prepares the Node for execution of a Job part
     * </p>
     *
     * @param id is the ID for the node
     */
    public CombinerNode(String id) {
        super(id);
    }

    /**
     * Function start()
     * <p>
     *     Sets the input and runs the thread
     * </p>
     * @param input is the input for the combiner
     */
    public void start(ArrayList<ArrayList<Tuple>> input) {
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
        ArrayList<ArrayList<Tuple>> input = (ArrayList<ArrayList<Tuple>>) this.getInput();
        ArrayList<Tuple> output = new ArrayList<>();
        for(ArrayList<Tuple> x : input) {
            ArrayList<Object> totalObjects = new ArrayList<>();
            Object key = x.get(0).getKey();
            for (Tuple t : x) {
                totalObjects.add(t.getValue());
            }
            Tuple tuple = new Tuple(key, totalObjects);
            output.add(tuple);
        }
        setOutput(output);
    }
}
