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
        ArrayList<Tuple> input = (ArrayList<Tuple>) this.getInput();
        ArrayList<Object> totalObjects = new ArrayList<>();
        Object key = input.get(0).getKey();
        for(Tuple t : input) {
            totalObjects.add(t.getValue());
        }
        setOutput(new Tuple(key, totalObjects));
    }
}
