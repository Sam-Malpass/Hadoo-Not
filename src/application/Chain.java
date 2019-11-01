/**
 * Chain
 * @author Sam Malpass
 * @version 0.0.8
 * @since 0.0.8
 */
package application;

import java.util.ArrayList;

public class Chain {

    /**
     * jobChain holds a list of all Jobs to be performed in the process
     */
    ArrayList<Process> jobChain;

    /**
     * inputPath holds the location of the input data
     */
    String inputPath;

    /**
     * outputPath holds the location of the output data
     */
    String outputPath;

    /**
     * Constructor with arguments
     * <p>
     *
     * </p>
     * @param jarPath is the JAR to load jobs from
     * @param jobNames the list of jobs to be performed in order
     */
    public Chain(String jarPath, ArrayList<String> jobNames) {
        for(String s : jobNames) {
            Process p = new Process(jarPath, s);
            jobChain.add(p);
        }
    }

    public void execute() {
        for(int i = 0; i < jobChain.size(); i++) {
            if(i == 0) {
                jobChain.get(i).setup(inputPath, outputPath);
            }
            else if(i == jobChain.size() - 1) {
                jobChain.get(i).setup(inputPath, outputPath);
                jobChain.get(i).setInput((ArrayList) jobChain.get(i-1).getOutput());
            }
            else {
                jobChain.get(i).setInput((ArrayList) jobChain.get(i-1).getOutput());
            }
            jobChain.get(i).start(i+1);
        }
    }
}
